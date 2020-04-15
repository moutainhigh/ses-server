package com.redescooter.ses.mobile.rps.service.preparematerial.impl;

import java.util.ArrayList;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.production.ProductContractEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.preparematerial.PrepareMaterialServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocate;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateB;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateBTrace;
import com.redescooter.ses.mobile.rps.dm.OpeParts;
import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsService;
import com.redescooter.ses.mobile.rps.service.preparematerial.PrepareMaterialService;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePartBasicDateEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialPartListEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:PrepareMaterialServiceImpl
 * @description: PrepareMaterialServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 13:24
 */
@Service
public class PrepareMaterialServiceImpl implements PrepareMaterialService {

    @Autowired
    private PrepareMaterialServiceMapper prepareMaterialServiceMapper;

    @Autowired
    private OpeAllocateBService opeAllocateBService;

    @Autowired
    private OpeAllocateService opeAllocateService;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private OpeAllocateBTraceService opeAllocateBTraceService;

    @Reference
    private IdAppService idAppService;

    /**
     * 待备料列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PrepareMaterialListResult> list(PageEnter enter) {
//        return PageResult.create(enter,1, Lists.newArrayList(PrepareMaterialListResult.builder()
//                .id(1312321L)
//                .allocatN("dasdasda")
//                .createdTime(new Date())
//                .preparationWaitTotal(0)
//                .build()));
        int count = prepareMaterialServiceMapper.listCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, prepareMaterialServiceMapper.list(enter));
    }

    /**
     * 待备料详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PrepareMaterialDetailResult> detail(PrepareMaterialDetailEnter enter) {
//        return PageResult.create(enter, 1, Lists.newArrayList(PrepareMaterialDetailResult.builder()
//                .id(423432L)
//                .partId(443432L)
//                .partCnName("轮胎")
//                .preparationWaitQty(0)
//                .build()));

        int count = prepareMaterialServiceMapper.detailListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, prepareMaterialServiceMapper.detailList(enter));
    }

    /**
     * 保存
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult save(SavePrepareMaterialEnter enter) {
        //备料部件集合
        List<SavePrepareMaterialPartListEnter> savePrepareMaterialListEnterList = new ArrayList<>();
        //部件基本数据
        Map<Long, List<SavePartBasicDateEnter>> savePartBasicDateMap = Maps.newHashMap();

        //备料记录集合
        List<OpeAllocateBTrace> opeAllocateBTraceList = Lists.newArrayList();

        //对入参参数进行解析
        try {
            savePrepareMaterialListEnterList.addAll(JSON.parseArray(enter.getPartListJson(), SavePrepareMaterialPartListEnter.class));
            if (CollectionUtils.isEmpty(savePrepareMaterialListEnterList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
            savePrepareMaterialListEnterList.forEach(item -> {
                savePartBasicDateMap.put(item.getId(), JSON.parseArray(item.getPartListJson(), SavePartBasicDateEnter.class));
            });
            if (savePartBasicDateMap.isEmpty()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        //验证子表数据是否存在
        List<OpeAllocateB> opeAllocateBList =
                new ArrayList<>(opeAllocateBService.listByIds(savePrepareMaterialListEnterList.stream().map(SavePrepareMaterialPartListEnter::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opeAllocateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }

        //查询订单主表数据
        OpeAllocate opeAllocate = opeAllocateService.getById(opeAllocateBList.get(0).getAllocateId());
        if (opeAllocate == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }

        //查询配件信息
        List<OpeParts> opePartsList = new ArrayList<>(opePartsService.listByIds(opeAllocateBList.stream().map(OpeAllocateB::getPartId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opePartsList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //备料批次号
        String batchNo = ProductContractEnums.REDE.getCode() + RandomUtil.randomNumbers(8);
        //子表数据更新
        opeAllocateBList.forEach(item -> {
            if (item.getPreparationWaitQty() != savePartBasicDateMap.get(item.getId()).size()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getMessage());
            }
            int partQty = 0;
            //便利部件基本数据
            for (SavePartBasicDateEnter partMap : savePartBasicDateMap.get(item.getId())) {
                opePartsList.forEach(part -> {
                    if (item.getPartId().equals(part.getId())) {
                        //设置备料记录
                        opeAllocateBTraceList.add(
                                OpeAllocateBTrace.builder()
                                        .id(idAppService.getId(SequenceName.OPE_ALLOCATE_B_TRACE))
                                        .dr(0)
                                        .allocateId(item.getAllocateId())
                                        .allocateBId(item.getId())
                                        .partId(item.getPartId())
                                        .batchNo(batchNo)
                                        .qty(item.getPreparationWaitQty())
                                        .serialNum(partMap.getSerialN())
                                        .revision(0)
                                        .createdBy(enter.getUserId())
                                        .createdTime(new Date())
                                        .updatedBy(enter.getUserId())
                                        .updatedTime(new Date())
                                        .build()
                        );
                    }

                });
                partQty += savePartBasicDateMap.get(item.getId()).size();
            }
            //待备料数量减掉
            item.setPreparationWaitQty(item.getPreparationWaitQty() - partQty);
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });

        opeAllocate.setPreparationWaitTotal(0);
        opeAllocate.setUpdatedBy(enter.getUserId());
        opeAllocate.setUpdatedTime(new Date());
        //主表记录更新
        opeAllocateService.updateById(opeAllocate);
        //子表记录更新
        opeAllocateBService.updateBatch(opeAllocateBList);
        //备料记录更新
        opeAllocateBTraceService.saveBatch(opeAllocateBTraceList);

        return new GeneralResult(enter.getRequestId());
    }
}
