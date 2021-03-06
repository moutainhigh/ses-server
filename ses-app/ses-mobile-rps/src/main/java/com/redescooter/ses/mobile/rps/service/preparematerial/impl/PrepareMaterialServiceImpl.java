package com.redescooter.ses.mobile.rps.service.preparematerial.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.ProductContractEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderEventEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
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
import com.redescooter.ses.mobile.rps.dm.OpeStock;
import com.redescooter.ses.mobile.rps.dm.OpeStockProdPart;
import com.redescooter.ses.mobile.rps.dm.OpeStockPurchas;
import com.redescooter.ses.mobile.rps.dm.OpeWhse;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateBTraceService;
import com.redescooter.ses.mobile.rps.service.base.OpeAllocateService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderPartService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyPreparationService;
import com.redescooter.ses.mobile.rps.service.base.OpePartsService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockBillService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockProdPartService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockPurchasService;
import com.redescooter.ses.mobile.rps.service.base.OpeStockService;
import com.redescooter.ses.mobile.rps.service.base.OpeWhseService;
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
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:PrepareMaterialServiceImpl
 * @description: PrepareMaterialServiceImpl
 * @author: Alex
 * @Version???1.3
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

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockService opeStockService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PrepareMaterialListResult> list(PageEnter enter) {
        //?????????????????????
//        int allocateListCount = prepareMaterialServiceMapper.allocatListCount(enter);
        int allocateListCount = opeAllocateService.count(new LambdaQueryWrapper<OpeAllocate>().eq(OpeAllocate::getStatus, AllocateOrderStatusEnums.PENDING.getValue()));
        //?????????
//        int assemblyListCount = prepareMaterialServiceMapper.assemblyListCount(enter);
        int assemblyListCount = opeAssemblyOrderService.count(new LambdaQueryWrapper<OpeAssemblyOrder>().eq(OpeAssemblyOrder::getStatus, AssemblyStatusEnums.PREPARE_MATERIAL.getValue()));

        if (allocateListCount + assemblyListCount == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        //???????????????
        List<PrepareMaterialListResult> allocatList = prepareMaterialServiceMapper.allocatList(enter);
        //???????????????
        List<PrepareMaterialListResult> assemblyList = prepareMaterialServiceMapper.assemblyList(enter);

        List<PrepareMaterialListResult> prepareMaterialList = new ArrayList(allocatList);
        prepareMaterialList.addAll(assemblyList);
        //???????????????
        return PageResult.create(enter, prepareMaterialList.size(), prepareMaterialList);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult confirmPreparation(ConfirmPreparationEnter enter) {
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ALLOCATE.getValue())) {
            //???????????????
            OpeAllocate opeAllocate = opeAllocateService.getById(enter.getId());
            if (opeAllocate == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.PENDING.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            //?????????????????????
            opeAllocate.setStatus(AllocateOrderStatusEnums.PREPARE.getValue());
            opeAllocate.setUpdatedBy(enter.getUserId());
            opeAllocate.setUpdatedTime(new Date());
            opeAllocateService.updateById(opeAllocate);
            //????????????
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opeAllocate.getId());
            saveNodeEnter.setStatus(AllocateOrderStatusEnums.PREPARE.getValue());
            saveNodeEnter.setEvent(AllocateOrderEventEnums.INPROGRESS.getValue());
            saveNodeEnter.setMemo(null);
            receiptTraceService.saveAllocateNode(saveNodeEnter);
        }
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ASSEMBLY.getValue())) {
            //????????????
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
            //????????????
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
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<PrepareMaterialDetailResult> detail(PrepareMaterialDetailEnter enter) {

        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ALLOCATE.getValue())) {
            //???????????????
            OpeAllocate opeAllocate = opeAllocateService.getById(enter.getId());
            if (opeAllocate == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.PREPARE.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }

            //?????????????????????
            int count = prepareMaterialServiceMapper.detailAllocateListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            List<PrepareMaterialDetailResult> resultList = prepareMaterialServiceMapper.detailAllocatelList(enter);

            //??????????????????????????????
            List<OpeStockPurchas> stockPurchasList =
                    opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>()
                            .in(OpeStockPurchas::getPartId, resultList.stream().map(PrepareMaterialDetailResult::getPartId).collect(Collectors.toList()))
                            .eq(OpeStockPurchas::getStatus, StockProductPartStatusEnums.AVAILABLE.getValue()));
            if (CollectionUtils.isEmpty(stockPurchasList)) {
                return PageResult.create(enter, count, resultList);
            }

            //?????? ?????????
            resultList.forEach(item -> {
                List<String> serialList = Lists.newArrayList();
                stockPurchasList.forEach(stock -> {
                    if (item.getPartId().equals(stock.getPartId())) {
                        serialList.add(stock.getSerialNumber());
                    }
                });
                item.setSerialNumList(serialList);
            });

            return PageResult.create(enter, count, resultList);
        }

        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ASSEMBLY.getValue())) {
            //????????????
            OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(enter.getId());
            if (opeAssemblyOrder == null) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
            }
            if (!StringUtils.equals(opeAssemblyOrder.getStatus(), AssemblyStatusEnums.PREPARE_MATERIAL.getValue())) {
                throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
            }
            //??????????????????
            int count = prepareMaterialServiceMapper.detailAssemblyListCount(enter);
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            }
            List<PrepareMaterialDetailResult> resultList = prepareMaterialServiceMapper.detailAssemblyList(enter);

            //???????????????
            List<OpeStockProdPart> opeStockProdPartList = opeStockProdPartService.list(new LambdaQueryWrapper<OpeStockProdPart>()
                    .in(OpeStockProdPart::getPartId, resultList.stream().map(PrepareMaterialDetailResult::getPartId).collect(Collectors.toList()))
                    .eq(OpeStockProdPart::getStatus, StockProductPartStatusEnums.AVAILABLE.getValue()));

            //?????? ?????????
            resultList.forEach(item -> {
                List<String> serialList = Lists.newArrayList();
                opeStockProdPartList.forEach(stock -> {
                    if (item.getPartId().equals(stock.getPartId())) {
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
     * ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)  
    @Override
    public GeneralResult save(SavePrepareMaterialEnter enter) {
        //??????????????????
        List<SavePrepareMaterialPartListEnter> savePrepareMaterialListEnterList = new ArrayList<>();
        Map<Long, List<SavePartBasicDateEnter>> savePartBasicDateMap = Maps.newHashMap();

        //???????????????????????????
        try {
            //??????????????????
            savePrepareMaterialListEnterList.addAll(JSON.parseArray(enter.getPartJson(), SavePrepareMaterialPartListEnter.class));
            if (CollectionUtils.isEmpty(savePrepareMaterialListEnterList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
            //??????????????????????????????
            savePrepareMaterialListEnterList.forEach(item -> {
                savePartBasicDateMap.put(item.getId(), JSON.parseArray(item.getPartListJson(), SavePartBasicDateEnter.class));
            });
            if (savePartBasicDateMap.isEmpty()) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
            }
        } catch (Exception e) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ILLEGAL_DATA.getCode(), ExceptionCodeEnums.ILLEGAL_DATA.getMessage());
        }
        //???????????????
        if (StringUtils.equals(enter.getSourceType(), SourceTypeEnums.ALLOCATE.getValue())) {
            AllocatePreparationEnter allocatePreparationEnter = new AllocatePreparationEnter();
            BeanUtils.copyProperties(enter, allocatePreparationEnter);
            allocatePreparationEnter.setId(enter.getId());
            allocatePreparationEnter.setSavePartBasicDateMap(savePartBasicDateMap);
            allocatePreparationEnter.setSavePrepareMaterialListEnterList(savePrepareMaterialListEnterList);
            allocatePreparation(allocatePreparationEnter);
        }
        //???????????????
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
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)  
    public GeneralResult allocatePreparation(AllocatePreparationEnter enter) {
        //???????????????????????????
        List<OpeAllocateBTrace> opeAllocateBTraceList = Lists.newArrayList();

        //?????????????????????
        List<Long> partIds = Lists.newArrayList();

        OpeAllocate opeAllocate = opeAllocateService.getById(enter.getId());
        if (opeAllocate == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.PREPARE.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }

        //?????????????????????
        List<OpeAllocateB> opeAllocateBList =
                new ArrayList<>(opeAllocateBService.listByIds(enter.getSavePrepareMaterialListEnterList().stream().map(SavePrepareMaterialPartListEnter::getId).collect(Collectors.toList())));
        if (CollectionUtils.isEmpty(opeAllocateBList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        if (opeAllocateBList.size() != enter.getSavePrepareMaterialListEnterList().size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());
        }
        //?????? ???????????????????????? ????????????
        opeAllocateBList.forEach(item -> {
            enter.getSavePartBasicDateMap().get(item.getId()).forEach(part -> {
                if (!item.getPartId().equals(part.getPartId())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_MATCH.getCode(), ExceptionCodeEnums.PART_IS_NOT_MATCH.getMessage());
                }
            });
        });
        partIds.addAll(opeAllocateBList.stream().map(OpeAllocateB::getPartId).collect(Collectors.toList()));


        //??????????????????
        List<OpeParts> opePartsList = new ArrayList(opePartsService.listByIds(partIds));
        if (CollectionUtils.isEmpty(opePartsList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //??????????????????????????????
        List<QueryStockBillDto> queryStockBillDtoList = prepareMaterialServiceMapper.queryStockBillBySourceTypeId(enter.getId(), SourceTypeEnums.ALLOCATE.getValue());


        //???Id ?????? ?????????
        List<String> serialNList = new ArrayList<>();
        //??????Id ??????????????????
        Map<Long, Integer> stockProdPartIdMap = Maps.newHashMap();

        //?????????Id ????????????????????????
        for (OpeAllocateB allocateB : opeAllocateBList) {
            for (OpeParts part : opePartsList) {
                for (SavePartBasicDateEnter serialN : enter.getSavePartBasicDateMap().get(allocateB.getId())) {//??????part ???Id ??????????????? ??????Id ????????????
                    if (part.getId().equals(serialN.getPartId())) {
                        //???Id??????
                        if (part.getIdClass()) {
                            serialNList.add(serialN.getSerialN());
                            continue;
                        } else {
                            //???ID ??????
                            if (stockProdPartIdMap.containsKey(serialN.getPartId())) {
                                stockProdPartIdMap.put(serialN.getPartId(), stockProdPartIdMap.get(serialN.getPartId()) + serialN.getQty());
                                continue;
                            } else {
                                stockProdPartIdMap.put(serialN.getPartId(), serialN.getQty());
                            }
                        }
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(serialNList)) {
            //???Id ??????
            List<OpeStockPurchas> opeStockProdPartListIdClassTrue = opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>().in(OpeStockPurchas::getSerialNumber, serialNList));
            if (CollectionUtils.isEmpty(opeStockProdPartListIdClassTrue)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
            for (OpeStockPurchas item : opeStockProdPartListIdClassTrue) {
                if (!StringUtils.equals(item.getStatus(), StockProductPartStatusEnums.AVAILABLE.getValue())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getCode(), ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getMessage());
                }

                //????????????
                for (QueryStockBillDto stockStill : queryStockBillDtoList) {
                    if (stockStill.getPartId().equals(item.getPartId())) {
                        item.setOutStockBillId(stockStill.getStockBillId());
                        item.setOutPrincipalId(enter.getUserId());
                        item.setOutStockTime(new Date());
                        item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                        item.setAvailableQty(0);
                        item.setUpdatedBy(enter.getUserId());
                        item.setUpdatedTime(new Date());
                        break;
                    }
                }
            }
            opeStockPurchasService.updateBatch(opeStockProdPartListIdClassTrue);
        }

        if (!stockProdPartIdMap.isEmpty()) {

            //???Id??????
            List<OpeStockPurchas> opeStockProdPartListIdClassFalse =
                    opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>()
                            .in(OpeStockPurchas::getPartId, stockProdPartIdMap.keySet())
                            .eq(OpeStockPurchas::getStatus, StockProductPartStatusEnums.AVAILABLE.getValue())
                            .orderByAsc(OpeStockPurchas::getCreatedTime));
            if (CollectionUtils.isEmpty(opeStockProdPartListIdClassFalse)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }

            //?????? ????????????????????????
            opeStockProdPartListIdClassFalse.forEach(item -> {
                if (!StringUtils.equals(item.getStatus(), StockProductPartStatusEnums.AVAILABLE.getValue())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getCode(), ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getMessage());
                }
                //????????????
                int partQty = stockProdPartIdMap.get(item.getPartId());

                for (QueryStockBillDto stockStill : queryStockBillDtoList) {
                    if (stockStill.getPartId().equals(item.getPartId())) {
                        if (partQty == 0) {
                            break;
                        }

                        if (partQty >= item.getInWhQty()) {
                            partQty -= item.getInWhQty();
                            item.setAvailableQty(0);
                            item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                            item.setOutStockBillId(stockStill.getStockBillId());
                            item.setOutPrincipalId(enter.getUserId());
                            item.setOutStockTime(new Date());
                            item.setUpdatedBy(enter.getUserId());
                            item.setUpdatedTime(new Date());
                            if (partQty == 0) {
                                break;
                            }
                        }

                        if (partQty < item.getInWhQty()) {
                            partQty = 0;
                            item.setAvailableQty(item.getInWhQty() - partQty);
                            item.setUpdatedBy(enter.getUserId());
                            item.setUpdatedTime(new Date());
                            if (partQty == 0) {
                                break;
                            }
                        }
                    }
                }
            });
            opeStockPurchasService.updateBatch(opeStockProdPartListIdClassFalse);
        }


        //todo ??????????????? ?????????
        String batchNo = ProductContractEnums.REDE.getCode() + RandomUtil.randomNumbers(8);

        //????????????
        int totalPart = 0;
        //??????????????????
        for (OpeAllocateB opeAllocateB : opeAllocateBList) {
            int partQty = 0;

            //????????????????????????
            for (SavePartBasicDateEnter partMap : enter.getSavePartBasicDateMap().get(opeAllocateB.getId())) {
                if (opeAllocateB.getPartId().equals(partMap.getPartId())) {
                    //??????????????????
                    opeAllocateBTraceList.add(buildOpeAllocateBTrace(enter.getUserId(), batchNo, opeAllocateB, partMap));
                }
                partQty += partMap.getQty();
                totalPart += partMap.getQty();
            }

            //??????????????????
            if (!opeAllocateB.getPreparationWaitQty().equals(partQty)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getMessage());
            }
            //?????????????????????
            opeAllocateB.setPreparationWaitQty(opeAllocateB.getPreparationWaitQty() - partQty);
            opeAllocateB.setUpdatedBy(enter.getUserId());
            opeAllocateB.setUpdatedTime(new Date());
        }
        //????????????????????????????????????
        Boolean updateOpeAllocateStatus = Boolean.TRUE;
        for (OpeAllocateB item : opeAllocateBList) {
            if (item.getPreparationWaitQty() != 0) {
                updateOpeAllocateStatus = Boolean.FALSE;
            }
        }

        if (updateOpeAllocateStatus) {
            opeAllocate.setPreparationWaitTotal(0);
            opeAllocate.setStatus(AllocateOrderStatusEnums.ALLOCATE.getValue());

            //????????????
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
        //??????????????????
        opeAllocateService.updateById(opeAllocate);
        //??????????????????
        opeAllocateBService.updateBatch(opeAllocateBList);
        //??????????????????
        opeAllocateBTraceService.saveBatch(opeAllocateBTraceList);

        //????????? ??????
        toBeStoredFillingPoint(opeAllocateBList);
        return new GeneralResult(enter.getRequestId());
    }


    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)  
    public GeneralResult assemblyPreparation(AssemblyPreparationEnter enter) {
        //?????????????????????
        List<OpeAssemblyPreparation> saveAssemblyPreparationList = Lists.newArrayList();

        //?????????????????????
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

        opeAssemblyOrderPartList.forEach(item -> {
            enter.getSavePartBasicDateMap().get(item.getId()).forEach(part -> {
                if (!item.getPartId().equals(part.getPartId())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_MATCH.getCode(), ExceptionCodeEnums.PART_IS_NOT_MATCH.getMessage());
                }
            });
        });

        partIds.addAll(opeAssemblyOrderPartList.stream().map(OpeAssemblyOrderPart::getPartId).collect(Collectors.toList()));

        //??????????????????
        List<OpeParts> opePartsList = new ArrayList(opePartsService.listByIds(partIds));
        if (CollectionUtils.isEmpty(opePartsList)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //??????????????????????????????
        List<QueryStockBillDto> queryStockBillDtoList = prepareMaterialServiceMapper.queryStockBillBySourceTypeId(enter.getId(), SourceTypeEnums.ASSEMBLY.getValue());


        //???Id ?????? ?????????
        List<String> serialNList = new ArrayList<>();
        //??????Id ??????????????????
        Map<Long, Integer> stockProdPartIdMap = Maps.newHashMap();

        //?????????Id ????????????????????????
        opeAssemblyOrderPartList.forEach(item -> {
            opePartsList.forEach(part -> {
                enter.getSavePartBasicDateMap().get(item.getId()).forEach(serialN -> {
                    //??????part ???Id ??????????????? ??????Id ????????????
                    if (part.getId().equals(serialN.getPartId())) {
                        if (part.getIdClass()) {
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
            //???Id ??????
            List<OpeStockProdPart> opeStockProdPartListIdClassTrue = opeStockProdPartService.list(new LambdaQueryWrapper<OpeStockProdPart>().in(OpeStockProdPart::getSerialNumber, serialNList));
            if (CollectionUtils.isEmpty(opeStockProdPartListIdClassTrue)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }
            for (OpeStockProdPart item : opeStockProdPartListIdClassTrue) {
                if (!StringUtils.equals(item.getStatus(), StockProductPartStatusEnums.AVAILABLE.getValue())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getCode(), ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getMessage());
                }

                //????????????
                for (QueryStockBillDto stockStill : queryStockBillDtoList) {
                    if (stockStill.getPartId().equals(item.getPartId())) {
                        item.setOutStockBillId(stockStill.getStockBillId());
                        item.setOutPrincipalId(enter.getUserId());
                        item.setAvailableQty(0);
                        item.setOutStockTime(new Date());
                        item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                        item.setUpdatedBy(enter.getUserId());
                        item.setUpdatedTime(new Date());
                        break;
                    }
                }
            }
            opeStockProdPartService.updateBatch(opeStockProdPartListIdClassTrue);
        }

        if (!stockProdPartIdMap.isEmpty()) {
            //???Id??????
            List<OpeStockProdPart> opeStockProdPartListIdClassFalse =
                    opeStockProdPartService.list(new LambdaQueryWrapper<OpeStockProdPart>()
                            .in(OpeStockProdPart::getPartId, stockProdPartIdMap.keySet())
                            .eq(OpeStockProdPart::getStatus, StockProductPartStatusEnums.AVAILABLE.getValue())
                            .orderByAsc(OpeStockProdPart::getCreatedTime));
            if (CollectionUtils.isEmpty(opeStockProdPartListIdClassFalse)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
            }

            //?????? ????????????????????????
            opeStockProdPartListIdClassFalse.forEach(item -> {
                if (!StringUtils.equals(item.getStatus(), StockProductPartStatusEnums.AVAILABLE.getValue())) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getCode(), ExceptionCodeEnums.PART_IS_ALREADY_DAMAGE.getMessage());
                }
                //????????????
                int partQty = stockProdPartIdMap.get(item.getPartId());

                for (QueryStockBillDto stockStill : queryStockBillDtoList) {
                    if (stockStill.getPartId().equals(item.getPartId())) {

                        if (partQty == 0) {
                            break;
                        }

                        if (partQty >= item.getInWhQty()) {
                            partQty -= item.getInWhQty();
                            item.setAvailableQty(0);
                            item.setStatus(StockProductPartStatusEnums.OUT_WH.getValue());
                            item.setOutStockBillId(stockStill.getStockBillId());
                            item.setOutPrincipalId(enter.getUserId());
                            item.setOutStockTime(new Date());
                            item.setUpdatedBy(enter.getUserId());
                            item.setUpdatedTime(new Date());
                            if (partQty == 0) {
                                break;
                            }
                        }

                        if (partQty < item.getInWhQty()) {
                            partQty = 0;
                            item.setAvailableQty(item.getInWhQty() - partQty);
                            item.setUpdatedBy(enter.getUserId());
                            item.setUpdatedTime(new Date());
                            if (partQty == 0) {
                                break;
                            }
                        }
                    }
                }
            });
            opeStockProdPartService.updateBatch(opeStockProdPartListIdClassFalse);
        }


        //todo ??????????????? ?????????
        String batchNo = ProductContractEnums.REDE.getCode() + RandomUtil.randomNumbers(8);

        //????????????
        int totalPart = 0;

        for (OpeAssemblyOrderPart item : opeAssemblyOrderPartList) {
            int partQty = 0;
            //????????????????????????
            for (SavePartBasicDateEnter value : enter.getSavePartBasicDateMap().get(item.getId())) {
                //???????????????????????????
                saveAssemblyPreparationList.add(buildOpeAssemblyPreparation(enter.getUserId(), batchNo, item, value));
                //??????????????????
                partQty += value.getQty();
                totalPart += value.getQty();
            }

            //??????????????????
            if (item.getWaitPreparationQty() != partQty) {
                throw new SesMobileRpsException(ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getCode(), ExceptionCodeEnums.PREPARE_MATERIAL_QTY_IS_WRONG.getMessage());
            }

            //?????????????????????
            item.setWaitPreparationQty(item.getWaitPreparationQty() - partQty);
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        }

        //????????????????????????????????????
        Boolean updateOpeAssemblyOrderPartStatus = Boolean.TRUE;
        for (OpeAssemblyOrderPart item : opeAssemblyOrderPartList) {
            if (item.getWaitPreparationQty() != 0) {
                updateOpeAssemblyOrderPartStatus = Boolean.FALSE;
            }
        }

        if (updateOpeAssemblyOrderPartStatus) {
            opeAssemblyOrder.setWaitPreparationTotal(0);
            opeAssemblyOrder.setStatus(AssemblyStatusEnums.ASSEMBLING.getValue());

            //????????????
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
        //??????????????????
        opeAssemblyOrderService.updateById(opeAssemblyOrder);
        //?????????????????????
        opeAssemblyOrderPartService.updateBatch(opeAssemblyOrderPartList);
        // ??????????????????
        opeAssemblyPreparationService.saveBatch(saveAssemblyPreparationList);

        return new GeneralResult(enter.getRequestId());
    }

    /**
     * buildOpeAllocateBTrace ????????????????????????
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
     * buildOpeAssemblyPreparation ??????
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

    /**
     * ???????????????
     *
     * @param opeAllocateBList
     */
    private void toBeStoredFillingPoint(List<OpeAllocateB> opeAllocateBList) {
        //????????????
        OpeWhse whse = opeWhseService.getOne(new LambdaQueryWrapper<OpeWhse>().eq(OpeWhse::getType, WhseTypeEnums.ALLOCATE.getValue()));
        if (whse == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        List<Long> partIds = opeAllocateBList.stream().map(OpeAllocateB::getPartId).collect(Collectors.toList());

        List<OpeParts> partsList = opePartsService.list(new LambdaQueryWrapper<OpeParts>().in(OpeParts::getId, partIds));
        if (CollectionUtils.isEmpty(partsList) || partsList.size() != opeAllocateBList.size()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //???????????????????????????
        List<OpeStock> opeStockList = opeStockService.list(new LambdaQueryWrapper<OpeStock>()
                .eq(OpeStock::getWhseId, whse.getId())
                .in(OpeStock::getMaterielProductId, partIds));

        List<OpeStock> saveOpeStockList = new ArrayList<>();

        if (CollectionUtils.isEmpty(opeStockList)) {
            for (OpeAllocateB item : opeAllocateBList) {
                OpeParts parts = partsList.stream().filter(part -> item.getPartId().equals(part.getId())).findFirst().orElse(null);
                //????????????
                saveOpeStockList.add(buildStock(whse, parts, item));
            }
        } else {

            for (OpeAllocateB item : opeAllocateBList) {
                OpeStock opeStock = opeStockList.stream().filter(stock -> stock.getMaterielProductId().equals(item.getPartId())).findFirst().orElse(null);
                if (opeStock != null) {
                    //????????????
                    opeStock.setWaitStoredTotal(item.getTotal() + opeStock.getWaitStoredTotal());
                    opeStock.setUpdatedTime(new Date());
                    saveOpeStockList.add(opeStock);
                } else {
                    OpeParts parts = partsList.stream().filter(part -> item.getPartId().equals(part.getId())).findFirst().orElse(null);
                    //????????????
                    saveOpeStockList.add(buildStock(whse, parts, item));
                }
            }
        }

        //????????????
        if (CollectionUtils.isNotEmpty(saveOpeStockList)) {
            opeStockService.saveOrUpdateBatch(saveOpeStockList);
        }
    }

    /**
     * ?????? stock ??????
     *
     * @param whse
     * @param parts
     * @param item
     * @return
     */
    private OpeStock buildStock(OpeWhse whse, OpeParts parts, OpeAllocateB item) {
        OpeStock opeStock = OpeStock.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK))
                .dr(0)
                .userId(0L)
                .tenantId(0L)
                .whseId(whse.getId())
                .intTotal(0)
                .availableTotal(0)
                .outTotal(0)
                .wornTotal(0)
                .lockTotal(0)
                .waitProductTotal(0)
                .waitStoredTotal(item.getTotal())
                .materielProductId(item.getPartId())
                .materielProductName(parts.getCnName())
                .materielProductType(BomCommonTypeEnums.getValueByCode(parts.getPartsType()))
                .revision(0)
                .updatedBy(0L)
                .updatedTime(new Date())
                .createdBy(0L)
                .createdTime(new Date())
                .build();
        return opeStock;
    }
}
