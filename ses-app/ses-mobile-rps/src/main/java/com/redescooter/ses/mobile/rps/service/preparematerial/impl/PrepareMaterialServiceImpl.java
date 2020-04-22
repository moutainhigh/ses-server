package com.redescooter.ses.mobile.rps.service.preparematerial.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.production.ProductContractEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderEventEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.preparematerial.PrepareMaterialServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocate;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateB;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateBTrace;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateTrace;
import com.redescooter.ses.mobile.rps.dm.OpeAssembiyOrderTrace;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrderPart;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyPreparation;
import com.redescooter.ses.mobile.rps.dm.OpeParts;
import com.redescooter.ses.mobile.rps.dm.OpeStockBill;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssembiyOrderTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockBillService;
import com.redescooter.ses.mobile.rps.service.base.impl.OpeAssemblyOrderPartService;
import com.redescooter.ses.mobile.rps.service.base.impl.OpeAssemblyPreparationService;
import com.redescooter.ses.mobile.rps.service.preparematerial.PrepareMaterialService;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialDetailResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.PrepareMaterialListResult;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePartBasicDateEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.SavePrepareMaterialPartListEnter;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAllocateBTraceService opeAllocateBTraceService;

    @Autowired
    private OpeAssemblyPreparationService opeAssemblyPreparationService;

    @Autowired
    private OpeAssemblyOrderPartService opeAssemblyOrderPartService;

    @Autowired
    private ReceiptTraceService receiptTraceService;

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
        List<OpeStockBill> opeStockBillList = prepareMaterialServiceMapper.stockBillList(enter);
        if (CollectionUtils.isEmpty(opeStockBillList)) {
            return PageResult.createZeroRowResult(enter);
        }

        //调拨单Id
        List<Long> allocateIds =
                opeStockBillList.stream().filter(item -> (StringUtils.equals(item.getSourceType(), SourceTypeEnums.ALLOCATE.getValue()))).map(OpeStockBill::getSourceId).collect(Collectors.toList());
        //调拨单查询
        Collection<OpeAllocate> opeAllocateList =
                opeAllocateService.list(new LambdaQueryWrapper<OpeAllocate>().eq(OpeAllocate::getStatus, AllocateOrderStatusEnums.PENDING.getValue()).in(OpeAllocate::getId, allocateIds));

        //组装单Id
        List<Long> assemblyIds =
                opeStockBillList.stream().filter(item -> (StringUtils.equals(item.getSourceType(), SourceTypeEnums.ALLOCATE.getValue()))).map(OpeStockBill::getSourceId).collect(Collectors.toList());
        //组装单查询
        List<OpeAssemblyOrder> opeAssemblyOrderList =
                opeAssemblyOrderService.list(new LambdaQueryWrapper<OpeAssemblyOrder>().eq(OpeAssemblyOrder::getStatus, AssemblyStatusEnums.PENDING.getValue()).in(OpeAssemblyOrder::getId,
                        assemblyIds));

        List<PrepareMaterialListResult> prepareMaterialList = new ArrayList<>();
        //调拨单赋值
        if (CollectionUtils.isNotEmpty(opeAllocateList)) {
            opeAllocateList.forEach(item -> {
                prepareMaterialList.add(
                        PrepareMaterialListResult.builder()
                                .id(item.getId())
                                .sourceType(SourceTypeEnums.ALLOCATE.getValue())
                                .preparationWaitTotal(item.getPreparationWaitTotal())
                                .createdTime(item.getCreatedTime())
                                .allocatN(item.getAllocateNum())
                                .build()
                );
            });
        }

        //组装单赋值
        if (CollectionUtils.isNotEmpty(opeAssemblyOrderList)) {
            opeAssemblyOrderList.forEach(item -> {
                prepareMaterialList.add(
                        PrepareMaterialListResult.builder()
                                .id(item.getId())
                                .sourceType(SourceTypeEnums.ALLOCATE.getValue())
                                .preparationWaitTotal(item.getWaitPreparationTotal())
                                .createdTime(item.getCreatedTime())
                                .allocatN(item.getAssemblyNumber())
                                .build()
                );
            });
        }
        return PageResult.create(enter, prepareMaterialList.size(), prepareMaterialList);
    }

    /**
     * 待备料详情
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PrepareMaterialDetailResult> detail(PrepareMaterialDetailEnter enter) {

        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ALLOCATE.getValue())) {
            //调拨单校验
            OpeAllocate opeAllocate = opeAllocateService.getById(enter.getId());
            if (opeAllocate == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.PENDING.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            //调拨单数据查询
            int count = prepareMaterialServiceMapper.allocateListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            return PageResult.create(enter, count, prepareMaterialServiceMapper.allocatelList(enter));
        }

        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ASSEMBLY.getValue())) {
            //组装备料
            OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(enter.getId());
            if (opeAssemblyOrder == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
            }
            if (!StringUtils.equals(opeAssemblyOrder.getStatus(), AssemblyStatusEnums.PREPARE_MATERIAL.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }
            //组装数据查询
            int count = prepareMaterialServiceMapper.assemblyListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            return PageResult.create(enter, count, prepareMaterialServiceMapper.assemblyList(enter));
        }
        return PageResult.createZeroRowResult(enter);
    }

    /**
     * 保存
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult save(SavePrepareMaterialEnter enter) {
        //备料部件集合
        List<SavePrepareMaterialPartListEnter> savePrepareMaterialListEnterList = new ArrayList<>();
        //部件基本数据
        Map<Long, List<SavePartBasicDateEnter>> savePartBasicDateMap = Maps.newHashMap();

        //调拨单备料记录集合
        List<OpeAllocateBTrace> opeAllocateBTraceList = Lists.newArrayList();
        //组装单备料集合
        List<OpeAssemblyPreparation> saveAssemblyPreparationList = Lists.newArrayList();

        //对入参参数进行解析
        try {
            //解析集合列表
            savePrepareMaterialListEnterList.addAll(JSON.parseArray(enter.getPartJson(), SavePrepareMaterialPartListEnter.class));
            if (CollectionUtils.isEmpty(savePrepareMaterialListEnterList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
            //解析部件备料的序列号
            savePrepareMaterialListEnterList.forEach(item -> {
                savePartBasicDateMap.put(item.getId(), JSON.parseArray(item.getPartListJson(), SavePartBasicDateEnter.class));
            });
            if (savePartBasicDateMap.isEmpty()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }

        //校验调拨主订单
        List<Long> partIds = Lists.newArrayList();
        //调拨单子表列表
        List<OpeAllocateB> opeAllocateBList = null;
        //组装单子表列表
        List<OpeAssemblyOrderPart> opeAssemblyOrderPartList = null;
        //调拨单
        OpeAllocate opeAllocate = null;
        //组装单
        OpeAssemblyOrder opeAssemblyOrder = null;
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ALLOCATE.getValue())) {
            opeAllocate = opeAllocateService.getById(enter.getId());
            if (opeAllocate == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.PENDING.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            //校验调拨子订单
            opeAllocateBList =
                    new ArrayList<>(opeAllocateBService.listByIds(savePrepareMaterialListEnterList.stream().map(SavePrepareMaterialPartListEnter::getId).collect(Collectors.toList())));
            if (opeAllocateBList.size() != savePrepareMaterialListEnterList.size()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }
            partIds.addAll(opeAllocateBList.stream().map(OpeAllocateB::getPartId).collect(Collectors.toList()));
        }
        //校验组装主订单
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ASSEMBLY.getValue())) {
            opeAssemblyOrder = opeAssemblyOrderService.getById(enter.getId());
            if (opeAssemblyOrder == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
            }
            if (StringUtils.equals(opeAssemblyOrder.getStatus(), AssemblyStatusEnums.PENDING.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            opeAssemblyOrderPartList =
                    new ArrayList(opeAssemblyOrderPartService.listByIds(savePrepareMaterialListEnterList.stream().map(SavePrepareMaterialPartListEnter::getId).collect(Collectors.toList())));
            if (opeAssemblyOrderPartList.size() != savePrepareMaterialListEnterList.size()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
            }
            partIds.addAll(opeAssemblyOrderPartList.stream().map(OpeAssemblyOrderPart::getPartId).collect(Collectors.toList()));
        }

        //查询配件信息
        List<OpeParts> opePartsList = new ArrayList(opePartsService.listByIds(partIds));
        if (CollectionUtils.isEmpty(opePartsList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //todo 备料批次号 需修改
        String batchNo = ProductContractEnums.REDE.getCode() + RandomUtil.randomNumbers(8);

        //部件总数
        int totalPart = 0;
        if (StringUtils.equals(SourceTypeEnums.ALLOCATE.getValue(), enter.getSourceType())) {
            //子表数据更新
            for (OpeAllocateB opeAllocateB : opeAllocateBList) {
                int partQty = 0;
                //便利部件基本数据
                for (SavePartBasicDateEnter partMap : savePartBasicDateMap.get(opeAllocateB.getId())) {
                    for (OpeParts part : opePartsList) {
                        if (opeAllocateB.getPartId().equals(part.getId())) {
                            //设置备料记录
                            opeAllocateBTraceList.add(
                                    buildOpeAllocateBTrace(enter.getUserId(), batchNo, opeAllocateB, partMap)
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
            //判断是否要更新主订单状态
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
                receiptTraceService.saveAllocateNode(saveNodeEnter);
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
        }
        if (StringUtils.equals(SourceTypeEnums.ASSEMBLY.getValue(), enter.getSourceType())) {
            for (OpeAssemblyOrderPart item : opeAssemblyOrderPartList) {
                int partQty = 0;
                //便利部件基本数据
                for (SavePartBasicDateEnter value : savePartBasicDateMap.get(item.getId())) {
                    saveAssemblyPreparationList.add(buildOpeAssemblyPreparation(enter.getUserId(), batchNo, item, value));
                    //备料数量维护
                    partQty += value.getQty();
                    totalPart += value.getQty();
                }
                if (item.getWaitPreparationQty() != partQty) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getMessage());
                }

                //待备料数量减掉
                item.setWaitPreparationQty(item.getWaitPreparationQty() - partQty);
                item.setUpdatedBy(enter.getUserId());
                item.setUpdatedTime(new Date());
            }

            //判断是否要更新主订单状态
            Boolean updateOpeAssemblyOrderPartStatus = Boolean.TRUE;
            for (OpeAssemblyOrderPart item : opeAssemblyOrderPartList) {
                if (item.getWaitPreparationQty() != 0) {
                    updateOpeAssemblyOrderPartStatus = Boolean.FALSE;
                }
            }

            if (updateOpeAssemblyOrderPartStatus) {
                opeAssemblyOrder.setWaitPreparationTotal(0);
                opeAssemblyOrder.setStatus(AssemblyStatusEnums.ASSEMBLING.getValue());

                //更新节点
                SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
                BeanUtils.copyProperties(enter, saveNodeEnter);
                saveNodeEnter.setId(opeAllocate.getId());
                saveNodeEnter.setStatus(AssemblyStatusEnums.ASSEMBLING.getValue());
                saveNodeEnter.setEvent(AssemblyStatusEnums.ASSEMBLING.getValue());
                saveNodeEnter.setMemo(null);
                receiptTraceService.saveAssemblyNode(saveNodeEnter);
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

        }


        return new GeneralResult(enter.getRequestId());
    }

    private OpeAllocateBTrace buildOpeAllocateBTrace(Long userId, String batchNo, OpeAllocateB opeAllocateB, SavePartBasicDateEnter partMap) {
        return OpeAllocateBTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_ALLOCATE_B_TRACE))
                .dr(0)
                .allocateId(opeAllocateB.getAllocateId())
                .allocateBId(opeAllocateB.getId())
                .partId(opeAllocateB.getPartId())
                .batchNo(batchNo)
                .qty(partMap.getQty())
                .serialNum(partMap.getSerialN())
                .revision(0)
                .createdBy(userId)
                .createdTime(new Date())
                .updatedBy(userId)
                .updatedTime(new Date())
                .build();
    }

    /**
     * buildOpeAssemblyPreparation 赋值
     *
     * @param userId
     * @param batchNo
     * @param item
     * @param value
     * @return
     */
    private OpeAssemblyPreparation buildOpeAssemblyPreparation(Long userId, String batchNo, OpeAssemblyOrderPart item, SavePartBasicDateEnter value) {
        return OpeAssemblyPreparation.builder()
                .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_PREPARATION))
                .dr(0)
                .userId(userId)
                .tenantId(userId)
                .assemblyId(item.getAssemblyId())
                .assemblyOrderPartId(item.getId())
                .partId(item.getPartId())
                .serialNum(value.getSerialN())
                .batchNo(batchNo)
                .qty(value.getQty())
                .revision(0)
                .createdBy(userId)
                .createdTime(new Date())
                .updatedBy(userId)
                .updatedTime(new Date())
                .build();
    }
}
