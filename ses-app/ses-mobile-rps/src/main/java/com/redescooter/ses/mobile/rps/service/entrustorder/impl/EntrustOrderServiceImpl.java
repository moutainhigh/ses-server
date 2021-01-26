package com.redescooter.ses.mobile.rps.service.entrustorder.impl;

import com.redescooter.ses.api.common.enums.entrustorder.EntrustOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.service.RosEntrustOrderService;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeLogisticsOrderMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.*;
import com.redescooter.ses.mobile.rps.dao.invoice.InvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.order.OrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.entrustorder.EntrustOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2020/12/30 18:12
 */
@Slf4j
@Service
public class EntrustOrderServiceImpl implements EntrustOrderService {

    @Reference
    private IdAppService idAppService;
    @Reference
    private RosEntrustOrderService rosEntrustOrderService;
    @Resource
    private EntrustOrderMapper entrustOrderMapper;
    @Resource
    private EntrustScooterBMapper entrustScooterBMapper;
    @Resource
    private EntrustCombinBMapper entrustCombinBMapper;
    @Resource
    private EntrustPartsBMapper entrustPartsBMapper;
    @Resource
    private EntrustProductSerialNumMapper entrustProductSerialNumMapper;
    @Resource
    private OpeLogisticsOrderMapper opeLogisticsOrderMapper;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public Map<Integer, Integer> getEntrustOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResults = entrustOrderMapper.getEntrustOrderTypeCount();

        /**
         * {entrustType, totalCount}
         */
        Map<Integer, Integer> resultMap = countByStatusResults.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
        );

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (null == resultMap.get(item.getValue())) {
                resultMap.put(item.getValue(), 0);
            }
        }

        return resultMap;
    }

    @Override
    public PageResult<QueryEntrustOrderResultDTO> getEntrustOrderList(QueryEntrustOrderParamDTO paramDTO) {
        paramDTO.setStatus(paramDTO.getStatus() >= 1 ? 1:0);
        int count = entrustOrderMapper.countByEntrustOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }

        return PageResult.create(paramDTO, count, entrustOrderMapper.getEntrustOrderList(paramDTO));
    }

    @Override
    public EntrustOrderDetailDTO getEntrustOrderDetailById(IdEnter enter) {
        EntrustOrderDetailDTO entrustOrderDetail = entrustOrderMapper.getEntrustOrderDetailById(enter.getId());
        RpsAssert.isNull(entrustOrderDetail, ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),
                ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());

        List<EntrustOrderProductDTO> productList = null;

        /**
         * 查询委托单产品信息 ---- 1：整车 2：组装件 3：部件
         */
        switch (entrustOrderDetail.getType()) {
            case 1:
                productList = entrustScooterBMapper.getEntrustOrderScooterByEntrustId(enter.getId());
                break;
            case 2:
                productList = entrustCombinBMapper.getEntrustOrderCombinByEntrustId(enter.getId());
                break;
            default:
                productList = entrustPartsBMapper.getEntrustOrderPartsByEntrustId(enter.getId());
                break;
        }

        entrustOrderDetail.setProductList(productList);

        return entrustOrderDetail;
    }

    @Override
    public GeneralResult entrustOrderDeliver(EntrustOrderDeliverParamDTO paramDTO) {
        /**
         * 数据完整性校验
         */
        EntrustOrderDetailDTO entrustOrderDetail = entrustOrderMapper.getEntrustOrderDetailById(paramDTO.getId());
        RpsAssert.isNull(entrustOrderDetail, ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isFalse(EntrustOrderStatusEnum.TO_BE_DELIVERED.getStatus().equals(entrustOrderDetail.getStatus()),
                ExceptionCodeEnums.ENTRUST_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ENTRUST_ORDER_STATUS_ERROR.getMessage());

        // 统计委托单已发货数量
        Integer deliveryQty;
        switch (entrustOrderDetail.getType()) {
            case 1:
                deliveryQty = entrustScooterBMapper.countEntrustScooterConsignorQtyByEntrustId(paramDTO.getId());
                break;
            case 2:
                deliveryQty = entrustCombinBMapper.countEntrustCombinConsignorQtyByEntrustId(paramDTO.getId());
                break;
            default:
                deliveryQty = entrustPartsBMapper.countEntrustPartsConsignorQtyByEntrustId(paramDTO.getId());
                break;
        }

        RpsAssert.isFalse(deliveryQty > entrustOrderDetail.getAlreadyConsignorQty(),
                ExceptionCodeEnums.DELIVERY_QTY_ERROR.getCode(), ExceptionCodeEnums.DELIVERY_QTY_ERROR.getMessage());

        /**
         * 使用编程式事务保证事务的一致性, 这里涉及到服务之间事务操作,现在没有分布式事务暂时只能保证自身服务事务一致
         */
        boolean result = transactionTemplate.execute(entrustOrderDeliverStatus -> {
            boolean flag = true;
            try {
                /**
                 * 更新委托单已发货数量
                 */
                OpeEntrustOrder opeEntrustOrder = OpeEntrustOrder.builder()
                        .id(paramDTO.getId())
                        .actualDeliveryTime(new Date())
                        .alreadyConsignorQty(deliveryQty)
                        .updatedBy(paramDTO.getUserId())
                        .updatedTime(new Date())
                        .build();
                entrustOrderMapper.updateEntrustOrder(opeEntrustOrder);

                /**
                 * 保存物流信息
                 */
                OpeLogisticsOrder opeLogisticsOrder = OpeLogisticsOrder.builder()
                        .id(idAppService.getId(SequenceName.OPE_LOGISTICS_ORDER))
                        .dr(0)
                        .entrustId(paramDTO.getId())
                        .logisticsCompany(paramDTO.getLogisticsCompany())
                        .logisticsNo(paramDTO.getLogisticsNo())
                        .remark(paramDTO.getRemark())
                        .createdBy(paramDTO.getUserId())
                        .createdTime(new Date())
                        .updatedBy(paramDTO.getUserId())
                        .updatedTime(new Date())
                        .build();
                opeLogisticsOrderMapper.insert(opeLogisticsOrder);

                /**
                 * 调用Aleks委托单发货后状态流转的逻辑
                 */
                IdEnter enter = new IdEnter(paramDTO.getId());
                rosEntrustOrderService.entrustOrderDeliver(enter);
            } catch (Exception e) {
                flag = false;
                log.error("【委托单发货失败】----{}", ExceptionUtils.getStackTrace(e));
                entrustOrderDeliverStatus.setRollbackOnly();
            }
            return flag;
        });

        RpsAssert.isFalse(result, ExceptionCodeEnums.ENTRUST_ORDER_DELIVER_FAILED.getCode(),
                ExceptionCodeEnums.ENTRUST_ORDER_DELIVER_FAILED.getMessage());

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        Long userId = paramDTO.getUserId();
        Integer qty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? 1 : paramDTO.getQty();

        // 避免重复扫码发货
        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            OpeEntrustProductSerialNum opeEntrustProductSerialNum = entrustProductSerialNumMapper.getEntrustProductSerialNumBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNotNull(opeEntrustProductSerialNum, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());
        }

        /**
         * 更新委托单产品实际发货数量
         */
        switch (paramDTO.getProductType()) {
            case 1:
                OpeEntrustScooterB opeEntrustScooterB = entrustScooterBMapper.getEntrustScooterById(paramDTO.getProductId());
                // 保存扫码序列号信息到委托单表中
                String serialNum = opeEntrustScooterB.getDef1();
                if (StringUtils.isNotBlank(serialNum)) {
                    serialNum += "," + paramDTO.getSerialNum();
                } else {
                    serialNum = paramDTO.getSerialNum();
                }
                opeEntrustScooterB.setDef1(serialNum);
                opeEntrustScooterB.setConsignorQty(opeEntrustScooterB.getConsignorQty() + 1); // 扫一下数量+1
                opeEntrustScooterB.setUpdatedBy(userId);
                opeEntrustScooterB.setUpdatedTime(new Date());
                entrustScooterBMapper.updateEntrustScooter(opeEntrustScooterB);
                break;
            case 2:
                OpeEntrustCombinB opeEntrustCombinB = entrustCombinBMapper.getEntrustCombinById(paramDTO.getProductId());
                opeEntrustCombinB.setConsignorQty(opeEntrustCombinB.getConsignorQty() + 1);
                opeEntrustCombinB.setUpdatedBy(userId);
                opeEntrustCombinB.setUpdatedTime(new Date());
                entrustCombinBMapper.updateEntrustCombin(opeEntrustCombinB);
                break;
            default:
                OpeEntrustPartsB opeEntrustPartsB = entrustPartsBMapper.getEntrustPartsById(paramDTO.getProductId());
                RpsAssert.isTrue(qty > opeEntrustPartsB.getQty(), ExceptionCodeEnums.DELIVERY_QTY_ERROR.getCode(),
                        ExceptionCodeEnums.DELIVERY_QTY_ERROR.getMessage());

                if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
                    opeEntrustPartsB.setConsignorQty(opeEntrustPartsB.getConsignorQty() + qty);
                } else {
                    opeEntrustPartsB.setConsignorQty(qty);
                }
                opeEntrustPartsB.setUpdatedBy(userId);
                opeEntrustPartsB.setUpdatedTime(new Date());
                entrustPartsBMapper.updateEntrustPartsB(opeEntrustPartsB);
                break;
        }


        /**
         * 保存委托单产品序列号信息(有码产品时保存)
         */
        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            OpeEntrustProductSerialNum entrustProductSerialNum = new OpeEntrustProductSerialNum();
            entrustProductSerialNum.setId(idAppService.getId(SequenceName.OPE_ENTRUST_PRODUCT_SERIAL_NUM));
            entrustProductSerialNum.setRelationId(paramDTO.getProductId());
            entrustProductSerialNum.setRelationType(paramDTO.getProductType());
            entrustProductSerialNum.setLot(paramDTO.getLot());
            entrustProductSerialNum.setIdClass(StringUtils.isNotBlank(paramDTO.getSerialNum()) ? 1 : 0);
            entrustProductSerialNum.setProductId(paramDTO.getBomId());
            entrustProductSerialNum.setProductType(paramDTO.getProductType());
            entrustProductSerialNum.setSerialNum(paramDTO.getSerialNum());
            entrustProductSerialNum.setQty(qty);
            entrustProductSerialNum.setCreatedBy(userId);
            entrustProductSerialNum.setCreatedTime(new Date());
            entrustProductSerialNum.setUpdatedBy(userId);
            entrustProductSerialNum.setUpdatedTime(new Date());
            entrustProductSerialNumMapper.insertEntrustProductSerialNum(entrustProductSerialNum);
        }

        return new GeneralResult(paramDTO.getRequestId());
    }

}
