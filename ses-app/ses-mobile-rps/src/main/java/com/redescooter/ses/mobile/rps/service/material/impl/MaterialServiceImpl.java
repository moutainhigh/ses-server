package com.redescooter.ses.mobile.rps.service.material.impl;

import cn.hutool.core.util.RandomUtil;
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
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.material.MaterialService;
import com.redescooter.ses.mobile.rps.vo.bo.RpsPartDetailDto;
import com.redescooter.ses.mobile.rps.vo.materialqc.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
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

        Map<String, Integer> map = Maps.newHashMap();
        map.put(QcTypeEnums.WAIT.getValue(), opePurchasService.count(opePurchasQueryWrapper));
        map.put(QcTypeEnums.FAIL.getValue(), materialServiceMapper.qcFailType(enter));
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
        return PageResult.create(enter, 1, materialServiceMapper.failList(enter));
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
        });

        //查询qc 信息
        QueryWrapper<OpePurchasBQc> opePurchasBQcQueryWrapper = new QueryWrapper<>();
        opePurchasBQcQueryWrapper.in(OpePurchasBQc.COL_PURCHAS_B_ID, opePurchasBList.stream().map(OpePurchasB::getId).collect(Collectors.toList()));
        opePurchasBQcQueryWrapper.eq(OpePurchasBQc.COL_DR, 0);
        opePurchasBQcQueryWrapper.ne(OpePurchasBQc.COL_FAIL_COUNT, 0);
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
                    item.setTotalQualityInspected(item.getPassCount());
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
                            item.setLaveWaitQcQty(item.getLaveWaitQcQty() - value);
                            //主表数量维护
                            opePurchas.setLaveWaitQcTotal(opePurchas.getLaveWaitQcTotal() - value);
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

            String memo = new StringBuffer(originalAmount.toString() + CurrencyUnitEnums.FR.getName()).append(",").append(retureTotalPrice.toString() + CurrencyUnitEnums.FR.getName()).toString();

            //节点
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opePurchas.getId());
            saveNodeEnter.setStatus(PurchasingStatusEnums.RETURN.getValue());
            saveNodeEnter.setEvent(PurchasingEventEnums.RETURN.getValue());
            saveNodeEnter.setMemo(memo);
            this.saveNode(saveNodeEnter);
        }

        //QC子表更新
        purchasBQcList.forEach(item -> {
            item.setFailCount(0);
            item.setTotalQualityInspected(item.getPassCount());
            item.setStatus(QcStatusEnums.PASS.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });

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
        //1.验证是否有质检失败的部品
        List<OpePurchasB> checkPurchasBList = opePurchasBService.list(new LambdaQueryWrapper<OpePurchasB>().in(OpePurchasB::getPurchasId,
                opePurchasList.stream().map(OpePurchas::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(checkPurchasBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        //2.质检表数据获取
        List<OpePurchasBQc> opePurchasBQcList = opePurchasBQcService.list(new LambdaQueryWrapper<OpePurchasBQc>().in(OpePurchasBQc::getPurchasBId,
                checkPurchasBList.stream().map(OpePurchasB::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opePurchasBQcList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PLEASE_SCAN_THE_CODE_FIRST.getCode(), ExceptionCodeEnums.PLEASE_SCAN_THE_CODE_FIRST.getMessage());
        }
        //筛选 是否有质检失败的产品
        int failConut = 0;
        if (CollectionUtils.isEmpty(opePurchasBQcList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PLEASE_SCAN_THE_CODE_FIRST.getCode(), ExceptionCodeEnums.PLEASE_SCAN_THE_CODE_FIRST.getMessage());
        }

        //校验质检结果表 数据是否要进行修改
        for (OpePurchasB purchasB : checkPurchasBList) {
            for (OpePurchasBQc item : opePurchasBQcList) {
                if (item.getFailCount() == 0) {
                    failConut++;
                }
                item.setFailCount(0);
                item.setUpdatedBy(enter.getUserId());
                item.setStatus(QcStatusEnums.PASS.getValue());
                item.setUpdatedTime(new Date());

                if (purchasB.getId().equals(item.getPurchasBId()) && item.getPartsId().equals(purchasB.getPartId())) {
                    purchasB.setQcStatus(QcStatusEnums.QUALITY_INSPECTION.getValue());
                    purchasB.setUpdatedBy(enter.getUserId());
                    purchasB.setUpdatedTime(new Date());
                }

            }
        }
        //若无质检失败的话 无需质检
        if (failConut == opePurchasBQcList.size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(), ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
        }

        //更新质检结果表
        if (CollectionUtils.isNotEmpty(opePurchasBQcList)) {
            opePurchasBQcService.updateBatchById(opePurchasBQcList);
        }

        //判断是否全部QC 通过 通过的话修改子表状态
        opePurchasBQcList.removeIf(item -> StringUtils.equals(item.getStatus(), QcStatusEnums.PASS.getValue()));
        opePurchasBQcList.forEach(item -> {
            item.setStatus(QcStatusEnums.PASS.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });
        if (CollectionUtils.isNotEmpty(opePurchasBQcList)) {
            opePurchasBQcService.updateBatchById(opePurchasBQcList);
        }

        //订单节点 更新
        opePurchasList.forEach(item -> {
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(item.getId());
            saveNodeEnter.setStatus(PurchasingStatusEnums.QC_AGAIN.getValue());
            saveNodeEnter.setEvent(PurchasingEventEnums.QC_AGAIN.getValue());
            saveNodeEnter.setMemo(null);
            this.saveNode(saveNodeEnter);
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
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
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
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        int count = materialServiceMapper.qcFailDetailCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        return PageResult.create(enter, count, materialServiceMapper.qcFailDetail(enter));
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

        // 有IdClas校验是否已经 通过质检
        List<OpePurchasBQcItem> checkPurchasBQcItemList = opePurchasBQcItemService.list(
                new LambdaQueryWrapper<OpePurchasBQcItem>().eq(OpePurchasBQcItem::getSerialNum, enter.getSerialNum())
                        .ne(OpePurchasBQcItem::getQcResult, QcStatusEnums.PASS.getValue()));
        if (CollectionUtils.isNotEmpty(checkPurchasBQcItemList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_PASSED_THE_QUALITY_INSPECTION.getCode(), ExceptionCodeEnums.PART_PASSED_THE_QUALITY_INSPECTION.getMessage());
        }

        OpeParts opeParts = opePartsService.getById(opePurchasB.getPartId());
        if (opeParts == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        //根据质检类型校验质检数量
        if (opeParts.getIdClass().equals(Boolean.TRUE)) {
            if (enter.getQty() == null || enter.getQty() == 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getCode(), ExceptionCodeEnums.PART_QC_QTY_IS_EMPTY.getMessage());
            }
            if (enter.getQty() > opePurchasB.getLaveWaitQcQty()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }
        }

        //质检结果
        Boolean qcResult = Boolean.TRUE;
        for (Map.Entry<PartTemplateEnter, Long> entry : templateMap.entrySet()) {
            PartTemplateEnter partTemplateEnter = entry.getKey();
            Long value = entry.getValue();

            //质检项
            Boolean qcItem = Boolean.FALSE;
            for (OpePartQcTemplate item : passTemplateMap.keySet()) {
                if (item.getId().equals(partTemplateEnter.getId())) {
                    qcItem = Boolean.TRUE;
                }
            }
            if (!qcItem) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getCode(), ExceptionCodeEnums.PART_TEMPLATE_ITEM_NOT_ARE_COMPLETE.getMessage());
            }
            for (Map.Entry<OpePartQcTemplate, OpePartQcTemplateB> e : passTemplateMap.entrySet()) {
                OpePartQcTemplate key = e.getKey();
                OpePartQcTemplateB passValue = e.getValue();
                if (!passValue.getId().equals(value)) {
                    qcResult = Boolean.FALSE;
                    break;
                }
            }
        }
        if (qcResult) {

            //判断采购单下其他部品状态
            List<OpePurchasB> opePurchasBList = opePurchasBService.list(new LambdaQueryWrapper<OpePurchasB>().eq(OpePurchasB::getPurchasId, opePurchasB.getPurchasId())
                    .ne(OpePurchasB::getId, opePurchasB.getId())
                    .ne(OpePurchasB::getLaveWaitQcQty, 0)
            );
            //查询采购单数据
            OpePurchas opePurchas = opePurchasService.getById(opePurchasB.getPurchasId());
            if (opePurchas == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
            }

            //根据质检方式减库存
            if (opeParts.getIdClass()) {
                opePurchasB.setLaveWaitQcQty(opePurchasB.getLaveWaitQcQty() - 1);
                opePurchas.setLaveWaitQcTotal(opePurchas.getLaveWaitQcTotal() - 1);
            } else {
                opePurchasB.setLaveWaitQcQty(opePurchasB.getLaveWaitQcQty() - enter.getQty());
                opePurchas.setLaveWaitQcTotal(opePurchas.getLaveWaitQcTotal() - enter.getQty());
            }
            if (opePurchasB.getLaveWaitQcQty() < 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }
            if (opePurchas.getLaveWaitQcTotal() < 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }

            //判断是否要更新采购单状态
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
                this.saveNode(saveNodeEnter);
            }
            if (opePurchasB.getLaveWaitQcQty() == 0) {
                opePurchasB.setQcStatus(QcStatusEnums.PASS.getValue());
            }
            opePurchasB.setUpdatedBy(enter.getUserId());
            opePurchasB.setUpdatedTime(new Date());
            opePurchasBService.updateById(opePurchasB);

            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
            opePurchasService.updateById(opePurchas);
        }


        //保存质检结果
        OpePurchasBQc purchasBQc = buildOpePurchasBQc(enter, opeParts, opePurchasB, qcResult);
        opePurchasBQcService.saveOrUpdate(purchasBQc);
        //保存质检条目
        OpePurchasBQcItem purchasBQcItem = buildOpePurchasBQcItem(enter, opeParts, purchasBQc, qcResult);
        opePurchasBQcItemService.save(purchasBQcItem);

        //保存质检项记录
        templateMap.forEach((key, value) -> {
            opePurchasQcTraceList.add(buildOpePurchasQcTrace(enter, opePurchasB.getId(), purchasBQc.getId(), purchasBQcItem.getId(), key, value));
        });
        opePurchasQcTraceService.saveBatch(opePurchasQcTraceList);

        return SaveMaterialQcResult.builder()
                .partCnName(opeParts.getCnName())
                .laveWaitQcQty(opePurchasB.getLaveWaitQcQty())
                .batchN(purchasBQc.getBatchNo())
                .partN(opeParts.getPartsNumber())
                .qcResult(qcResult)
                .build();
    }

    private OpePurchasBQc buildOpePurchasBQc(SaveMaterialQcEnter enter, OpeParts opeParts, OpePurchasB opePurchasB, Boolean qcResult) {
        //查询今天是否 已经质检过
        List<OpePurchasBQc> purchasBQcList =
                opePurchasBQcService.list(new LambdaQueryWrapper<OpePurchasBQc>().eq(OpePurchasBQc::getPurchasBId, opePurchasB.getId()).orderByAsc(OpePurchasBQc::getCreatedTime));

        //批次号入参
        IdEnter batchNoEnter = new IdEnter();
        batchNoEnter.setId(opePurchasB.getId());
        OpePurchasBQc purchasBQc = null;
        //如果 质检结果数据不存在新建，如果存在就进行批次号的累加
        if (CollectionUtils.isEmpty(purchasBQcList) || (CollectionUtils.isNotEmpty(purchasBQcList) && !DateUtils.isSameDay(purchasBQcList.get(0).getCreatedTime(), new Date()))) {
            purchasBQc = OpePurchasBQc.builder()
                    .id(idAppService.getId(SequenceName.OPE_PURCHAS_B_QC))
                    .dr(0)
                    .tenantId(0L)
                    .userId(0L)
                    .purchasBId(opePurchasB.getId())
                    .partsId(enter.getId())
                    .qualityInspectorId(enter.getUserId())
                    .batchNo(bussinessNumberService.materialQcBatchNo(batchNoEnter))
                    .qualityInspectionTime(new Date())
                    .revision(0)
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .build();
            if (qcResult) {
                if (opeParts.getIdClass()) {
                    purchasBQc.setTotalQualityInspected(1);
                    purchasBQc.setPassCount(1);
                } else {
                    purchasBQc.setPassCount(enter.getQty());
                    purchasBQc.setTotalQualityInspected(enter.getQty());
                }
                purchasBQc.setFailCount(0);
                purchasBQc.setStatus(QcStatusEnums.PASS.getValue());
            } else {
                if (opeParts.getIdClass()) {
                    purchasBQc.setTotalQualityInspected(1);
                    purchasBQc.setFailCount(1);
                } else {
                    purchasBQc.setFailCount(enter.getQty());
                    purchasBQc.setTotalQualityInspected(enter.getQty());
                }
                purchasBQc.setPassCount(0);
                purchasBQc.setStatus(QcStatusEnums.FAIL.getValue());
            }
        }

        if (CollectionUtils.isNotEmpty(purchasBQcList) && DateUtils.isSameDay(purchasBQcList.get(0).getCreatedTime(), new Date())) {

            purchasBQc = purchasBQcList.get(0);

            if (qcResult) {
                if (opeParts.getIdClass()) {
                    purchasBQc.setTotalQualityInspected(purchasBQc.getTotalQualityInspected() + 1);
                    purchasBQc.setPassCount(purchasBQc.getPassCount() + 1);
                } else {
                    purchasBQc.setPassCount(purchasBQc.getPassCount() + enter.getQty());
                    purchasBQc.setTotalQualityInspected(purchasBQc.getTotalQualityInspected() + enter.getQty());
                }
                purchasBQc.setFailCount(purchasBQc.getFailCount());
                purchasBQc.setStatus(purchasBQc.getStatus());
            } else {
                if (opeParts.getIdClass()) {
                    purchasBQc.setTotalQualityInspected(purchasBQc.getTotalQualityInspected() + 1);
                    purchasBQc.setFailCount(purchasBQc.getFailCount() + 1);
                } else {
                    purchasBQc.setFailCount(purchasBQc.getFailCount() + enter.getQty());
                    purchasBQc.setTotalQualityInspected(purchasBQc.getTotalQualityInspected() + enter.getQty());
                }
                purchasBQc.setPassCount(purchasBQc.getPassCount());
                purchasBQc.setStatus(QcStatusEnums.FAIL.getValue());
            }
        }

        return purchasBQc;
    }

    private OpePurchasBQcItem buildOpePurchasBQcItem(SaveMaterialQcEnter enter, OpeParts opeParts, OpePurchasBQc purchasBQc, Boolean qcResult) {
        return OpePurchasBQcItem.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_B_QC_ITEM))
                .dr(0)
                .partId(opeParts.getId())
                .purchasBId(enter.getId())
                .purchasBQcId(purchasBQc.getId())
                .qcBatchTotal(opeParts.getIdClass() == true ? 1 : enter.getQty())
                .serialNum(opeParts.getIdClass() == true ? "REDE" + RandomUtil.BASE_CHAR_NUMBER : null)
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
                                                     Long value) {
        return OpePurchasQcTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_QC_TRACE))
                .dr(0)
                .partId(enter.getId())
                .purchasId(opePurchasBId)
                .purchasBQcId(purchasBQcId)
                .purchasBQcItemId(purchasBQcItemId)
                .partQcTemplateId(key.getId())
                .partQcTemplateBId(value)
                .partQcPicture(key.getPictureList())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
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

    /**
     * 保存采购单记录
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveNode(SaveNodeEnter enter) {
        opePurchasTraceService.save(OpePurchasTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .purchasId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createBy(enter.getUserId())
                .createTime(new Date())
                .updateBy(enter.getUserId())
                .updateTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }
}
