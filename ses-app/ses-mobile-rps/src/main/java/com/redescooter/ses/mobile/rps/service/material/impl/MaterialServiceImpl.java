package com.redescooter.ses.mobile.rps.service.material.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.rps.QcTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.dao.material.MaterialServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchas;
import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasBService;
import com.redescooter.ses.mobile.rps.service.base.OpePurchasService;
import com.redescooter.ses.mobile.rps.service.material.MaterialService;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcDetailEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcListResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.MaterialQcTemplateDetailResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.PartQcResultResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.PartTemplateResult;
import com.redescooter.ses.mobile.rps.vo.materialqc.ReturnedCompletedEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.SaveMaterialQcEnter;
import com.redescooter.ses.mobile.rps.vo.materialqc.SaveMaterialQcResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        opePurchasService.count(opePurchasQueryWrapper);
//        materialServiceMapper.qcFailType(enter);
        map.put(QcTypeEnums.WAIT.getValue(),0);
        map.put(QcTypeEnums.FAIL.getValue(),0);
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
//        int count = opePurchasService.count(opePurchasQueryWrapper);
//        if (count == 0) {
//            return PageResult.createZeroRowResult(enter);
//        }
//        materialServiceMapper.list(enter);
        return PageResult.create(enter, 1, Lists.newArrayList(MaterialQcListResult.builder()
                .id(0L)
                .createdTime(new Date())
                .laveWaitQcTotal(0)
                .purchasN("Rede635143132")
                .build()));
    }

    /**
     * 质检失败列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MaterialQcListResult> failList(PageEnter enter) {

//        int count = materialServiceMapper.failListCount(enter);
//        if (count == 0) {
//            return PageResult.createZeroRowResult(enter);
//        }
//        materialServiceMapper.failList(enter);
        return PageResult.create(enter, 1, Lists.newArrayList(MaterialQcListResult.builder()
                .id(0L)
                .createdTime(new Date())
                .laveWaitQcTotal(0)
                .purchasN("Rede635143132")
                .build()));
    }

    /**
     * 退回并完成
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult returnedCompleted(ReturnedCompletedEnter enter) {
        return null;
    }

    /**
     * 继续质检
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult againQc(IdEnter enter) {
        return null;
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
//        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
//        if (opePurchas == null) {
//            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
//        }
//        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
//            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
//        }
//
//        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
//        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
//        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, enter.getId());
//        int count = opePurchasBService.count(opePurchasBQueryWrapper);
//        if (count == 0) {
//            return PageResult.createZeroRowResult(enter);
//        }
//        materialServiceMapper.detail(enter);
        return PageResult.create(enter, 1, Lists.newArrayList(MaterialDetailResult.builder()
                .id(0L)
                .idClass(Boolean.FALSE)
                .laveWaitQcQty(1)
                .partN("Rede635143132")
                .partCnName("轮胎")
                .partId(123L)
                .purchasId(2312321L)
                .build()));
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
//        OpePurchas opePurchas = opePurchasService.getById(enter.getId());
//        if (opePurchas == null) {
//            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
//        }
//        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.MATERIALS_QC.getValue())) {
//            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
//        }
//
//        int count = materialServiceMapper.qcFailDetailCount(enter);
//        if (count == 0) {
//            return PageResult.createZeroRowResult(enter);
//        }

//        return PageResult.create(enter, count, materialServiceMapper.qcFailDetail(enter));
        return PageResult.create(enter, 1, Lists.newArrayList(MaterialDetailResult.builder()
                .id(0L)
                .idClass(Boolean.FALSE)
                .laveWaitQcQty(1)
                .partN("Rede635143132")
                .partCnName("轮胎")
                .partId(123L)
                .purchasId(2312321L)
                .build()));
    }

    /**
     * 部件质检模板
     *
     * @param enter
     * @return
     */
    @Override
    public MaterialQcTemplateDetailResult MaterialQcTemplate(IdEnter enter) {
        List<PartTemplateResult> partTemplateList=Lists.newArrayList();
        List<PartQcResultResult> partQcResultList=Lists.newArrayList();
        partQcResultList.add(
                PartQcResultResult.builder()
                        .id(123123L)
                        .partQcTemplateId(423423L)
                        .qcResult("PASS")
                        .resultsSequence(1)
                        .uploadFlag(Boolean.FALSE)
                        .build()
        );
        partTemplateList.add(
                PartTemplateResult.builder()
                        .id(423423L)
                        .qcItemName("外观")
                        .partQcResultList(partQcResultList)
                        .build()
        );

        return MaterialQcTemplateDetailResult.builder()
                .id(123213L)
                .partCnName("轮胎")
                .partN("REDEdasdasdasd")
                .purchasBId(34234L)
                .partTemplateList(partTemplateList)
                .build();
    }

    /**
     * 保存来料质检结果
     *
     * @param enter
     * @return
     */
    @Override
    public SaveMaterialQcResult saveMaterialQc(SaveMaterialQcEnter enter) {
        return null;
    }
}
