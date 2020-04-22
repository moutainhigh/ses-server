package com.redescooter.ses.mobile.rps.service.preparematerial.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.production.ProductContractEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderEventEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.preparematerial.PrepareMaterialServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.preparematerial.PrepareMaterialService;
import com.redescooter.ses.mobile.rps.vo.preparematerial.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private OpeAllocateTraceService opeAllocateTraceService;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private OpeAllocateBTraceService opeAllocateBTraceService;

    @Autowired
    private OpeStockBillService opeStockBillService;

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
        //查询出库单据
        //opeStockBillService.list(new LambdaQueryWrapper<OpeStockBill>().eq(OpeStockBill::getSourceType));
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
            savePrepareMaterialListEnterList.addAll(JSON.parseArray(enter.getPartJson(), SavePrepareMaterialPartListEnter.class));
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

        //todo 备料批次号 需修改
        String batchNo = ProductContractEnums.REDE.getCode() + RandomUtil.randomNumbers(8);

        //部件总数列表
        int totalPart = 0;
        //子表数据更新
        for (OpeAllocateB opeAllocateB : opeAllocateBList) {
            int partQty = 0;
            //便利部件基本数据
            for (SavePartBasicDateEnter partMap : savePartBasicDateMap.get(opeAllocateB.getId())) {
                for (OpeParts part : opePartsList) {
                    if (opeAllocateB.getPartId().equals(part.getId())) {
                        //设置备料记录
                        opeAllocateBTraceList.add(
                                OpeAllocateBTrace.builder()
                                        .id(idAppService.getId(SequenceName.OPE_ALLOCATE_B_TRACE))
                                        .dr(0)
                                        .allocateId(opeAllocateB.getAllocateId())
                                        .allocateBId(opeAllocateB.getId())
                                        .partId(opeAllocateB.getPartId())
                                        .batchNo(batchNo)
                                        .qty(partMap.getQty())
                                        .serialNum(partMap.getSerialN())
                                        .revision(0)
                                        .createdBy(enter.getUserId())
                                        .createdTime(new Date())
                                        .updatedBy(enter.getUserId())
                                        .updatedTime(new Date())
                                        .build()
                        );
                    }
                    partQty += partMap.getQty();
                    totalPart += partMap.getQty();
                }
                if (!opeAllocateB.getPreparationWaitQty().equals(partMap.getQty())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getMessage());
                }
            }
            //待备料数量减掉
            opeAllocateB.setPreparationWaitQty(opeAllocateB.getPreparationWaitQty() - partQty);
            opeAllocateB.setUpdatedBy(enter.getUserId());
            opeAllocateB.setUpdatedTime(new Date());
        }

        Boolean updateOpeAllocateStatus = Boolean.TRUE;
        for (OpeAllocateB item : opeAllocateBList) {
            if (item.getPreparationWaitQty() != 0) {
                updateOpeAllocateStatus = Boolean.FALSE;
            }
        }

        if (updateOpeAllocateStatus) {
            opeAllocate.setPreparationWaitTotal(0);
            opeAllocate.setStatus(AllocateOrderStatusEnums.PREPARE.getValue());

            //更新节点
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opeAllocate.getId());
            saveNodeEnter.setStatus(AllocateOrderStatusEnums.PREPARE.getValue());
            saveNodeEnter.setEvent(AllocateOrderEventEnums.INPROGRESS.getValue());
            saveNodeEnter.setMemo(null);
            this.saveAllocateNode(saveNodeEnter);
        } else {
            opeAllocate.setPreparationWaitTotal(opeAllocate.getPreparationWaitTotal() - totalPart);
        }
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

    /**
     * 保存节点
     *
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveAllocateNode(SaveNodeEnter enter) {
        opeAllocateTraceService.save(OpeAllocateTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .allocateId(enter.getId())
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
