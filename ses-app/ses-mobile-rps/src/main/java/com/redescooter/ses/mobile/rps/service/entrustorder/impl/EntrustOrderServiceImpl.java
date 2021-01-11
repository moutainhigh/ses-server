package com.redescooter.ses.mobile.rps.service.entrustorder.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.redescooter.ses.mobile.rps.dao.base.OpeEntrustProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeLogisticsOrderMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustOrderMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.order.OrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.entrustorder.EntrustOrderService;
import com.redescooter.ses.mobile.rps.vo.entrustorder.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    @Resource
    private EntrustOrderMapper entrustOrderMapper;
    @Resource
    private RosEntrustOrderService rosEntrustOrderService;
    @Resource
    private EntrustScooterBMapper entrustScooterBMapper;
    @Resource
    private EntrustCombinBMapper entrustCombinBMapper;
    @Resource
    private EntrustPartsBMapper entrustPartsBMapper;
    @Resource
    private OpeEntrustProductSerialNumMapper entrustProductSerialNumMapper;
    @Resource
    private OpeLogisticsOrderMapper opeLogisticsOrderMapper;
    @Resource
    private OrderSerialBindMapper orderSerialBindMapper;
    @Resource
    private IdAppService idAppService;


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
        int count = entrustOrderMapper.countByEntrustOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }

        return PageResult.create(paramDTO, count, entrustOrderMapper.getEntrustOrderList(paramDTO));
    }

    @Override
    public EntrustOrderDetailDTO getEntrustOrderDetailById(IdEnter enter) {
        EntrustOrderDetailDTO entrustOrderDetail = entrustOrderMapper.getEntrustOrderDetailById(enter.getId());
        if (null == entrustOrderDetail) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());
        }

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
        EntrustOrderDetailDTO entrustOrderDetail = entrustOrderMapper.getEntrustOrderDetailById(paramDTO.getId());
        RpsAssert.isNull(entrustOrderDetail, ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(!EntrustOrderStatusEnum.TO_BE_DELIVERED.getStatus().equals(entrustOrderDetail.getStatus()),
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

        RpsAssert.isTrue(!deliveryQty.equals(entrustOrderDetail.getAlreadyConsignorQty()),
                ExceptionCodeEnums.DELIVERY_QTY_ERROR.getCode(), ExceptionCodeEnums.DELIVERY_QTY_ERROR.getMessage());

        /**
         * 更新委托单已发货数量
         */
        OpeEntrustOrder opeEntrustOrder = OpeEntrustOrder.builder()
                .id(paramDTO.getId())
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
        opeLogisticsOrderMapper.insertOrUpdate(opeLogisticsOrder);

        /**
         * 调用Aleks委托单发货后状态流转的逻辑
         */
        IdEnter enter = new IdEnter(paramDTO.getId());
        rosEntrustOrderService.entrustOrderDeliver(enter);

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult updatePartActualDeliveryQty(UpdatePartActualDeliveryQtyParamDTO paramDTO) {
        OpeEntrustPartsB entrustPartsB = entrustPartsBMapper.getEntrustPartsById(paramDTO.getProductId());

        RpsAssert.isNull(entrustPartsB, ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isTrue(!paramDTO.getConsignorQty().equals(entrustPartsB.getQty()), ExceptionCodeEnums.DELIVERY_QTY_ERROR.getCode(),
                ExceptionCodeEnums.DELIVERY_QTY_ERROR.getMessage());

        /**
         * 修改委托单部件产品实际发货数量 -- 只针对于部件无码产品
         */
        OpeEntrustPartsB opeEntrustPartsB = new OpeEntrustPartsB();
        opeEntrustPartsB.setId(paramDTO.getProductId());
        opeEntrustPartsB.setConsignorQty(paramDTO.getConsignorQty());
        opeEntrustPartsB.setUpdatedBy(paramDTO.getUserId());
        opeEntrustPartsB.setUpdatedTime(new Date());
        entrustPartsBMapper.updateEntrustPartsB(opeEntrustPartsB);

        return new GeneralResult(paramDTO.getRequestId());
    }

    @Override
    public GeneralResult saveDeliverInfo(SaveProductDeliverInfoParamDTO paramDTO) {
        /**
         * 委托单发货的扫码流程主要涉及到了两张表：[ope_order_serial_bind]和[ope_invoice_product_serial_num]
         * [ope_order_serial_bind]：我扫码之后会通过二维码上面的序列号去这张表里面查找数据
         * [ope_invoice_product_serial_num]：扫码后会往这张表里面添加数据,这里业主要用于页面上的展示,比如每扫码一个产品
         * 页面上对应产品就会展示扫码过的序列号信息
         * ----出库单那边质检扫码逻辑跟委托单这边差不多----
         */
        OpeOrderSerialBind opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());

        /**
         * 已扫码无需再次扫码
         */
        QueryWrapper<OpeEntrustProductSerialNum> entrustProductSnQueryWrapper = new QueryWrapper<>();
        entrustProductSnQueryWrapper.eq(OpeEntrustProductSerialNum.COL_SERIAL_NUM, paramDTO.getSerialNum());
        entrustProductSnQueryWrapper.eq(OpeEntrustProductSerialNum.COL_DR, 0);

        OpeEntrustProductSerialNum opeEntrustProductSerialNum = entrustProductSerialNumMapper.selectOne(entrustProductSnQueryWrapper);
        RpsAssert.isNotNull(opeEntrustProductSerialNum, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());

        /**
         * 保存委托单产品序列号信息 -- 每扫码一次保存一次
         */
        OpeEntrustProductSerialNum entrustProductSerialNum = new OpeEntrustProductSerialNum();
        BeanUtils.copyProperties(opeOrderSerialBind, entrustProductSerialNum);

        entrustProductSerialNum.setId(idAppService.getId(SequenceName.OPE_ENTRUST_PRODUCT_SERIAL_NUM));
        entrustProductSerialNum.setRelationId(opeOrderSerialBind.getOrderBId());
        entrustProductSerialNum.setRelationType(opeOrderSerialBind.getProductType());
        entrustProductSerialNum.setQty(1);
        entrustProductSerialNum.setCreatedTime(new Date());
        entrustProductSerialNum.setUpdatedTime(new Date());
        entrustProductSerialNumMapper.insertOrUpdate(entrustProductSerialNum);

        /**
         * 更新委托单产品实际发货数量
         */
        switch (opeOrderSerialBind.getProductType()) {
            case 1:
                OpeEntrustScooterB opeEntrustScooterB = entrustScooterBMapper.getEntrustScooterById(entrustProductSerialNum.getRelationId());
                opeEntrustScooterB.setConsignorQty(opeEntrustScooterB.getConsignorQty() + 1); // 扫一下数量+1
                opeEntrustScooterB.setUpdatedBy(entrustProductSerialNum.getUpdatedBy());
                opeEntrustScooterB.setUpdatedTime(new Date());

                entrustScooterBMapper.updateEntrustScooter(opeEntrustScooterB);
                break;
            case 2:
                OpeEntrustCombinB opeEntrustCombinB = entrustCombinBMapper.getEntrustCombinById(entrustProductSerialNum.getRelationId());
                opeEntrustCombinB.setConsignorQty(opeEntrustCombinB.getConsignorQty() + 1);
                opeEntrustCombinB.setUpdatedBy(entrustProductSerialNum.getUpdatedBy());
                opeEntrustCombinB.setUpdatedTime(new Date());

                entrustCombinBMapper.updateEntrustCombin(opeEntrustCombinB);
                break;
            default:
                OpeEntrustPartsB opeEntrustPartsB = entrustPartsBMapper.getEntrustPartsById(entrustProductSerialNum.getRelationId());
                opeEntrustPartsB.setConsignorQty(opeEntrustPartsB.getConsignorQty() + 1);
                opeEntrustPartsB.setUpdatedBy(entrustProductSerialNum.getUpdatedBy());
                opeEntrustPartsB.setUpdatedTime(new Date());

                entrustPartsBMapper.updateEntrustPartsB(opeEntrustPartsB);
                break;
        }

        return new GeneralResult(paramDTO.getRequestId());
    }

}
