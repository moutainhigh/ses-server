package com.redescooter.ses.mobile.rps.service.entrustorder.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.redescooter.ses.mobile.rps.dao.base.OpeInvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustOrderMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustScooterBMapper;
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
    private OpeInvoiceProductSerialNumMapper opeInvoiceProductSerialNumMapper;
    @Resource
    private OpeEntrustProductSerialNumMapper entrustProductSerialNumMapper;
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

    @Override
    public GeneralResult entrustOrderDeliver(IdEnter enter) {
        /**
         * 调用Aleks委托单发货后状态流转的逻辑
         */
        rosEntrustOrderService.entrustOrderDeliver(enter);

        return new GeneralResult(enter.getRequestId());
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
         * 委托单产品序列号信息来源于发货单产品序列号表 ---- 发货单产品序列号表数据来源于出库单逻辑那边
         */
        QueryWrapper<OpeInvoiceProductSerialNum> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OpeInvoiceProductSerialNum.COL_SERIAL_NUM, paramDTO.getSerialNum())
                    .eq(OpeInvoiceProductSerialNum.COL_DR, 0);

        OpeInvoiceProductSerialNum invoiceProductSerialNum = opeInvoiceProductSerialNumMapper.selectOne(queryWrapper);

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
        BeanUtils.copyProperties(invoiceProductSerialNum, entrustProductSerialNum);

        entrustProductSerialNum.setId(idAppService.getId(SequenceName.OPE_ENTRUST_PRODUCT_SERIAL_NUM));
        entrustProductSerialNum.setQty(1);
        entrustProductSerialNum.setCreatedTime(new Date());
        entrustProductSerialNum.setUpdatedTime(new Date());
        entrustProductSerialNumMapper.insertOrUpdate(entrustProductSerialNum);

        /**
         * 更新委托单产品实际发货数量
         * 这里可以不再去做 [实际发货数量 > 应发货数量] 的判断,上面已经对已扫码再次扫码的产品做限制了
         */
        Long entrustOrderId = null;
        switch (invoiceProductSerialNum.getProductType()) {
            case 1:
                OpeEntrustScooterB opeEntrustScooterB = entrustScooterBMapper.getEntrustScooterById(entrustProductSerialNum.getRelationId());
                opeEntrustScooterB.setConsignorQty(opeEntrustScooterB.getConsignorQty() + 1); // 扫一下数量+1
                opeEntrustScooterB.setUpdatedBy(entrustProductSerialNum.getUpdatedBy());
                opeEntrustScooterB.setUpdatedTime(new Date());

                entrustOrderId = opeEntrustScooterB.getEntrustId();
                entrustScooterBMapper.updateEntrustScooter(opeEntrustScooterB);
                break;
            case 2:
                OpeEntrustCombinB opeEntrustCombinB = entrustCombinBMapper.getEntrustCombinById(entrustProductSerialNum.getRelationId());
                opeEntrustCombinB.setConsignorQty(opeEntrustCombinB.getConsignorQty() + 1);
                opeEntrustCombinB.setUpdatedBy(entrustProductSerialNum.getUpdatedBy());
                opeEntrustCombinB.setUpdatedTime(new Date());

                entrustOrderId = opeEntrustCombinB.getEntrustId();
                entrustCombinBMapper.updateEntrustCombin(opeEntrustCombinB);
                break;
            default:
                OpeEntrustPartsB opeEntrustPartsB = entrustPartsBMapper.getEntrustPartsById(entrustProductSerialNum.getRelationId());
                opeEntrustPartsB.setConsignorQty(opeEntrustPartsB.getConsignorQty());
                opeEntrustPartsB.setUpdatedBy(entrustProductSerialNum.getUpdatedBy());
                opeEntrustPartsB.setUpdatedTime(new Date());

                entrustOrderId = opeEntrustPartsB.getEntrustId();
                entrustPartsBMapper.updateEntrustPartsB(opeEntrustPartsB);
                break;
        }

        // 更新委托单的已发货数量
        OpeEntrustOrder opeEntrustOrder = new OpeEntrustOrder();
        opeEntrustOrder.setId(entrustOrderId);
        opeEntrustOrder.setUpdatedTime(new Date());
        entrustOrderMapper.updateEntrustOrderAlreadyConsignorQty(opeEntrustOrder);

        return new GeneralResult(paramDTO.getRequestId());
    }

}
