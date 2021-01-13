package com.redescooter.ses.mobile.rps.service.qc.impl;

import com.alibaba.fastjson.JSONArray;
import com.redescooter.ses.api.common.enums.qc.QcTemplateProductTypeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.outbound.OutBoundOrderStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcItemMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderQcTraceMapper;
import com.redescooter.ses.mobile.rps.dao.invoice.InvoiceProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.order.OrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWarehouseOrderMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.outwhorder.OutWhScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionQualityTemplateMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.qc.QcService;
import com.redescooter.ses.mobile.rps.vo.outwhorder.OutWhOrderProductDetailDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QcProductResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.SaveQcResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.ProductQcTemplateDTO;
import com.redescooter.ses.mobile.rps.vo.qc.ProductQcTemplateResultDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateParamDTO;
import com.redescooter.ses.mobile.rps.vo.qc.QueryQcTemplateResultDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2021/1/12 17:59
 */
@Slf4j
@Service
public class QcServiceImpl implements QcService {

    @Reference
    private IdAppService idAppService;
    @Resource
    private OpeOrderQcItemMapper opeOrderQcItemMapper;
    @Resource
    private OpeOrderQcTraceMapper opeOrderQcTraceMapper;
    @Resource
    private OrderSerialBindMapper orderSerialBindMapper;
    @Resource
    private ProductionQualityTemplateMapper templateMapper;
    @Resource
    private ProductionScooterBomMapper scooterBomMapper;
    @Resource
    private ProductionCombinBomMapper combinBomMapper;
    @Resource
    private ProductionPartsMapper partsMapper;
    @Resource
    private InWhouseScooterBMapper inWhouseScooterBMapper;
    @Resource
    private InWhouseCombinBMapper inWhouseCombinBMapper;
    @Resource
    private InWhousePartsBMapper inWhousePartsBMapper;
    @Resource
    private OutWhScooterBMapper outWhScooterBMapper;
    @Resource
    private OutWhCombinBMapper outWhCombinBMapper;
    @Resource
    private OutWhPartsBMapper outWhPartsBMapper;
    @Resource
    private OutWarehouseOrderMapper outWarehouseOrderMapper;
    @Resource
    private InvoiceProductSerialNumMapper invoiceProductSerialNumMapper;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public QueryQcTemplateResultDTO getQcTemplateByIdAndType(QueryQcTemplateParamDTO paramDTO) {
        QueryQcTemplateResultDTO resultDTO = new QueryQcTemplateResultDTO();

        /**
         * 入库单和出库单区别在于：如果是原料入库单的时候ope_order_serial_bind表是没有数据的,这个时候只能通过扫码得到的
         * 部件号去ope_production_parts表中查询部件的信息(原料入库只会有部件), 至于出库单ope_order_serial_bind这张表肯定是有数据的
         */
        OpeOrderSerialBind opeOrderSerialBind = null;
        if (ProductTypeEnums.SCOOTER.getValue().equals(paramDTO.getProductType())
                || ProductTypeEnums.COMBINATION.getValue().equals(paramDTO.getProductType())) {
            opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());

            RpsAssert.isNull(opeOrderSerialBind, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
        }

        /**
         * 根据入参productType调整为质检模板的产品类型
         * -productionId就理解成
         */
        Long productionId;
        Integer productionType;
        switch (paramDTO.getProductType()) {
            case 1:
                OpeProductionScooterBom opeProductionScooterBom = scooterBomMapper.getScooterBomById(opeOrderSerialBind.getProductId());
                resultDTO.setName(opeProductionScooterBom.getEnName());

                productionId = opeProductionScooterBom.getId();
                productionType = QcTemplateProductTypeEnum.SCOOTER.getType();
                break;
            case 2 :
                OpeProductionCombinBom opeProductionCombinBom = combinBomMapper.getCombinBomById(opeOrderSerialBind.getProductId());
                resultDTO.setName(opeProductionCombinBom.getCnName());

                productionId = opeProductionCombinBom.getId();
                productionType = QcTemplateProductTypeEnum.COMBINATION.getType();
                break;
            default:
                OpeProductionParts opeProductionParts = partsMapper.getProductionPartsByPartsNo(paramDTO.getPartsNo());
                resultDTO.setName(opeProductionParts.getCnName());

                productionId = opeProductionParts.getId();
                productionType = QcTemplateProductTypeEnum.PARTS.getType();
                break;
        }

        List<ProductQcTemplateDTO> productQcTemplateList = templateMapper.getQcTemplateByProductIdAndType(productionId, productionType);
        RpsAssert.isEmpty(productQcTemplateList, ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(),
                ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());

        List<Long> templateIdList = productQcTemplateList.stream().map(ProductQcTemplateDTO::getId).collect(Collectors.toList());

        List<ProductQcTemplateResultDTO> qcResultList = templateMapper.getProductQcTemplateResultByTemplateIds(templateIdList);
        RpsAssert.isEmpty(qcResultList, ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(),
                ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());

        /**
         * {productQualityTempateId, List<ProductQcTemplateResultDTO>}
         */
        Map<Long, List<ProductQcTemplateResultDTO>> templateBMap = qcResultList.stream().collect(
                Collectors.groupingBy(ProductQcTemplateResultDTO::getTemplateId)
        );

        productQcTemplateList.forEach(template ->{
            template.setQcTemplateResultList(templateBMap.get(template.getId()));
        });

        resultDTO.setProductQcTemplateList(productQcTemplateList);
        return resultDTO;
    }

    @Override
    public GeneralResult saveQcResult(SaveQcResultParamDTO paramDTO) {
        boolean qcResultFlag = true;
        List<OpeOrderQcTrace> opeOrderQcTraceList = new ArrayList<>();

        List<QcProductResultDTO> qcProductResultList = null;
        try {
            qcProductResultList = JSONArray.parseArray(paramDTO.getSt(), QcProductResultDTO.class);
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(),ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        /**
         * 查询质检模板信息, 用于检查产品质检结果是否通过
         */
        QueryQcTemplateParamDTO queryParam = new QueryQcTemplateParamDTO();
        queryParam.setPartsNo(paramDTO.getPartsNo());
        queryParam.setSerialNum(paramDTO.getSerialNum());
        queryParam.setProductType(paramDTO.getProductType());

        QueryQcTemplateResultDTO qcTemplateResult = getQcTemplateByIdAndType(queryParam);
        List<ProductQcTemplateDTO> productQcTemplateList = qcTemplateResult.getProductQcTemplateList();

        /**
         * {templateId, List<ProductQcTemplateResultDTO>}
         */
        Map<Long, List<ProductQcTemplateResultDTO>> qcResultMap = productQcTemplateList.stream().collect(
                Collectors.toMap(ProductQcTemplateDTO::getId, t -> t.getQcTemplateResultList())
        );

        /**
         * {templateId, ProductQcTemplateDTO}
         */
        Map<Long, ProductQcTemplateDTO> qcTemplateMap = productQcTemplateList.stream().collect(
                Collectors.toMap(ProductQcTemplateDTO::getId, t -> t)
        );

        // 质检条目
        OpeOrderQcItem opeOrderQcItem = new OpeOrderQcItem();
        Long qcItemId = idAppService.getId(SequenceName.OPE_ORDER_QC_ITEM);
        opeOrderQcItem.setId(qcItemId);
        opeOrderQcItem.setRevision(1);
        opeOrderQcItem.setCreatedBy(paramDTO.getUserId());
        opeOrderQcItem.setCreatedTime(new Date());
        opeOrderQcItem.setUpdatedBy(paramDTO.getUserId());
        opeOrderQcItem.setUpdatedTime(new Date());

        /**
         * 检查质检结果是否通过
         */
        for (QcProductResultDTO qc : qcProductResultList) {
            // 组装质检记录数据
            OpeOrderQcTrace opeOrderQcTrace = new OpeOrderQcTrace();
            opeOrderQcTrace.setId(idAppService.getId(SequenceName.OPE_ORDER_QC_TRACE));
            opeOrderQcTrace.setProductQcTemplateBId(qc.getTemplateResultId());
            opeOrderQcTrace.setProductQcTemplateId(qc.getTemplateId());
            opeOrderQcTrace.setProductQcTemplateName(qcTemplateMap.get(qc.getTemplateId()).getQcItemName());
            opeOrderQcTrace.setQcItemId(qcItemId);
            opeOrderQcTrace.setPicture(qc.getImageUrls());
            opeOrderQcTrace.setRevision(1);
            opeOrderQcTrace.setRemark(qc.getRemark());
            opeOrderQcTrace.setCreatedTime(new Date());
            opeOrderQcTrace.setUpdatedTime(new Date());

            for (ProductQcTemplateResultDTO result : qcResultMap.get(qc.getTemplateId())) {
                if (qc.getTemplateResultId().equals(result.getId())) {
                    opeOrderQcTrace.setProductQcTemplateBName(result.getQcResult());
                    if (!result.getPassFlag()) {
                        qcResultFlag = false;
                    }
                }
            }
            opeOrderQcTraceList.add(opeOrderQcTrace);
        }

        // 质检结果 pass/ng
        opeOrderQcItem.setQcResult(qcResultFlag ? 1 : 2);

        OpeOrderSerialBind opeOrderSerialBind = orderSerialBindMapper.getOrderSerialBindBySerialNum(paramDTO.getSerialNum());
        RpsAssert.isNull(opeOrderSerialBind, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

        /**
         * 保存订单序列号绑定信息
         * [入库单]往ope_order_serial_bind表添加数据
         * [出库单]往ope_invoice_product_serial_num表中添加数据, 数据来源于ope_order_serial_bind表中
         */
        if (1 == paramDTO.getType()) {
            /**
             * 入库单 start
             */
            OpeOrderSerialBind opeOrderSerialBindNew = new OpeOrderSerialBind();

            /**
             * 部件入库单表示当前是入原料库,因为是第一次入库质检此时ope_order_serial_bind表中是没有数据的,所以这个时候只能去
             * 入库单部件表中查询数据,或者查询部件表也行,不过部件表拿不到子单据id
             */
            if (ProductTypeEnums.PARTS.getValue().equals(paramDTO.getProductType())) {
                OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsByPartsNoAndInWhId(paramDTO.getPartsNo(),
                        paramDTO.getOrderId());

                RpsAssert.isNull(opeInWhousePartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                opeOrderSerialBindNew.setOrderBId(opeInWhousePartsB.getPartsId());
                opeOrderSerialBindNew.setOrderType(OrderTypeEnums.SHIPPING.getValue());
                opeOrderSerialBindNew.setProductId(opeInWhousePartsB.getId());
                opeOrderSerialBindNew.setProductType(paramDTO.getProductType());
            } else {
                BeanUtils.copyProperties(opeOrderSerialBind, opeOrderSerialBindNew);
            }

            // 订单序列号绑定表(这张表放的都是入库单质检完成所产生的数据)
            Long orderSerialId = idAppService.getId(SequenceName.OPE_ORDER_SERIAL_BIND);
            opeOrderSerialBindNew.setId(orderSerialId);
            opeOrderSerialBindNew.setSerialNum(String.format("%s-TEST", System.currentTimeMillis()));
            opeOrderSerialBindNew.setLot(paramDTO.getLot());
            opeOrderSerialBindNew.setCreatedBy(paramDTO.getUserId());
            opeOrderSerialBindNew.setCreatedTime(new Date());
            opeOrderSerialBindNew.setUpdatedBy(paramDTO.getUserId());
            opeOrderSerialBindNew.setUpdatedTime(new Date());

            // 质检条目表
            BeanUtils.copyProperties(opeOrderSerialBindNew, opeOrderQcItem);
            opeOrderQcItem.setId(qcItemId);
            opeOrderQcItem.setOrderSerialId(orderSerialId);
            opeOrderQcItem.setOrderType(OrderTypeEnums.SHIPPING.getValue());
        } else {
            /**
             * 出库单 start
             */

            // 出库单产品序列号绑定表(它表面上是发货单的产品序列号绑定表,背地里却是出库单的0.0)
            OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = new OpeInvoiceProductSerialNum();
            BeanUtils.copyProperties(opeOrderSerialBind, opeInvoiceProductSerialNum);

            opeInvoiceProductSerialNum.setId(idAppService.getId(SequenceName.OPE_INVOICE_PRODUCT_SERIAL_NUM));
            opeInvoiceProductSerialNum.setRelationId(opeOrderSerialBind.getOrderBId());
            opeInvoiceProductSerialNum.setRelationType(paramDTO.getProductType());
            opeInvoiceProductSerialNum.setCreatedBy(paramDTO.getUserId());
            opeInvoiceProductSerialNum.setCreatedTime(new Date());
            opeInvoiceProductSerialNum.setUpdatedBy(paramDTO.getUserId());
            opeInvoiceProductSerialNum.setUpdatedTime(new Date());

            // 质检条目表
            BeanUtils.copyProperties(opeOrderSerialBind, opeOrderQcItem);
            opeOrderQcItem.setId(qcItemId);
            opeOrderQcItem.setOrderSerialId(opeOrderSerialBind.getId());
            opeOrderQcItem.setOrderType(OrderTypeEnums.OUTBOUND.getValue());
        }


        // TODO 需要把生成的序列号返回给App那边,用于打印使用(只针对于入库质检)

        return null;
    }


    /**
     * TODO 待完善
     * 保存质检结果编程式事务方法
     * @param opeOrderQcItem
     * @param opeOrderSerialBind
     * @param opeOrderQcTraceList
     * @param userId
     */
    private void saveQcResultTransaction(OpeOrderQcItem opeOrderQcItem, OpeOrderSerialBind opeOrderSerialBind,
                                         List<OpeOrderQcTrace> opeOrderQcTraceList, Long userId) {
        boolean result = transactionTemplate.execute(qcStatus -> {
            boolean flag = true;
            try {
                Long outWhId = null;
                OutWhOrderProductDetailDTO outWhOrderProduct = null;
                /**
                 * 更新出库单产品质检数量, 只有质检成功时才会更新
                 */
                if (opeOrderQcItem.getQcResult() == 1) {
                    switch (opeOrderSerialBind.getProductType()) {
                        case 1:
                            outWhOrderProduct = outWhScooterBMapper.getScooterProductDetailByProductId(opeOrderSerialBind.getOrderBId());
                            OpeOutWhScooterB opeOutWhScooterB = new OpeOutWhScooterB();
                            opeOutWhScooterB.setId(outWhOrderProduct.getId());
                            opeOutWhScooterB.setAlreadyOutWhQty(outWhOrderProduct.getQcQty() + 1);
                            opeOutWhScooterB.setUpdatedTime(new Date());

                            outWhId = outWhOrderProduct.getOutWhId();
                            outWhScooterBMapper.updateOutWhScooterB(opeOutWhScooterB);
                            break;
                        case 2:
                            outWhOrderProduct = outWhCombinBMapper.getCombinProductDetailByProductId(opeOrderSerialBind.getOrderBId());
                            OpeOutWhCombinB opeOutWhCombinB = new OpeOutWhCombinB();
                            opeOutWhCombinB.setId(outWhOrderProduct.getId());
                            opeOutWhCombinB.setAlreadyOutWhQty(outWhOrderProduct.getQcQty() + 1);
                            opeOutWhCombinB.setUpdatedTime(new Date());

                            outWhId = outWhOrderProduct.getOutWhId();
                            outWhCombinBMapper.updateOutWhCombinB(opeOutWhCombinB);
                            break;
                        default:
                            outWhOrderProduct = outWhPartsBMapper.getPartsProductDetailByProductId(opeOrderSerialBind.getOrderBId());
                            OpeOutWhPartsB opeOutWhPartsB = new OpeOutWhPartsB();
                            opeOutWhPartsB.setId(outWhOrderProduct.getId());
                            opeOutWhPartsB.setAlreadyOutWhQty(outWhOrderProduct.getQcQty() + 1);
                            opeOutWhPartsB.setUpdatedTime(new Date());

                            outWhId = outWhOrderProduct.getOutWhId();
                            outWhPartsBMapper.updateOutWhPartsB(opeOutWhPartsB);
                            break;
                    }

                    /**
                     * 更新出库单状态为 【部分出库】
                     */
                    int qcQty = outWarehouseOrderMapper.getOutWhOrderQcQtyById(outWhId);
                    outWarehouseOrderMapper.updateOutWarehouseOrder(buildOutWhouseOrder(outWhId,
                            OutBoundOrderStatusEnums.PARTIAL_DELIVERY.getValue(), qcQty + 1,null, userId));
                }

                /**
                 * 保存出库单产品序列号信息
                 */
                OpeInvoiceProductSerialNum opeInvoiceProductSerialNum = new OpeInvoiceProductSerialNum();
                BeanUtils.copyProperties(opeOrderSerialBind, opeInvoiceProductSerialNum);

                opeInvoiceProductSerialNum.setId(idAppService.getId(SequenceName.OPE_INVOICE_PRODUCT_SERIAL_NUM));
                opeInvoiceProductSerialNum.setRelationId(opeOrderSerialBind.getOrderBId());
                opeInvoiceProductSerialNum.setRelationType(opeOrderSerialBind.getProductType());
                opeInvoiceProductSerialNum.setCreatedBy(userId);
                opeInvoiceProductSerialNum.setCreatedTime(new Date());
                opeInvoiceProductSerialNum.setUpdatedBy(userId);
                opeInvoiceProductSerialNum.setUpdatedTime(new Date());
                invoiceProductSerialNumMapper.insertInvoiceProductSerialNum(opeInvoiceProductSerialNum);

                /**
                 * 保存质检记录
                 */
                opeOrderQcItemMapper.insertOrUpdate(opeOrderQcItem);
                opeOrderQcTraceMapper.batchInsert(opeOrderQcTraceList);

                /**
                 * 质检失败需要将质检的产品丢入不合格品库中去,通对库存做扣减操作
                 */

            } catch (Exception e) {
                flag = false;
                log.error("【保存质检结果失败】----{}", ExceptionUtils.getStackTrace(e));
                qcStatus.setRollbackOnly();
            }
            return flag;
        });

        // 手动抛出编程式事务异常
        RpsAssert.isFalse(result, ExceptionCodeEnums.QC_ERROR.getCode(), ExceptionCodeEnums.QC_ERROR.getMessage());
    }

    /**
     * 组装出库单信息
     * @param id 出库单id
     * @param status 出库单状态,参照枚举类：{@link OutBoundOrderStatusEnums}
     * @param qcQty 已质检数量
     * @param alreadyOutWhQty 已出库数量
     * @param userId 用户id
     * @return
     */
    private OpeOutWhouseOrder buildOutWhouseOrder(Long id, Integer status, Integer qcQty, Integer alreadyOutWhQty, Long userId) {
        OpeOutWhouseOrder opeOutWhouseOrder = new OpeOutWhouseOrder();
        opeOutWhouseOrder.setId(id);
        opeOutWhouseOrder.setOutWhStatus(status);
        opeOutWhouseOrder.setQcQty(qcQty);
        opeOutWhouseOrder.setAlreadyOutWhQty(alreadyOutWhQty);
        opeOutWhouseOrder.setUpdatedBy(userId);
        opeOutWhouseOrder.setUpdatedTime(new Date());
        if (null != alreadyOutWhQty) {
            opeOutWhouseOrder.setOutStockTime(new Date());
        }

        return opeOutWhouseOrder;
    }

}
