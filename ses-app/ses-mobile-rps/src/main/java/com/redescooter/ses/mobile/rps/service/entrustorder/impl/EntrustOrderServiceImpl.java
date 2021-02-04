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
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.entrustorder.EntrustOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @DubboReference
    private IdAppService idAppService;
    @DubboReference
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
    private ProductionPartsMapper partsMapper;


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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult entrustOrderDeliver(EntrustOrderDeliverParamDTO paramDTO) {
        /**
         * 数据完整性校验
         */
        OpeEntrustOrder opeEntrustOrder = entrustOrderMapper.getEntrustOrderById(paramDTO.getId());
        RpsAssert.isNull(opeEntrustOrder, ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isFalse(EntrustOrderStatusEnum.TO_BE_DELIVERED.getStatus().equals(opeEntrustOrder.getEntrustStatus()),
                ExceptionCodeEnums.ENTRUST_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ENTRUST_ORDER_STATUS_ERROR.getMessage());

        // 统计委托单已发货数量
        Integer deliveryQty;
        switch (opeEntrustOrder.getEntrustType()) {
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

        RpsAssert.isFalse(deliveryQty > opeEntrustOrder.getAlreadyConsignorQty(),
                ExceptionCodeEnums.DELIVERY_QTY_ERROR.getCode(), ExceptionCodeEnums.DELIVERY_QTY_ERROR.getMessage());

        /**
         * 更新委托单已发货数量
         */
//        opeEntrustOrder.setEntrustStatus(ConsignOrderStatusEnums.BE_SIGNED.getValue());
//        opeEntrustOrder.setActualDeliveryTime(new Date());
//        opeEntrustOrder.setAlreadyConsignorQty(deliveryQty);
//        opeEntrustOrder.setUpdatedBy(paramDTO.getUserId());
//        opeEntrustOrder.setUpdatedTime(new Date());
//        entrustOrderMapper.updateEntrustOrder(opeEntrustOrder);

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

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        // 无码产品不填写扫码数量时抛出异常
        RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQty(),
                ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getCode(), ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getMessage());
        RpsAssert.isTrue(paramDTO.getIdClass() && StringUtils.isBlank(paramDTO.getSerialNum()),
                ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getMessage());

        Long userId = paramDTO.getUserId();
        Integer qty = paramDTO.getIdClass() ? 1 : paramDTO.getQty();

        // 避免重复扫码发货
        if (paramDTO.getIdClass()) {
            OpeEntrustProductSerialNum opeEntrustProductSerialNum = entrustProductSerialNumMapper.getEntrustProductSerialNumBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNotNull(opeEntrustProductSerialNum, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());
        }

        /**
         * 更新委托单产品实际发货数量
         */
        String serialNum = null;
        switch (paramDTO.getProductType()) {
            case 1:
                // 车辆和组装件一定是有码的,如果传的参数是无码,说明参数传递有误
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeEntrustScooterB opeEntrustScooterB = entrustScooterBMapper.getEntrustScooterById(paramDTO.getProductId());
                RpsAssert.isNull(opeEntrustScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // 保存扫码序列号信息到委托单表中
                serialNum = opeEntrustScooterB.getDef1();
                if (StringUtils.isNotBlank(serialNum)) {
                    serialNum += "," + paramDTO.getSerialNum();
                } else {
                    serialNum = paramDTO.getSerialNum();
                }
                opeEntrustScooterB.setConsignorQty(opeEntrustScooterB.getConsignorQty() + 1); // 扫一下数量+1
                opeEntrustScooterB.setUpdatedBy(userId);
                opeEntrustScooterB.setUpdatedTime(new Date());
                opeEntrustScooterB.setDef1(serialNum);
                entrustScooterBMapper.updateEntrustScooter(opeEntrustScooterB);
                break;
            case 2:
                // 车辆和组装件一定是有码的,如果传的参数是无码,说明参数传递有误
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeEntrustCombinB opeEntrustCombinB = entrustCombinBMapper.getEntrustCombinById(paramDTO.getProductId());
                RpsAssert.isNull(opeEntrustCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                serialNum = opeEntrustCombinB.getDef1();
                if (StringUtils.isNotBlank(serialNum)) {
                    serialNum += "," + paramDTO.getSerialNum();
                } else {
                    serialNum = paramDTO.getSerialNum();
                }
                opeEntrustCombinB.setConsignorQty(opeEntrustCombinB.getConsignorQty() + 1);
                opeEntrustCombinB.setUpdatedBy(userId);
                opeEntrustCombinB.setUpdatedTime(new Date());
                opeEntrustCombinB.setDef1(serialNum);
                entrustCombinBMapper.updateEntrustCombin(opeEntrustCombinB);
                break;
            default:
                RpsAssert.isBlank(paramDTO.getPartsNo(), ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getMessage());
                // 校验部件是否有序列号标识跟入参传递的是否一致
                Integer idClass = partsMapper.getPartsIdClassById(paramDTO.getBomId(), paramDTO.getPartsNo());
                RpsAssert.isNull(idClass, ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                boolean flag = 0 == idClass ? false : true;
                RpsAssert.isFalse(paramDTO.getIdClass() == flag, ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeEntrustPartsB opeEntrustPartsB = entrustPartsBMapper.getEntrustPartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeEntrustPartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // 有无码调整实际发货数量
                if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
                    opeEntrustPartsB.setConsignorQty(opeEntrustPartsB.getConsignorQty() + qty);
                } else {
                    // 限制无码产品重复质检, 并且无码产品输入的入库数量必须和入库数量一致
                    RpsAssert.isTrue(!qty.equals(opeEntrustPartsB.getQty()),ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(),
                            ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());
                    RpsAssert.isTrue(opeEntrustPartsB.getConsignorQty() > 0,ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                            ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());

                    opeEntrustPartsB.setConsignorQty(qty);
                }

                serialNum = opeEntrustPartsB.getDef1();
                if (StringUtils.isNotBlank(serialNum)) {
                    serialNum += "," + paramDTO.getSerialNum();
                } else {
                    serialNum = paramDTO.getSerialNum();
                }
                opeEntrustPartsB.setUpdatedBy(userId);
                opeEntrustPartsB.setUpdatedTime(new Date());
                opeEntrustPartsB.setDef1(serialNum);
                entrustPartsBMapper.updateEntrustPartsB(opeEntrustPartsB);
                break;
        }


        /**
         * 保存委托单产品序列号信息(有码产品时保存)
         */
        if (paramDTO.getIdClass()) {
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
