package com.redescooter.ses.mobile.rps.service.material.impl;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.bom.CurrencyUnitEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingEventEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.rps.QcTypeEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.material.MaterialServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.BussinessNumberService;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.material.MaterialService;
import com.redescooter.ses.mobile.rps.vo.bo.ReturnCompletePartDto;
import com.redescooter.ses.mobile.rps.vo.bo.RpsPartDetailDto;
import com.redescooter.ses.mobile.rps.vo.materialqc.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @ClassName:MaterialService
 * @description: MaterialService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 13:23
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialServiceMapper materialServiceMapper;

    @Autowired
    private OpePurchasService opePurchasService;

    @Autowired
    private OpePurchasBService opePurchasBService;

    @Autowired
    private OpePartQcTemplateService opePartQcTemplateService;

    @Autowired
    private OpePartQcTemplateBService opePartQcTemplateBService;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private BussinessNumberService bussinessNumberService;

    @Autowired
    private OpePurchasTraceService opePurchasTraceService;

    @Autowired
    private OpePurchasBQcService opePurchasBQcService;

    @Autowired
    private OpePurchasBQcItemService opePurchasBQcItemService;

    @Autowired
    private OpePurchasQcTraceService opePurchasQcTraceService;

    @Autowired
    private OpePurchasPaymentService opePurchasPaymentService;

    @Autowired
    private ReceiptTraceService receiptTraceService;

    @Autowired
    private OpePurchasLotTraceService opePurchasLotTraceService;

    @Reference
    private IdAppService idAppService;

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByStatus(GeneralEnter enter) {
        QueryWrapper<OpePurchas> opePurchasQueryWrapper = new QueryWrapper<>();
        opePurchasQueryWrapper.eq(OpePurchas.COL_DR, 0);
        opePurchasQueryWrapper.eq(OpePurchas.COL_STATUS, PurchasingStatusEnums.MATERIALS_QC.getValue());
        opePurchasQueryWrapper.ne(OpePurchas.COL_LAVE_WAIT_QC_TOTAL, 0);

        Map<String, Integer> map = Maps.newHashMap();
        map.put(QcTypeEnums.WAIT.getValue(), opePurchasService.count(opePurchasQueryWrapper));
        //查询质检 质检失败的主订单
        List<OpePurchasB> opePurchasBList = materialServiceMapper.qcFailPurchasBList(enter);
        if (CollectionUtils.isEmpty(opePurchasBList)) {
            map.put(QcTypeEnums.FAIL.getValue(), 0);
        }
        //质检失败 数量
        map.put(QcTypeEnums.FAIL.getValue(), opePurchasBList.stream().map(OpePurchasB::getPurchasId).collect(Collectors.toSet()).size());
        return map;
    }

    /**
     * 来料质检列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialQcListResult> list(PageEnter enter) {
        QueryWrapper<OpePurchas> opePurchasQueryWrapper = new QueryWrapper<>();
        opePurchasQueryWrapper.eq(OpePurchas.COL_STATUS, PurchasingStatusEnums.MATERIALS_QC.getValue());
        opePurchasQueryWrapper.eq(OpePurchas.COL_DR, 0);
        opePurchasQueryWrapper.ne(OpePurchas.COL_LAVE_WAIT_QC_TOTAL, 0);
        int count = opePurchasService.count(opePurchasQueryWrapper);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, materialServiceMapper.list(enter));
    }

    /**
     * 质检失败列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialQcListResult> failList(PageEnter enter) {

        int count = materialServiceMapper.failListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, materialServiceMapper.failList(enter));
    }

    /**
     * 退回并完成
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult returnedCompleted(IdsEnter enter) {
        //入参校验
        List<OpePurchasB> opePurchasBList = new ArrayList<>(opePurchasBService.listByIds(enter.getIds()));
        if (CollectionUtils.isEmpty(opePurchasBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        List<OpePurchas> opePurchasList = new ArrayList<>(opePurchasService.listByIds(opePurchasBList.stream().map(OpePurchasB::getPurchasId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opePurchasList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        opePurchasList.forEach(item -> {
            if (!StringUtils.equals(item.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            if (item.getLaveWaitQcTotal() != 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_ORDER_HAS_EXIST_PART_NOT_QC.getCode(), ExceptionCodeEnums.PURCHAS_ORDER_HAS_EXIST_PART_NOT_QC.getMessage());
            }
        });
        //查询所有主订单相关联的子表数据
        Collection<OpePurchasB> checkOpePurchasBList = opePurchasBService.list(new LambdaQueryWrapper<OpePurchasB>().in(OpePurchasB::getPurchasId,
                opePurchasList.stream().map(OpePurchas::getId).collect(Collectors.toList())));

        //查询qc 信息
        QueryWrapper<OpePurchasBQc> opePurchasBQcQueryWrapper = new QueryWrapper<>();
        opePurchasBQcQueryWrapper.in(OpePurchasBQc.COL_PURCHAS_B_ID, opePurchasBList.stream().map(OpePurchasB::getId).collect(Collectors.toList()));
        opePurchasBQcQueryWrapper.eq(OpePurchasBQc.COL_DR, 0);
        opePurchasBQcQueryWrapper.gt(OpePurchasBQc.COL_FAIL_COUNT, 0);
        List<OpePurchasBQc> purchasBQcList = opePurchasBQcService.list(opePurchasBQcQueryWrapper);

        if (CollectionUtils.isEmpty(purchasBQcList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.NO_QUALITY_INSPECTION_FIRST_QUALITY_INSPECTION.getCode(),
                    ExceptionCodeEnums.NO_QUALITY_INSPECTION_FIRST_QUALITY_INSPECTION.getMessage());
        }
        //查询质检结果 若无质检失败的部件 无需退货
        purchasBQcList.forEach(item -> {
            if (item.getFailCount() == 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_PASS_WITHOUT_RETURN.getCode(), ExceptionCodeEnums.QC_PASS_WITHOUT_RETURN.getMessage());
            }
        });

        Map<OpePurchasB, Integer> partMap = Maps.newHashMap();

        for (OpePurchasBQc item : purchasBQcList) {
            for (OpePurchasB purchasB : opePurchasBList) {
                if (item.getPurchasBId().equals(purchasB.getId()) && item.getFailCount() != 0) {
                    partMap.put(purchasB, item.getFailCount());
                }
            }
        }
        //查询退货部件价格
        List<RpsPartDetailDto> partDetailDtoList = materialServiceMapper.partDetailById(new ArrayList<>(opePurchasBList.stream().map(OpePurchasB::getPartId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(partDetailDtoList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        Map<OpePurchasB, BigDecimal> priceMap = Maps.newHashMap();
        partDetailDtoList.forEach(part -> {
            partMap.forEach((key, value) -> {
                if (key.getPartId().equals(part.getPartId())) {
                    priceMap.put(key, new BigDecimal(value).multiply(part.getPrice()));
                }
            });
        });

        //查询支付信息
        List<OpePurchasPayment> opePurchasPaymentList = opePurchasPaymentService.list(new LambdaQueryWrapper<OpePurchasPayment>().in(OpePurchasPayment::getPurchasId,
                opePurchasList.stream().map(OpePurchas::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opePurchasPaymentList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_NOT_EXIST.getMessage());
        }

        //封装退货时主订单的信息
        Map<Long, ReturnCompletePartDto> returnCompletePartDtoMap = new HashMap<>();

        //对采购子订单 价格、数量 处理
        for (OpePurchas opePurchas : opePurchasList) {//退货金额
            BigDecimal retureTotalPrice = BigDecimal.ZERO;
            //退货数量
            int returnTotal = 0;
            //原始金额
            BigDecimal originalAmount = opePurchas.getTotalPrice();
            for (OpePurchasB item : opePurchasBList) {

                if (item.getPurchasId().equals(opePurchas.getId())) {
                    // 数量处理
                    for (Map.Entry<OpePurchasB, Integer> e : partMap.entrySet()) {
                        OpePurchasB key = e.getKey();
                        Integer value = e.getValue();
                        if (item.getPartId().equals(key.getPartId()) && item.getId().equals(key.getId())) {

                            item.setTotalCount(item.getTotalCount() - value);
                            item.setInWaitWhQty(item.getInWaitWhQty() - value);
                            returnTotal += value;
                        }
                    }

                    //价格处理
                    for (Map.Entry<OpePurchasB, BigDecimal> entry : priceMap.entrySet()) {
                        OpePurchasB priceKey = entry.getKey();
                        BigDecimal priceValue = entry.getValue();
                        if (item.getPartId().equals(priceKey.getPartId()) && item.getId().equals(priceKey.getId())) {
                            item.setTotalPrice(item.getTotalPrice().subtract(priceValue));
                            //主表金额维护
                            opePurchas.setTotalPrice(opePurchas.getTotalPrice().subtract(priceValue));
                            retureTotalPrice = retureTotalPrice.add(priceValue);
                        }
                    }
                    //子表数据维护
                    item.setQcStatus(QcStatusEnums.PASS.getValue());
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                }
            }

            for (OpePurchasPayment payment : opePurchasPaymentList) {
                if (payment.getPurchasId().equals(opePurchas.getId())) {
                    payment.setAmount(payment.getAmount().subtract(retureTotalPrice));
                    payment.setUpdatedBy(enter.getUserId());
                    payment.setUpdatedTime(new Date());
                }
            }


            ReturnCompletePartDto returnCompletePartDto = ReturnCompletePartDto.builder()
                    .id(opePurchas.getId())
                    .originalAmount(originalAmount)
                    .retureTotalPrice(retureTotalPrice)
                    .returnTotal(returnTotal)
                    .build();
            returnCompletePartDtoMap.put(opePurchas.getId(), returnCompletePartDto);
        }

        //抹除掉 质检失败和批次号建立的绑定关系
        List<OpePurchasBQcItem> opePurchasBQcItemList = opePurchasBQcItemService.list(new LambdaQueryWrapper<OpePurchasBQcItem>().in(OpePurchasBQcItem::getPurchasBQcId,
                purchasBQcList.stream().map(OpePurchasBQc::getId).collect(Collectors.toList())));
        if (CollectionUtils.isNotEmpty(opePurchasBQcItemList)) {
            opePurchasBQcItemList.forEach(item -> {
                if (item.getQcResult().equals(QcStatusEnums.FAIL.getValue())) {
                    item.setBatchNo(null);
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                }
            });
            opePurchasBQcItemService.updateBatchById(opePurchasBQcItemList);
        }

        //QC子表更新
        purchasBQcList.forEach(item -> {
            item.setFailCount(0);
            item.setTotalQualityInspected(item.getPassCount());
            item.setStatus(QcStatusEnums.PASS.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });


        List<OpePurchasB> checkPurchasList = new ArrayList<>();
        //形成 子订单校验集合
        checkOpePurchasBList.forEach(item -> {
            opePurchasBList.forEach(opePurchasB -> {
                if (item.getId().equals(opePurchasB.getId())) {
                    checkPurchasList.add(opePurchasB);
                } else {
                    checkPurchasList.add(item);
                }
            });
        });

        //主表数量、数量维护
        for (OpePurchas opePurchas : opePurchasList) {
            //校验每个主订单部件 是否全部通过质检
            Boolean updatePurchasStatus = Boolean.TRUE;

            for (OpePurchasB item : checkPurchasList) {
                if (item.getPurchasId().equals(opePurchas.getId())) {

                    //校验质检信息是否能 已经全部质检过
                    for (OpePurchasBQc purchasbqc : purchasBQcList) {
                        //同一个主订单下 判断所有子订单 是否 采购所有部件 是否已经通过质检  都通过时 修改主订单状态
                        if (item.getPurchasId().equals(opePurchas.getId())) {
                            if (!item.getTotalCount().equals(purchasbqc.getPassCount())) {
                                updatePurchasStatus = Boolean.FALSE;
                            }
                        }
                    }
                }

            }
            //修改主订单状态
            ReturnCompletePartDto returnCompletePartDto = returnCompletePartDtoMap.get(opePurchas.getId());
            if (updatePurchasStatus) {
                opePurchas.setStatus(PurchasingStatusEnums.RETURN.getValue());

                //节点
                String memo =
                        new StringBuffer(returnCompletePartDto.getOriginalAmount().toString() + CurrencyUnitEnums.FR.getName()).append(",").append(returnCompletePartDto.getRetureTotalPrice().toString() + CurrencyUnitEnums.FR.getName()).toString();
                SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
                BeanUtils.copyProperties(enter, saveNodeEnter);
                saveNodeEnter.setId(opePurchas.getId());
                saveNodeEnter.setStatus(PurchasingStatusEnums.RETURN.getValue());
                saveNodeEnter.setEvent(PurchasingEventEnums.RETURN.getValue());
                saveNodeEnter.setMemo(memo);
                receiptTraceService.savePurchasingNode(saveNodeEnter);
            }
            opePurchas.setInWaitWhTotal(opePurchas.getInWaitWhTotal() - returnCompletePartDto.getReturnTotal());
            opePurchas.setTotalQty(opePurchas.getTotalQty() - returnCompletePartDto.getReturnTotal());
            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
        }


        //更新付款价格
        opePurchasPaymentService.updateBatch(opePurchasPaymentList);

        //采购单更新
        opePurchasService.updateBatch(opePurchasList);

        //子表数据更新
        opePurchasBService.updateBatchById(opePurchasBList);
        //Qc 质检通过
        opePurchasBQcService.updateBatchById(purchasBQcList);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 继续质检
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult againQc(IdsEnter enter) {
        //入参校验
        Collection<OpePurchasB> opePurchasBList = opePurchasBService.listByIds(enter.getIds());
        if (CollectionUtils.isEmpty(opePurchasBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //查询对应的主订单
        Collection<OpePurchas> opePurchasList = opePurchasService.listByIds(new ArrayList<>(opePurchasBList.stream().map(OpePurchasB::getPurchasId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opePurchasList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //主订单状态过滤
        opePurchasList.forEach(item -> {
            if (!StringUtils.equals(item.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }
        });

        //1.质检表数据获取
        List<OpePurchasBQc> opePurchasBQcList = opePurchasBQcService.list(new LambdaQueryWrapper<OpePurchasBQc>().in(OpePurchasBQc::getPurchasBId,
                opePurchasBList.stream().map(OpePurchasB::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opePurchasBQcList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PLEASE_SCAN_THE_CODE_FIRST.getCode(), ExceptionCodeEnums.PLEASE_SCAN_THE_CODE_FIRST.getMessage());
        }
        //筛选 是否有质检失败的产品
        int failConut = 0;
        if (CollectionUtils.isEmpty(opePurchasBQcList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PLEASE_SCAN_THE_CODE_FIRST.getCode(), ExceptionCodeEnums.PLEASE_SCAN_THE_CODE_FIRST.getMessage());
        }

        //校验质检结果表 数据是否要进行修改
        for (OpePurchasB purchasB : opePurchasBList) {
            int initFailTotal = 0;
            for (OpePurchasBQc item : opePurchasBQcList) {
                if (purchasB.getId().equals(item.getPurchasBId())) {
                    if (item.getFailCount() != 0) {
                        failConut++;
                        initFailTotal += item.getFailCount();
                    }
                    purchasB.setLaveWaitQcQty(purchasB.getLaveWaitQcQty() + initFailTotal);
                    purchasB.setQcStatus(QcStatusEnums.QUALITY_INSPECTION.getValue());
                    purchasB.setUpdatedBy(enter.getUserId());
                    purchasB.setUpdatedTime(new Date());
                    item.setTotalQualityInspected(item.getTotalQualityInspected() - item.getFailCount());
                    item.setFailCount(0);
                    item.setUpdatedBy(enter.getUserId());
                    item.setStatus(QcStatusEnums.PASS.getValue());
                    item.setUpdatedTime(new Date());
                }
            }
            for (OpePurchas item : opePurchasList) {
                if (item.getId().equals(purchasB.getPurchasId())) {
                    item.setLaveWaitQcTotal(item.getLaveWaitQcTotal() + initFailTotal);
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                }
            }
        }
        //若无质检失败的话 无需质检
        if (failConut == 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(), ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
        }

        //抹除掉 质检失败和批次号建立的绑定关系
        List<OpePurchasBQcItem> opePurchasBQcItemList = opePurchasBQcItemService.list(new LambdaQueryWrapper<OpePurchasBQcItem>().in(OpePurchasBQcItem::getPurchasBQcId,
                opePurchasBQcList.stream().map(OpePurchasBQc::getId).collect(Collectors.toList())));
        if (CollectionUtils.isNotEmpty(opePurchasBQcItemList)) {
            opePurchasBQcItemList.forEach(item -> {
                if (item.getQcResult().equals(QcStatusEnums.FAIL.getValue())) {
                    item.setBatchNo(null);
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                }
            });
        }
        opePurchasBQcItemService.updateBatch(opePurchasBQcItemList);
        //更新质检结果表
        if (CollectionUtils.isNotEmpty(opePurchasBQcList)) {
            opePurchasBQcService.updateBatchById(opePurchasBQcList);
        }

        //更新主表
        opePurchasService.updateBatchById(opePurchasList);

        //更新子表数据
        if (CollectionUtils.isNotEmpty(opePurchasBList)) {
            opePurchasBService.updateBatchById(opePurchasBList);
        }

        //订单节点 更新
        opePurchasList.forEach(item -> {
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(item.getId());
            saveNodeEnter.setStatus(PurchasingStatusEnums.QC_AGAIN.getValue());
            saveNodeEnter.setEvent(PurchasingEventEnums.QC_AGAIN.getValue());
            saveNodeEnter.setMemo(null);
            receiptTraceService.savePurchasingNode(saveNodeEnter);
        });
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 来料质检详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialDetailResult> materialQcDetail(MaterialQcDetailEnter enter) {
        //订单、状态 过滤
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue()) && !StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.PENDING.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        int count = materialServiceMapper.materialQcDetailListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, materialServiceMapper.detailList(enter));
    }

    /**
     * 来料质检失败详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialDetailResult> materialQcFailDetail(MaterialQcDetailEnter enter) {
        //订单、状态 过滤
        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue()) && !StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.PENDING.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        int count = materialServiceMapper.qcFailDetailCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<MaterialDetailResult> materialDetailResultList = materialServiceMapper.qcFailDetail(enter);
        materialDetailResultList.forEach(item -> {
            if (opePurchas.getLaveWaitQcTotal() > 0) {
                item.setWhetherQcComplate(Boolean.FALSE);
            } else {
                item.setWhetherQcComplate(Boolean.TRUE);
            }
        });
        return PageResult.create(enter, count, materialDetailResultList);
    }

    /**
     * 部件质检模板
     *
     * @param enter
     * @return
     */
    @Override
    public MaterialQcTemplateDetailResult MaterialQcTemplate(IdEnter enter) {
        //采购单 校验
        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        OpeParts opeParts = opePartsService.getById(opePurchasB.getPartId());
        if (opeParts == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //模板质检项 查询
        QueryWrapper<OpePartQcTemplate> opePartQcTemplateQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateQueryWrapper.eq(OpePartQcTemplate.COL_PART_ID, opePurchasB.getPartId());
        opePartQcTemplateQueryWrapper.eq(OpePartQcTemplate.COL_DR, 0);
        List<OpePartQcTemplate> opePartQcTemplateList = opePartQcTemplateService.list(opePartQcTemplateQueryWrapper);
        if (CollectionUtils.isEmpty(opePartQcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getCode(), ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getMessage());
        }

        //质检项 结果集查询
        QueryWrapper<OpePartQcTemplateB> opePartQcTemplateBQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateBQueryWrapper.eq(OpePartQcTemplateB.COL_DR, 0);
        opePartQcTemplateBQueryWrapper.in(OpePartQcTemplateB.COL_PART_QC_TEMPLATE_ID, opePartQcTemplateList.stream().map(OpePartQcTemplate::getId).collect(Collectors.toList()));
        List<OpePartQcTemplateB> opePartQcTemplateBList = opePartQcTemplateBService.list(opePartQcTemplateBQueryWrapper);
        if (CollectionUtils.isEmpty(opePartQcTemplateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getCode(), ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getMessage());
        }

        //封装质检模板
        List<PartTemplateResult> partTemplateList = Lists.newArrayList();
        opePartQcTemplateList.forEach(item -> {
            List<PartQcResultResult> partQcResultList = Lists.newArrayList();
            opePartQcTemplateBList.forEach(templateB -> {
                if (item.getId().equals(templateB.getPartQcTemplateId())) {
                    partQcResultList.add(
                            PartQcResultResult.builder()
                                    .id(templateB.getId())
                                    .partQcTemplateId(templateB.getPartQcTemplateId())
                                    .qcResult(templateB.getQcResult())
                                    .uploadFlag(templateB.getUploadFlag())
                                    .resultsSequence(templateB.getResultsSequence())
                                    .build()
                    );
                }
            });
            partTemplateList.add(
                    PartTemplateResult.builder()
                            .id(item.getId())
                            .qcItemName(item.getQcItemName())
                            .partQcResultList(partQcResultList)
                            .build()
            );

        });

        return MaterialQcTemplateDetailResult.builder()
                .id(opePurchasB.getId())
                .partCnName(opeParts.getCnName())
                .partN(opeParts.getPartsNumber())
                .partId(opeParts.getId())
                .partTemplateList(partTemplateList)
                .build();
    }

    /**
     * 保存来料质检结果
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public SaveMaterialQcResult saveMaterialQc(SaveMaterialQcEnter enter) {
        List<PartTemplateEnter> partQcResultList = Lists.newArrayList();
        //设置 模板、模板结果 之间关系
        Map<PartTemplateEnter, Long> templateMap = Maps.newHashMap();
        //质检通过模板
        Map<OpePartQcTemplate, OpePartQcTemplateB> passTemplateMap = Maps.newHashMap();
        //质检项结果保存
        List<OpePurchasQcTrace> opePurchasQcTraceList = new ArrayList<>();

        //对入参数据做校验
        OpePurchasB opePurchasB = checkSaveMaterialEnter(enter, partQcResultList, templateMap, passTemplateMap);

        OpeParts opeParts = opePartsService.getById(opePurchasB.getPartId());
        if (opeParts == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        //根据质检类型校验质检数量
        if (opeParts.getIdClass().equals(Boolean.TRUE)) {

            //序列号不能为空
            if (StringUtils.isBlank(enter.getSerialNum())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getCode(), ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getMessage());
            }

            // 有IdClas校验是否已经 通过质检
            List<OpePurchasBQcItem> checkPurchasBQcItemList = opePurchasBQcItemService.list(
                    new LambdaQueryWrapper<OpePurchasBQcItem>().eq(OpePurchasBQcItem::getSerialNum, enter.getSerialNum())
                            .eq(OpePurchasBQcItem::getQcResult, QcStatusEnums.PASS.getValue()));
            if (CollectionUtils.isNotEmpty(checkPurchasBQcItemList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_PASSED_THE_QUALITY_INSPECTION.getCode(), ExceptionCodeEnums.PART_PASSED_THE_QUALITY_INSPECTION.getMessage());
            }

            if (enter.getQty() == null || enter.getQty() == 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getCode(), ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getMessage());
            }
            if (enter.getQty() > opePurchasB.getLaveWaitQcQty()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }
        }
        //没有IdClas 校验质检数量
        if (!opeParts.getIdClass()) {
            if (enter.getQty() == null || enter.getQty() == 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getCode(), ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getMessage());
            }
            if (enter.getQty() > opePurchasB.getLaveWaitQcQty()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }
        }

        //质检结果 是否通过判断
        Boolean qcResult = Boolean.TRUE;
        for (Map.Entry<PartTemplateEnter, Long> entry : templateMap.entrySet()) {
            PartTemplateEnter partTemplateEnter = entry.getKey();
            Long value = entry.getValue();

            //质检项 是否存在判断
            Boolean qcItem = Boolean.FALSE;
            for (OpePartQcTemplate item : passTemplateMap.keySet()) {
                if (item.getId().equals(partTemplateEnter.getId())) {
                    qcItem = Boolean.TRUE;
                    break;
                }
            }
            if (!qcItem) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getCode(), ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getMessage());
            }
            // 判断 质检项 是否 质检通过的模板要求
            for (Map.Entry<OpePartQcTemplate, OpePartQcTemplateB> e : passTemplateMap.entrySet()) {
                OpePartQcTemplate key = e.getKey();
                OpePartQcTemplateB passValue = e.getValue();
                // 判断同一个质检项 的质检结果
                if (key.getId().equals(partTemplateEnter.getId()) && !passValue.getId().equals(value)) {
                    qcResult = Boolean.FALSE;
                    break;
                }
            }
        }

        //查询采购单数据
        OpePurchas opePurchas = opePurchasService.getById(opePurchasB.getPurchasId());
        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.PENDING.getValue()) && !StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        //判断采购单下其他部品状态
        List<OpePurchasB> opePurchasBList = opePurchasBService.list(new LambdaQueryWrapper<OpePurchasB>()
                .eq(OpePurchasB::getPurchasId, opePurchasB.getPurchasId())
                .ne(OpePurchasB::getId, opePurchasB.getId())
                .ne(OpePurchasB::getLaveWaitQcQty, 0)
        );


        //判断如果为pending 状态时改变采购单的状态为 来料质检
        //查询质检结果记录 若无 修改
        if (StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.PENDING.getValue())) {

            //没有其他子订单的话 跳过校验 直接修改状态
            if (CollectionUtils.isNotEmpty(opePurchasBList)) {
                //查询质检结果
                List<OpePurchasBQc> opePurchasBQcList = opePurchasBQcService.list(new LambdaQueryWrapper<OpePurchasBQc>().in(OpePurchasBQc::getPurchasBId,
                        opePurchasBList.stream().map(OpePurchasB::getId).collect(Collectors.toList())));
                //当前所有子订单 没有质检记录 修改主表状态  pengding--》 来料质检
                if (CollectionUtils.isEmpty(opePurchasBQcList)) {
                    opePurchas.setStatus(PurchasingStatusEnums.MATERIALS_QC.getValue());
                }
            } else {
                opePurchas.setStatus(PurchasingStatusEnums.MATERIALS_QC.getValue());
            }

            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opePurchas.getId());
            saveNodeEnter.setStatus(PurchasingStatusEnums.MATERIALS_QC.getValue());
            saveNodeEnter.setEvent(PurchasingStatusEnums.MATERIALS_QC.getValue());
            saveNodeEnter.setMemo(null);
            receiptTraceService.savePurchasingNode(saveNodeEnter);
        }

        //更新直接结果

        //根据质检方式减库存
        int initLaveWaitQcQty = opePurchasB.getLaveWaitQcQty();
        //质检成功、质检失败 都要待备料数量递减
        if (opeParts.getIdClass()) {
            opePurchasB.setLaveWaitQcQty(opePurchasB.getLaveWaitQcQty() - 1);
            opePurchas.setLaveWaitQcTotal(opePurchas.getLaveWaitQcTotal() - 1);
        } else {
            opePurchasB.setLaveWaitQcQty(0);
            opePurchas.setLaveWaitQcTotal(opePurchas.getLaveWaitQcTotal() - initLaveWaitQcQty);
        }
        if (opePurchasB.getLaveWaitQcQty() < 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
        }
        if (opePurchas.getLaveWaitQcTotal() < 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
        }

        //质检成功后进行的逻辑处理
        if (qcResult) {
            //判断是否要更新采购单状态为质检通过
            Boolean updatePuchasStatus = Boolean.TRUE;
            opePurchasBList.add(opePurchasB);
            for (OpePurchasB item : opePurchasBList) {
                if (item.getLaveWaitQcQty() != 0) {
                    updatePuchasStatus = Boolean.FALSE;
                    break;
                }
            }

            //更新采购单状态以及采购单记录
            if (updatePuchasStatus && qcResult) {
                opePurchas.setStatus(PurchasingStatusEnums.QC_COMPLETED.getValue());
                opePurchas.setLaveWaitQcTotal(0);
                //更新订单记录
                SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
                BeanUtils.copyProperties(enter, saveNodeEnter);
                saveNodeEnter.setId(opePurchas.getId());
                saveNodeEnter.setStatus(PurchasingStatusEnums.QC_COMPLETED.getValue());
                saveNodeEnter.setEvent(PurchasingStatusEnums.QC_COMPLETED.getValue());
                saveNodeEnter.setMemo(null);
                receiptTraceService.savePurchasingNode(saveNodeEnter);
            }
            if (opePurchasB.getLaveWaitQcQty() == 0) {
                opePurchasB.setQcStatus(QcStatusEnums.PASS.getValue());
            }
        }

        //查询 前端传过来的质检结果 如果符合 pass模板跳过查询 不符合 重新查询
        Map<OpePartQcTemplate, OpePartQcTemplateB> opePartQcTemplateMap = new HashMap<>();
        if (qcResult) {
            opePartQcTemplateMap.putAll(passTemplateMap);
        } else {
            //前端 传参的质检结果集 判断做处理
            Collection<OpePartQcTemplate> opePartQcTemplateList = opePartQcTemplateService.listByIds(templateMap.keySet().stream().map(PartTemplateEnter::getId).collect(Collectors.toList()));
            if (CollectionUtils.isEmpty(opePartQcTemplateList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_IS_EMPTY.getMessage());
            }
            Collection<OpePartQcTemplateB> opePartQcTemplateBList = opePartQcTemplateBService.listByIds(templateMap.values());
            if (CollectionUtils.isEmpty(opePartQcTemplateBList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_TEMPLATE_B_IS_EMPTY.getMessage());
            }

            //封装Map
            opePartQcTemplateList.forEach(template -> {
                opePartQcTemplateBList.forEach(templateB -> {
                    if (template.getId().equals(templateB.getPartQcTemplateId())) {
                        opePartQcTemplateMap.put(template, templateB);
                    }
                });
            });
        }

        //主订单数据更新
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchas.setUpdatedTime(new Date());
        opePurchasService.updateById(opePurchas);

        //子表数据更新
        if (opePurchasB.getLaveWaitQcQty() == 0) {
            opePurchasB.setQcStatus(QcStatusEnums.PASS.getValue());
        }
        opePurchasB.setUpdatedBy(enter.getUserId());
        opePurchasB.setUpdatedTime(new Date());
        opePurchasBService.updateById(opePurchasB);

        //批次号入参
        IdEnter batchNoEnter = new IdEnter();
        batchNoEnter.setId(opePurchasB.getPurchasId());
        String batchNo = bussinessNumberService.materialQcBatchNo(batchNoEnter);

        //保存质检结果
        OpePurchasBQc purchasBQc = buildOpePurchasBQc(enter, opeParts, opePurchasB.getId(), qcResult, batchNo, initLaveWaitQcQty);
        opePurchasBQcService.saveOrUpdate(purchasBQc);

        //保存批次号的质检记录
        OpePurchasLotTrace opePurchasLotTrace = buildOpepurchasLotTraceSingle(enter, opeParts, qcResult, opePurchas, batchNo);
        opePurchasLotTraceService.saveOrUpdate(opePurchasLotTrace);

        //保存质检条目
        OpePurchasBQcItem purchasBQcItem = buildOpePurchasBQcItem(enter, opeParts, purchasBQc, qcResult, batchNo, opePurchasLotTrace.getId());
        opePurchasBQcItemService.save(purchasBQcItem);

        //保存质检项记录
        templateMap.forEach((key, value) -> {
            opePurchasQcTraceList.add(buildOpePurchasQcTrace(enter, opePurchasB.getId(), purchasBQc.getId(), purchasBQcItem.getId(), key, value, opePartQcTemplateMap));
        });
        opePurchasQcTraceService.saveBatch(opePurchasQcTraceList);

        return SaveMaterialQcResult.builder()
                .partCnName(opeParts.getCnName())
                .laveWaitQcQty(opePurchasB.getLaveWaitQcQty())
                .batchN(batchNo)
                .partN(opeParts.getPartsNumber())
                .qcResult(qcResult)
                .build();
    }

    private OpePurchasLotTrace buildOpepurchasLotTraceSingle(SaveMaterialQcEnter enter, OpeParts opeParts, Boolean qcResult, OpePurchas opePurchas, String batchNo) {
        OpePurchasLotTrace opePurchasLotTrace = opePurchasLotTraceService.getOne(new LambdaQueryWrapper<OpePurchasLotTrace>().eq(OpePurchasLotTrace::getBatchNo, batchNo));
        if (opePurchasLotTrace == null) {
            opePurchasLotTrace = OpePurchasLotTrace.builder()
                    .id(idAppService.getId(SequenceName.OPE_PURCHAS_LOT_TRACE))
                    .dr(0)
                    .tenantId(enter.getTenantId())
                    .userId(enter.getUserId())
                    .purchasId(opePurchas.getId())
                    .qualityInspectorId(enter.getUserId())
                    .batchNo(batchNo)
                    .qualityInspectionTime(new Date())
                    .revision(0)
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .build();
            if (qcResult) {
                if (opeParts.getIdClass()) {
                    opePurchasLotTrace.setTotalQualityInspected(1);
                    opePurchasLotTrace.setPassCount(1);
                } else {
                    opePurchasLotTrace.setPassCount(enter.getQty());
                    opePurchasLotTrace.setTotalQualityInspected(enter.getQty());
                }
                opePurchasLotTrace.setFailCount(0);
            } else {
                if (opeParts.getIdClass()) {
                    opePurchasLotTrace.setTotalQualityInspected(1);
                    opePurchasLotTrace.setFailCount(1);
                } else {
                    opePurchasLotTrace.setFailCount(enter.getQty());
                    opePurchasLotTrace.setTotalQualityInspected(enter.getQty());
                }
                opePurchasLotTrace.setPassCount(0);
            }
        } else {
            if (qcResult) {
                if (opeParts.getIdClass()) {
                    opePurchasLotTrace.setTotalQualityInspected(opePurchasLotTrace.getTotalQualityInspected() + 1);
                    opePurchasLotTrace.setPassCount(opePurchasLotTrace.getPassCount() + 1);
                } else {
                    opePurchasLotTrace.setPassCount(opePurchasLotTrace.getPassCount() + enter.getQty());
                    opePurchasLotTrace.setTotalQualityInspected(opePurchasLotTrace.getTotalQualityInspected() + enter.getQty());
                }
                opePurchasLotTrace.setFailCount(opePurchasLotTrace.getFailCount());
            } else {
                if (opeParts.getIdClass()) {
                    opePurchasLotTrace.setTotalQualityInspected(opePurchasLotTrace.getTotalQualityInspected() + 1);
                    opePurchasLotTrace.setFailCount(opePurchasLotTrace.getFailCount() + 1);
                } else {
                    opePurchasLotTrace.setFailCount(opePurchasLotTrace.getFailCount() + enter.getQty());
                    opePurchasLotTrace.setTotalQualityInspected(opePurchasLotTrace.getTotalQualityInspected() + enter.getQty());
                }
                opePurchasLotTrace.setPassCount(opePurchasLotTrace.getPassCount());
            }
        }

        return opePurchasLotTrace;
    }

    private OpePurchasBQc buildOpePurchasBQc(SaveMaterialQcEnter enter, OpeParts opeParts, Long opePurchasBId, Boolean qcResult, String batchNo, int initLaveWaitQcQty) {
        //查询今天是否 已经质检过
        OpePurchasBQc opePurchasBQc = opePurchasBQcService.getOne(new LambdaQueryWrapper<OpePurchasBQc>().eq(OpePurchasBQc::getPurchasBId, opePurchasBId));

        //如果 质检结果数据不存在新建，如果存在就进行数据的累加
        if (opePurchasBQc == null) {
            opePurchasBQc = OpePurchasBQc.builder()
                    .id(idAppService.getId(SequenceName.OPE_PURCHAS_B_QC))
                    .dr(0)
                    .tenantId(0L)
                    .userId(0L)
                    .purchasBId(opePurchasBId)
                    .partsId(enter.getId())
                    .qualityInspectorId(enter.getUserId())
                    .batchNo(batchNo)
                    .qualityInspectionTime(new Date())
                    .revision(0)
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .build();
            if (qcResult) {
                if (opeParts.getIdClass()) {
                    opePurchasBQc.setTotalQualityInspected(1);
                    opePurchasBQc.setPassCount(1);
                } else {
                    opePurchasBQc.setPassCount(initLaveWaitQcQty);
                    opePurchasBQc.setTotalQualityInspected(initLaveWaitQcQty);
                }
                opePurchasBQc.setFailCount(0);
                opePurchasBQc.setStatus(QcStatusEnums.PASS.getValue());
            } else {
                if (opeParts.getIdClass()) {
                    opePurchasBQc.setTotalQualityInspected(1);
                    opePurchasBQc.setFailCount(1);
                } else {
                    opePurchasBQc.setFailCount(initLaveWaitQcQty);
                    opePurchasBQc.setTotalQualityInspected(initLaveWaitQcQty);
                }
                opePurchasBQc.setPassCount(0);
                opePurchasBQc.setStatus(QcStatusEnums.FAIL.getValue());
            }
        } else {
            if (qcResult) {
                if (opeParts.getIdClass()) {
                    opePurchasBQc.setTotalQualityInspected(opePurchasBQc.getTotalQualityInspected() + 1);
                    opePurchasBQc.setPassCount(opePurchasBQc.getPassCount() + 1);
                } else {
                    opePurchasBQc.setPassCount(opePurchasBQc.getPassCount() + initLaveWaitQcQty);
                    opePurchasBQc.setTotalQualityInspected(opePurchasBQc.getTotalQualityInspected() + initLaveWaitQcQty);
                }
                opePurchasBQc.setFailCount(opePurchasBQc.getFailCount());
            } else {
                if (opeParts.getIdClass()) {
                    opePurchasBQc.setTotalQualityInspected(opePurchasBQc.getTotalQualityInspected() + 1);
                    opePurchasBQc.setFailCount(opePurchasBQc.getFailCount() + 1);
                } else {
                    opePurchasBQc.setFailCount(opePurchasBQc.getFailCount() + initLaveWaitQcQty);
                    opePurchasBQc.setTotalQualityInspected(opePurchasBQc.getTotalQualityInspected() + initLaveWaitQcQty);
                }
                opePurchasBQc.setPassCount(opePurchasBQc.getPassCount());
            }
            opePurchasBQc.setStatus(QcStatusEnums.FAIL.getValue());
        }
        return opePurchasBQc;
    }

    private OpePurchasBQcItem buildOpePurchasBQcItem(SaveMaterialQcEnter enter, OpeParts opeParts, OpePurchasBQc purchasBQc, Boolean qcResult, String batchNo, Long lotTraceId) {
        return OpePurchasBQcItem.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_B_QC_ITEM))
                .dr(0)
                .partId(opeParts.getId())
                .purchasBId(enter.getId())
                .purchasBQcId(purchasBQc.getId())
                .purchasBLotTraceId(lotTraceId)
                .batchNo(batchNo)
                .qcBatchTotal(opeParts.getIdClass() == true ? 1 : enter.getQty())
                .serialNum(opeParts.getIdClass() == true ? enter.getSerialNum() : null)
                .qcResult(qcResult == true ? QcStatusEnums.PASS.getValue() : QcStatusEnums.FAIL.getValue())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    /**
     * 保存质检记录
     *
     * @param enter
     * @param opePurchasBId
     * @param purchasBQcId
     * @param purchasBQcItemId
     * @param key
     * @param value
     * @return
     */
    private OpePurchasQcTrace buildOpePurchasQcTrace(SaveMaterialQcEnter enter, Long opePurchasBId, Long purchasBQcId, Long purchasBQcItemId, PartTemplateEnter key,
                                                     Long value, Map<OpePartQcTemplate, OpePartQcTemplateB> opePartQcTemplateMap) {
        String qcItemName = opePartQcTemplateMap.keySet().stream().filter(item -> item.getId().equals(key.getId())).findFirst().get().getQcItemName();

        String qcResult = opePartQcTemplateMap.values().stream().filter(item -> item.getId().equals(value)).findFirst().get().getQcResult();
        OpePurchasQcTrace opePurchasQcTrace = OpePurchasQcTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_QC_TRACE))
                .dr(0)
                .partId(enter.getId())
                .purchasId(opePurchasBId)
                .purchasBQcId(purchasBQcId)
                .purchasBQcItemId(purchasBQcItemId)
                .partQcTemplateId(key.getId())
                .partQcTemplateName(StringUtils.isBlank(qcItemName) == true ? null : qcItemName)
                .partQcTemplateBId(value)
                .partQcTemplateBName(StringUtils.isBlank(qcResult) == true ? null : qcResult)
                .partQcPicture(key.getPictureList())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();

        return opePurchasQcTrace;
    }

    /**
     * 保存质检记录 入参校验
     *
     * @param enter
     * @param partQcResultList
     * @param templateMap
     */
    private OpePurchasB checkSaveMaterialEnter(SaveMaterialQcEnter enter, List<PartTemplateEnter> partQcResultList, Map<PartTemplateEnter, Long> templateMap,
                                               Map<OpePartQcTemplate, OpePartQcTemplateB> passTemplateMap) {
        try {
            partQcResultList.addAll(JSON.parseArray(enter.getPartTemplateListJson(), PartTemplateEnter.class));
            if (CollectionUtils.isEmpty(partQcResultList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
            partQcResultList.forEach(item -> {
                templateMap.put(item, item.getQcResultId());
            });
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (opePurchasB.getLaveWaitQcQty() == 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_PART_FINISHED_PREPARATION.getCode(), ExceptionCodeEnums.PURCHAS_PART_FINISHED_PREPARATION.getMessage());
        }

        //查询质检模板
        QueryWrapper<OpePartQcTemplate> opePartQcTemplateQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateQueryWrapper.eq(OpePartQcTemplate.COL_PART_ID, opePurchasB.getPartId());
        List<OpePartQcTemplate> partQcTemplateList = opePartQcTemplateService.list(opePartQcTemplateQueryWrapper);
        if (CollectionUtils.isEmpty(partQcTemplateList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getCode(), ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getMessage());
        }
        if (templateMap.keySet().size() != partQcTemplateList.size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getCode(), ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getMessage());
        }

        //查询模板对应的pass结果集
        QueryWrapper<OpePartQcTemplateB> opePartQcTemplateBQueryWrapper = new QueryWrapper<>();
        opePartQcTemplateBQueryWrapper.eq(OpePartQcTemplateB.COL_DR, 0);
        opePartQcTemplateBQueryWrapper.in(OpePartQcTemplateB.COL_PART_QC_TEMPLATE_ID, partQcTemplateList.stream().map(OpePartQcTemplate::getId).collect(Collectors.toList()));
        opePartQcTemplateBQueryWrapper.eq(OpePartQcTemplateB.COL_PASS_FLAG, Boolean.TRUE);
        Collection<OpePartQcTemplateB> opePartQcTemplateBList = opePartQcTemplateBService.list(opePartQcTemplateBQueryWrapper);
        if (CollectionUtils.isEmpty(opePartQcTemplateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getCode(), ExceptionCodeEnums.PART_IS_NOT_HAVE_QC_TEMPLATE.getMessage());
        }
        if (opePartQcTemplateBList.size() != templateMap.values().size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getCode(), ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getMessage());
        }

        //封装质检pass模板
        partQcTemplateList.forEach(item -> {
            opePartQcTemplateBList.forEach(template -> {
                if (item.getId().equals(template.getPartQcTemplateId())) {
                    passTemplateMap.put(item, template);
                }
            });
        });
        return opePurchasB;
    }
}
