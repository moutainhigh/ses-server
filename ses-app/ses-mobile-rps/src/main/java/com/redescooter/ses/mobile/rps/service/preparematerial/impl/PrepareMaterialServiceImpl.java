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
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.preparematerial.PrepareMaterialServiceMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocate;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateB;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateBTrace;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrderPart;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyPreparation;
import com.redescooter.ses.mobile.rps.dm.OpeParts;
import com.redescooter.ses.mobile.rps.dm.OpeStockProdPart;
import com.redescooter.ses.mobile.rps.dm.OpeStockPurchas;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderPartService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyPreparationService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockBillService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockProdPartService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockPurchasService;
import com.redescooter.ses.mobile.rps.service.preparematerial.PrepareMaterialService;
import com.redescooter.ses.mobile.rps.vo.bo.QueryStockBillDto;
import com.redescooter.ses.mobile.rps.vo.preparematerial.AllocatePreparationEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.AssemblyPreparationEnter;
import com.redescooter.ses.mobile.rps.vo.preparematerial.ConfirmPreparationEnter;
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

    @Autowired
    private OpeStockProdPartService opeStockProdPartService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpeStockPurchasService opeStockPurchasService;

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
        //查询调拨单统计
//        int allocateListCount = prepareMaterialServiceMapper.allocatListCount(enter);
        int allocateListCount = opeAllocateService.count(new LambdaQueryWrapper<OpeAllocate>().eq(OpeAllocate::getStatus, AllocateOrderStatusEnums.PENDING.getValue()));
        //组装单
//        int assemblyListCount = prepareMaterialServiceMapper.assemblyListCount(enter);
        int assemblyListCount = opeAssemblyOrderService.count(new LambdaQueryWrapper<OpeAssemblyOrder>().eq(OpeAssemblyOrder::getStatus, AssemblyStatusEnums.PREPARE_MATERIAL.getValue()));

        if (allocateListCount + assemblyListCount == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        //调拨单数据
        List<PrepareMaterialListResult> allocatList = prepareMaterialServiceMapper.allocatList(enter);
        //组装单数据
        List<PrepareMaterialListResult> assemblyList = prepareMaterialServiceMapper.assemblyList(enter);

        List<PrepareMaterialListResult> prepareMaterialList = new ArrayList(allocatList);
        prepareMaterialList.addAll(assemblyList);
        //调拨单赋值
        return PageResult.create(enter, prepareMaterialList.size(), prepareMaterialList);
    }

    /**
     * 确认备料
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult confirmPreparation(ConfirmPreparationEnter enter) {
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ALLOCATE.getValue())) {
            //调拨单校验
            OpeAllocate opeAllocate = opeAllocateService.getById(enter.getId());
            if (opeAllocate == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.PENDING.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            //主订单修改状态
            opeAllocate.setStatus(AllocateOrderStatusEnums.PREPARE.getValue());
            opeAllocate.setUpdatedBy(enter.getUserId());
            opeAllocate.setUpdatedTime(new Date());
            opeAllocateService.updateById(opeAllocate);
            //更新节点
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opeAllocate.getId());
            saveNodeEnter.setStatus(AllocateOrderStatusEnums.PREPARE.getValue());
            saveNodeEnter.setEvent(AllocateOrderEventEnums.INPROGRESS.getValue());
            saveNodeEnter.setMemo(null);
            receiptTraceService.saveAllocateNode(saveNodeEnter);
        }
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ASSEMBLY.getValue())) {
            //组装备料
            OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(enter.getId());
            if (opeAssemblyOrder == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
            }
            if (!StringUtils.equals(opeAssemblyOrder.getStatus(), AssemblyStatusEnums.PENDING.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            opeAssemblyOrder.setStatus(AssemblyStatusEnums.PREPARE_MATERIAL.getValue());
            opeAssemblyOrder.setUpdatedBy(enter.getUserId());
            opeAssemblyOrder.setUpdatedTime(new Date());
            opeAssemblyOrderService.updateById(opeAssemblyOrder);
            //更新节点
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opeAssemblyOrder.getId());
            saveNodeEnter.setStatus(AssemblyStatusEnums.PREPARE_MATERIAL.getValue());
            saveNodeEnter.setEvent(AssemblyStatusEnums.PREPARE_MATERIAL.getValue());
            saveNodeEnter.setMemo(null);
            receiptTraceService.saveAssemblyNode(saveNodeEnter);
        }
        return new GeneralResult(enter.getRequestId());
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
            if (!StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.PREPARE.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            //调拨单数据查询
            int count = prepareMaterialServiceMapper.detailAllocateListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            List<PrepareMaterialDetailResult> resultList = prepareMaterialServiceMapper.detailAllocatelList(enter);

            //查询符合条件的序列号
            List<OpeStockPurchas> stockPurchasList =
                    opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>()
                            .in(OpeStockPurchas::getPartId, resultList.stream().map(PrepareMaterialDetailResult::getPartId).collect(Collectors.toList()))
                    .eq(OpeStockPurchas::getStatus,StockProductPartStatusEnums.OUT_WH.getValue()));
            if (CollectionUtils.isEmpty(stockPurchasList)){
                return PageResult.create(enter, count, resultList);
            }

            //封装 序列号
            resultList.forEach(item->{
                List<String> serialList = Lists.newArrayList();
                stockPurchasList.forEach(stock->{
                    if(item.getPartId().equals(stock.getStatus())){
                        serialList.add(stock.getSerialNumber());
                    }
                });
                item.setSerialNumList(serialList);
            });

            return PageResult.create(enter, count, resultList);
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
            int count = prepareMaterialServiceMapper.detailAssemblyListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            List<PrepareMaterialDetailResult> resultList = prepareMaterialServiceMapper.detailAssemblyList(enter);

            //查询序列号
            List<OpeStockProdPart> opeStockProdPartList = opeStockProdPartService.list(new LambdaQueryWrapper<OpeStockProdPart>()
                    .in(OpeStockProdPart::getPartId,resultList.stream().map(PrepareMaterialDetailResult::getPartId).collect(Collectors.toList()))
                    .eq(OpeStockProdPart::getStatus, StockProductPartStatusEnums.OUT_WH.getValue()));

            //封装 序列号
            resultList.forEach(item->{
                List<String> serialList = Lists.newArrayList();
                opeStockProdPartList.forEach(stock->{
                    if(item.getPartId().equals(stock.getStatus())){
                        serialList.add(stock.getSerialNumber());
                    }
                });
                item.setSerialNumList(serialList);
            });

            return PageResult.create(enter, count, resultList);
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
        //调拨单备料
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ALLOCATE.getValue())) {
            AllocatePreparationEnter allocatePreparationEnter = new AllocatePreparationEnter();
            BeanUtils.copyProperties(enter, allocatePreparationEnter);
            allocatePreparationEnter.setId(enter.getId());
            allocatePreparationEnter.setSavePartBasicDateMap(savePartBasicDateMap);
            allocatePreparationEnter.setSavePrepareMaterialListEnterList(savePrepareMaterialListEnterList);
            allocatePreparation(allocatePreparationEnter);
        }
        //组装单备料
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ASSEMBLY.getValue())) {
            AssemblyPreparationEnter assemblyPreparation = new AssemblyPreparationEnter();
            BeanUtils.copyProperties(enter, assemblyPreparation);
            assemblyPreparation.setId(enter.getId());
            assemblyPreparation.setSavePartBasicDateMap(savePartBasicDateMap);
            assemblyPreparation.setSavePrepareMaterialListEnterList(savePrepareMaterialListEnterList);
            assemblyPreparation(assemblyPreparation);
        }
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 调拨单备料
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult allocatePreparation(AllocatePreparationEnter enter) {
        //调拨单备料记录集合
        List<OpeAllocateBTrace> opeAllocateBTraceList = Lists.newArrayList();

        //校验调拨主订单
        List<Long> partIds = Lists.newArrayList();

        OpeAllocate opeAllocate = opeAllocateService.getById(enter.getId());
        if (opeAllocate == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.PREPARE.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        //校验调拨子订单
        List<OpeAllocateB> opeAllocateBList =
                new ArrayList<>(opeAllocateBService.listByIds(enter.getSavePrepareMaterialListEnterList().stream().map(SavePrepareMaterialPartListEnter::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opeAllocateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        if (opeAllocateBList.size() != enter.getSavePrepareMaterialListEnterList().size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
        }
        partIds.addAll(opeAllocateBList.stream().map(OpeAllocateB::getPartId).collect(Collectors.toList()));


        //查询配件信息
        List<OpeParts> opePartsList = new ArrayList(opePartsService.listByIds(partIds));
        if (CollectionUtils.isEmpty(opePartsList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //查询调拨对应的出库单
        List<QueryStockBillDto> queryStockBillDtoList = prepareMaterialServiceMapper.queryStockBillBySourceTypeId(enter.getId(), SourceTypeEnums.ALLOCATE.getValue());


        //有Id 验证 序列号
        List<String> serialNList = new ArrayList<>();
        //没有Id 验证部品数量
        Map<Long, Integer> stockProdPartIdMap = Maps.newHashMap();

        //判断有Id 的序列号是否存在
        opeAllocateBList.forEach(item -> {
            opePartsList.forEach(part -> {
                enter.getSavePartBasicDateMap().get(item.getId()).forEach(serialN -> {
                    //判断part 有Id 验证序列号 没有Id 验证数量
                    if (part.getId().equals(serialN.getPartId()) && part.getIdClass()) {
                        serialNList.add(serialN.getSerialN());
                    } else {
                        if (stockProdPartIdMap.containsKey(serialN.getPartId())) {
                            stockProdPartIdMap.put(serialN.getPartId(), stockProdPartIdMap.get(serialN.getPartId()) + serialN.getQty());
                        } else {
                            stockProdPartIdMap.put(serialN.getPartId(), serialN.getQty());
                        }
                    }
                });
            });
        });

        if (CollectionUtils.isNotEmpty(serialNList)) {
            //有Id 校验
            List<OpeStockPurchas> opeStockProdPartListIdClassTrue=opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>().in(OpeStockPurchas::getSerialNumber, serialNList));
            if (CollectionUtils.isEmpty(opeStockProdPartListIdClassTrue)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
            opeStockProdPartListIdClassTrue.forEach(item -> {
                if (!StringUtils.equals(item.getStatus(), StockProductPartStatusEnums.AVAILABLE.getValue())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getCode(), ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getMessage());
                }

                //修改数据
                queryStockBillDtoList.forEach(stockStill -> {
                    if (stockStill.getPartId().equals(item.getPartId())) {
                        item.setOutStockBillId(stockStill.getStockBillId());
                        item.setOutPrincipalId(enter.getUserId());
                        item.setOutStockTime(new Date());
                        item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                        item.setUpdatedBy(enter.getUserId());
                        item.setUpdatedTime(new Date());
                    }
                });
            });
            opeStockPurchasService.updateBatch(opeStockProdPartListIdClassTrue);
        }

        if (!stockProdPartIdMap.isEmpty()) {

            //无Id校验
            List<OpeStockPurchas> opeStockProdPartListIdClassFalse =
                    opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>().in(OpeStockPurchas::getPartId, stockProdPartIdMap.keySet()).orderByAsc(OpeStockPurchas::getCreatedTime));
            if (CollectionUtils.isEmpty(opeStockProdPartListIdClassFalse)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }

            //减掉 库存条目中的数量
            opeStockProdPartListIdClassFalse.forEach(item -> {
                if (!StringUtils.equals(item.getStatus(), StockProductPartStatusEnums.AVAILABLE.getValue())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getCode(), ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getMessage());
                }
                //修改数据
                Integer partQty = stockProdPartIdMap.get(item.getPartId());

                for (QueryStockBillDto stockStill : queryStockBillDtoList) {
                    if (stockStill.getPartId().equals(item.getPartId())) {

                        if (partQty >= item.getInWhQty()) {
                            partQty -= item.getInWhQty();
                            item.setInWhQty(0);
                            item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                            item.setOutStockBillId(stockStill.getStockBillId());
                            item.setOutPrincipalId(enter.getUserId());
                            item.setOutStockTime(new Date());
                            item.setUpdatedBy(enter.getUserId());
                            item.setUpdatedTime(new Date());
                        }

                        if (partQty < item.getInWhQty()) {
                            item.setInWhQty(item.getInWhQty() - partQty);
                            item.setOutStockBillId(stockStill.getStockBillId());
                            item.setOutPrincipalId(enter.getUserId());
                            item.setOutStockTime(new Date());
                            item.setUpdatedBy(enter.getUserId());
                            item.setUpdatedTime(new Date());
                        }

                        if (partQty == 0) {
                            break;
                        }
                    }
                }
            });
            opeStockPurchasService.updateBatch(opeStockProdPartListIdClassFalse);
        }


        //todo 备料批次号 需修改
        String batchNo = ProductContractEnums.REDE.getCode() + RandomUtil.randomNumbers(8);

        //部件总数
        int totalPart = 0;
        //子表数据更新
        for (OpeAllocateB opeAllocateB : opeAllocateBList) {
            int partQty = 0;
            //便利部件基本数据
            for (SavePartBasicDateEnter partMap : enter.getSavePartBasicDateMap().get(opeAllocateB.getId())) {
                if (opeAllocateB.getPartId().equals(partMap.getPartId())) {
                    //设置备料记录
                    opeAllocateBTraceList.add(buildOpeAllocateBTrace(enter.getUserId(), batchNo, opeAllocateB, partMap));
                }
                //部件数量校验
                if (!opeAllocateB.getPreparationWaitQty().equals(partMap.getQty())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getMessage());
                }

                partQty += partMap.getQty();
                totalPart += partMap.getQty();
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
            opeAllocate.setStatus(AllocateOrderStatusEnums.ALLOCATE.getValue());

            //更新节点
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opeAllocate.getId());
            saveNodeEnter.setStatus(AllocateOrderStatusEnums.ALLOCATE.getValue());
            saveNodeEnter.setEvent(AllocateOrderEventEnums.ALLOCATE.getValue());
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
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 组装单备料
     *
     * @param enter
     * @return
     */
    @Override
    public GeneralResult assemblyPreparation(AssemblyPreparationEnter enter) {
        //组装单备料集合
        List<OpeAssemblyPreparation> saveAssemblyPreparationList = Lists.newArrayList();

        //校验调拨主订单
        List<Long> partIds = Lists.newArrayList();


        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(enter.getId());
        if (opeAssemblyOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeAssemblyOrder.getStatus(), AssemblyStatusEnums.PREPARE_MATERIAL.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        List<OpeAssemblyOrderPart> opeAssemblyOrderPartList =
                new ArrayList(opeAssemblyOrderPartService.listByIds(enter.getSavePrepareMaterialListEnterList().stream().map(SavePrepareMaterialPartListEnter::getId).collect(Collectors.toList())));
        if (opeAssemblyOrderPartList.size() != enter.getSavePrepareMaterialListEnterList().size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
        }
        partIds.addAll(opeAssemblyOrderPartList.stream().map(OpeAssemblyOrderPart::getPartId).collect(Collectors.toList()));

        //查询配件信息
        List<OpeParts> opePartsList = new ArrayList(opePartsService.listByIds(partIds));
        if (CollectionUtils.isEmpty(opePartsList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //查询调拨对应的出库单
        List<QueryStockBillDto> queryStockBillDtoList = prepareMaterialServiceMapper.queryStockBillBySourceTypeId(enter.getId(), SourceTypeEnums.ASSEMBLY.getValue());


        //有Id 验证 序列号
        List<String> serialNList = new ArrayList<>();
        //没有Id 验证部品数量
        Map<Long, Integer> stockProdPartIdMap = Maps.newHashMap();

        //判断有Id 的序列号是否存在
        opeAssemblyOrderPartList.forEach(item -> {
            opePartsList.forEach(part -> {
                enter.getSavePartBasicDateMap().get(item.getId()).forEach(serialN -> {
                    //判断part 有Id 验证序列号 没有Id 验证数量
                    if (part.getId().equals(serialN.getPartId())){
                        if ( part.getIdClass()) {
                            serialNList.add(serialN.getSerialN());
                        } else {
                            if (!stockProdPartIdMap.containsKey(serialN.getPartId())) {
                                stockProdPartIdMap.put(serialN.getPartId(), serialN.getQty());
                            } else {
                                stockProdPartIdMap.put(serialN.getPartId(), stockProdPartIdMap.get(serialN.getPartId()) + serialN.getQty());
                            }
                        }
                    }
                });
            });
        });

        if (CollectionUtils.isNotEmpty(serialNList)) {
            //有Id 校验
            List<OpeStockProdPart> opeStockProdPartListIdClassTrue = opeStockProdPartService.list(new LambdaQueryWrapper<OpeStockProdPart>().in(OpeStockProdPart::getSerialNumber, serialNList));
            if (CollectionUtils.isEmpty(opeStockProdPartListIdClassTrue)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
            opeStockProdPartListIdClassTrue.forEach(item -> {
                if (!StringUtils.equals(item.getStatus(), StockProductPartStatusEnums.AVAILABLE.getValue())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getCode(), ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getMessage());
                }

                //修改数据
                queryStockBillDtoList.forEach(stockStill -> {
                    if (stockStill.getPartId().equals(item.getPartId())) {
                        item.setOutStockBillId(stockStill.getStockBillId());
                        item.setOutPrincipalId(enter.getUserId());
                        item.setOutStockTime(new Date());
                        item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                        item.setUpdatedBy(enter.getUserId());
                        item.setUpdatedTime(new Date());
                    }
                });
            });
            opeStockProdPartService.updateBatch(opeStockProdPartListIdClassTrue);
        }

        if (!stockProdPartIdMap.isEmpty()) {
            //无Id校验
            List<OpeStockProdPart> opeStockProdPartListIdClassFalse =
                    opeStockProdPartService.list(new LambdaQueryWrapper<OpeStockProdPart>().in(OpeStockProdPart::getPartId, stockProdPartIdMap.keySet()).orderByAsc(OpeStockProdPart::getCreatedTime));
            if (CollectionUtils.isEmpty(opeStockProdPartListIdClassFalse)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }

            //减掉 库存条目中的数量
            opeStockProdPartListIdClassFalse.forEach(item -> {
                if (!StringUtils.equals(item.getStatus(), StockProductPartStatusEnums.AVAILABLE.getValue())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getCode(), ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getMessage());
                }
                //修改数据
                Integer partQty = stockProdPartIdMap.get(item.getPartId());

                for (QueryStockBillDto stockStill : queryStockBillDtoList) {
                    if (stockStill.getPartId().equals(item.getPartId())) {

                        if (partQty >= item.getInWhQty()) {
                            partQty -= item.getInWhQty();
                            item.setInWhQty(0);
                            item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                            item.setOutStockBillId(stockStill.getStockBillId());
                            item.setOutPrincipalId(enter.getUserId());
                            item.setOutStockTime(new Date());
                            item.setUpdatedBy(enter.getUserId());
                            item.setUpdatedTime(new Date());
                        }

                        if (partQty < item.getInWhQty()) {
                            item.setInWhQty(item.getInWhQty() - partQty);
                            item.setOutStockBillId(stockStill.getStockBillId());
                            item.setOutPrincipalId(enter.getUserId());
                            item.setOutStockTime(new Date());
                            item.setUpdatedBy(enter.getUserId());
                            item.setUpdatedTime(new Date());
                        }

                        if (partQty == 0) {
                            break;
                        }
                    }
                }
            });
            opeStockProdPartService.updateBatch(opeStockProdPartListIdClassFalse);
        }


        //todo 备料批次号 需修改
        String batchNo = ProductContractEnums.REDE.getCode() + RandomUtil.randomNumbers(8);

        //部件总数
        int totalPart = 0;

        for (OpeAssemblyOrderPart item : opeAssemblyOrderPartList) {
            int partQty = 0;
            //便利部件基本数据
            for (SavePartBasicDateEnter value : enter.getSavePartBasicDateMap().get(item.getId())) {
                //组装备料表数据保存
                saveAssemblyPreparationList.add(buildOpeAssemblyPreparation(enter.getUserId(), batchNo, item, value));
                //备料数量维护
                partQty += value.getQty();
                totalPart += value.getQty();
            }

            //部件数量校验
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
            saveNodeEnter.setId(opeAssemblyOrder.getId());
            saveNodeEnter.setStatus(AssemblyStatusEnums.ASSEMBLING.getValue());
            saveNodeEnter.setEvent(AssemblyStatusEnums.ASSEMBLING.getValue());
            saveNodeEnter.setMemo(null);
            receiptTraceService.saveAssemblyNode(saveNodeEnter);
        } else {
            opeAssemblyOrder.setWaitPreparationTotal(opeAssemblyOrder.getWaitPreparationTotal() - totalPart);
        }
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        //主表记录更新
        opeAssemblyOrderService.updateById(opeAssemblyOrder);
        //部件表记录更新
        opeAssemblyOrderPartService.updateBatch(opeAssemblyOrderPartList);
        // 备料数据保存
        opeAssemblyPreparationService.saveBatch(saveAssemblyPreparationList);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * buildOpeAllocateBTrace 备料子记录做保存
     *
     * @param userId
     * @param batchNo
     * @param opeAllocateB
     * @param partMap
     * @return
     */
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
