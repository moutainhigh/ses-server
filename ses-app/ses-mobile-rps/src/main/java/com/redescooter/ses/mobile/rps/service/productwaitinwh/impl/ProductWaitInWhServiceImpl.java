package com.redescooter.ses.mobile.rps.service.productwaitinwh.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderEventEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyEventEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.productwaitinwh.ProductWaitInWhServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.productwaitinwh.ProductWaitInWhService;
import com.redescooter.ses.mobile.rps.vo.productwaitinwh.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassNameProWaitInWHServiceImpl
 * @Description
 * @Author kyle
 * @Date2020/4/14 17:52
 * @Version V1.0
 **/
@Service
public class ProductWaitInWhServiceImpl implements ProductWaitInWhService {

    @Autowired
    private ProductWaitInWhServiceMapper productWaitInWhServiceMapper;

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAssemblyBOrderService opeAssemblyBOrderService;

    @Autowired
    private OpeAssemblyQcItemService opeAssemblyQcItemService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private ReceiptTraceService receiptTraceService;

    @Autowired
    private OpeStockProdProductService opeStockProdProductService;

    @Autowired
    private OpeAllocateService opeAllocateService;

    @Autowired
    private OpeAllocateBService opeAllocateBService;

    @Autowired
    private OpePartsService opePartsService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpeAllocateBTraceService opeAllocateBTraceService;

    @Autowired
    private OpeStockProdPartService opeStockProdPartService;

    @Autowired
    private OpeProductAssemblyService opeProductAssemblyService;

    @Autowired
    private OpeStockPurchasService opeStockPurchasService;

    @DubboReference
    private IdAppService idAppService;

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1????????????????????????????????????
     * @Date 2020/4/14 17:51
     * @Param [enter]
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public PageResult<AllocateAndProductResult> productWaitInWhList(PageEnter enter) {
        int count = productWaitInWhServiceMapper.proWaitInWHListCount();
        //?????????????????????????????????????????????
        List<AllocateAndProductResult> allocateAndProductResultList = new ArrayList<>();

        if (count > 0) {
            QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
            //?????????????????????0
            opeAssemblyOrderQueryWrapper.gt(OpeAssemblyOrder.COL_IN_WAIT_WH_TOTAL, 0);
            opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_STATUS, AssemblyStatusEnums.QC_PASSED.getValue());
            List<OpeAssemblyOrder> opeAssemblyOrderList = opeAssemblyOrderService.list(opeAssemblyOrderQueryWrapper);
            //???????????????

            AllocateAndProductResult allocateAndProductResult = null;
            if (!CollectionUtils.isEmpty(opeAssemblyOrderList)) {
                for (OpeAssemblyOrder opeAssemblyOrder : opeAssemblyOrderList) {
                    allocateAndProductResultList.add(
                            allocateAndProductResult = AllocateAndProductResult.builder()
                                    .id(opeAssemblyOrder.getId())
                                    .waitInWHNum(opeAssemblyOrder.getInWaitWhTotal())
                                    .waitInWHStr(opeAssemblyOrder.getAssemblyNumber())
                                    .inWHTListTime(opeAssemblyOrder.getCreatedTime())
                                    .sourceType(SourceTypeEnums.ASSEMBLY.getValue())//????????????
                                    .build());
                }
            }
        }
        //??????????????????????????????
        allocateAndProductResultList = this.allocateWaitInWHList(enter, allocateAndProductResultList);
        //??????????????????
        if (CollectionUtils.isEmpty(allocateAndProductResultList)) {
            return PageResult.createZeroRowResult(enter);
        }

        //??????????????????????????????????????????
        return PageResult.create(enter, allocateAndProductResultList.size(), allocateAndProductResultList);
    }

    /**
     * @return
     * @Author kyle
     * @Description //???????????????
     * @Date 2020/4/26 13:41
     * @Param [enter]
     **/
    @GlobalTransactional(rollbackFor = Exception.class)
    public List<AllocateAndProductResult> allocateWaitInWHList(PageEnter enter, List<AllocateAndProductResult> allocateAndProductResultList) {
        QueryWrapper<OpeAllocate> opeAllocateQueryWrapper = new QueryWrapper<>();
        opeAllocateQueryWrapper.eq(OpeAllocate.COL_STATUS, AllocateOrderStatusEnums.ALLOCATE.getValue());
        opeAllocateQueryWrapper.gt(OpeAllocate.COL_PENDING_STORAGE_TOTAL, 0);
        List<OpeAllocate> opeAllocateList = opeAllocateService.list(opeAllocateQueryWrapper);
        //???????????????
        if (CollectionUtils.isEmpty(opeAllocateList)) {
            return allocateAndProductResultList;
        }

        if (opeAllocateList.size() == 0) {
            return allocateAndProductResultList;
        } else {
            for (OpeAllocate opeAllocate : opeAllocateList) {
                AllocateAndProductResult allocateAndProductResult = null;
                allocateAndProductResultList.add(
                        allocateAndProductResult = AllocateAndProductResult.builder()
                                .id(opeAllocate.getId())
                                .waitInWHNum(opeAllocate.getPendingStorageTotal())
                                .waitInWHStr(opeAllocate.getAllocateNum())
                                .inWHTListTime(new Date())
                                .sourceType(SourceTypeEnums.ALLOCATE.getValue())//????????????
                                .build());
            }
        }
        return allocateAndProductResultList;
    }


    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1???????????????id????????????????????????????????????????????????
     * @Date 2020/4/14 17:49
     * @Param [enter]
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public PageResult<ProductWaitInWhItemResult> productWaitWhItemList(ProductDetailEnter enter) {

        //????????????????????????
        if (enter.getSourceType().equals(SourceTypeEnums.ASSEMBLY.getValue())) {
            return assemblyDetaiList(enter);
        }
        if (enter.getSourceType().equals(SourceTypeEnums.ALLOCATE.getValue())) {
            return allocateDetaiList(enter);
        }
        return null;
    }

    /**
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<ProductWaitInWhItemResult> allocateDetaiList(ProductDetailEnter enter) {
        OpeAllocate opeAllocate = opeAllocateService.getById(enter.getId());
        if (opeAllocate == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }
        if (!org.apache.commons.lang3.StringUtils.equals(opeAllocate.getStatus(), AllocateOrderStatusEnums.ALLOCATE.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        int count = opeAllocateBService.count(new LambdaQueryWrapper<OpeAllocateB>().eq(OpeAllocateB::getAllocateId, enter.getId()).gt(OpeAllocateB::getPendingStorageQty, 0));
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<ProductWaitInWhItemResult> result = productWaitInWhServiceMapper.allocateDetaiList(enter);

//        //??????????????????????????????
//        List<OpeStockPurchas> opeStockPurchasList =
//                opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>()
//                        .in(OpeStockPurchas::getPartId, result.stream().map(ProductWaitInWhItemResult::getProductId).collect(Collectors.toList()))
//                        .eq(OpeStockPurchas::getStatus, StockProductPartStatusEnums.OUT_WH.getValue()));
//        if (CollectionUtils.isEmpty(opeStockPurchasList)) {
//            return PageResult.create(enter, count, result);
//        }

//        //????????????????????????
//        result.forEach(item -> {
//            List<String> serialList = Lists.newArrayList();
//            opeStockPurchasList.forEach(stock -> {
//                if (item.getProductId().equals(stock.getPartId())) {
//                    serialList.add(stock.getSerialNumber());
//                }
//            });
//            item.setSerialNumList(serialList);
//        });

        return PageResult.create(enter, count, result);
    }


    /**
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<ProductWaitInWhItemResult> assemblyDetaiList(ProductDetailEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(enter.getId());
        if (opeAssemblyOrder == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }
        if (!org.apache.commons.lang3.StringUtils.equals(opeAssemblyOrder.getStatus(), AssemblyStatusEnums.QC_PASSED.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        int count = opeAssemblyBOrderService.count(new LambdaQueryWrapper<OpeAssemblyBOrder>().eq(OpeAssemblyBOrder::getAssemblyId, enter.getId()));
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<ProductWaitInWhItemResult> result = productWaitInWhServiceMapper.assemblyDetaiList(enter);

        //??????????????????????????????
        List<OpeProductAssembly> opeProductAssemblyList = opeProductAssemblyService.list(new LambdaQueryWrapper<OpeProductAssembly>()
                .in(OpeProductAssembly::getProductId,
                        result.stream().map(ProductWaitInWhItemResult::getProductId).collect(Collectors.toList()))
                .eq(OpeProductAssembly::getInwhStatus, Boolean.FALSE));
        if (CollectionUtils.isEmpty(opeProductAssemblyList)) {
            return PageResult.create(enter, count, result);
        }

        //????????????????????????
        result.forEach(item -> {
            List<String> serialList = Lists.newArrayList();
            opeProductAssemblyList.forEach(stock -> {
                if (item.getProductId().equals(stock.getProductId())) {
                    serialList.add(stock.getProductSerialNum());
                }
            });
            item.setSerialNumList(serialList);
        });

        return PageResult.create(enter, count, result);
    }


    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1?????????????????????????????????
     * @Date 2020/4/14 17:52
     * @Param [enter]
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public ProductWaitInWhInfoResult setProductWaitInWhInfo(ProductWaitInWhIdItemEnter enter) {

        //??????????????????????????????????????????????????????
        if (!enter.getInWhNum().equals(enter.getShouldInWhNum())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
        }

        //???????????????
        if (enter.getSourceType().equals(SourceTypeEnums.ASSEMBLY.getValue())) {
            return this.getProductWaitInWhInfoResultFromAssembly(enter);
        }

        //???????????????
        if (enter.getSourceType().equals(SourceTypeEnums.ALLOCATE.getValue())) {
            return this.getProductWaitInWhInfoResultFromAllocate(enter);
        }

        return null;

    }

    /**
     * @return
     * @Author kyle
     * @Description //??????????????????????????????->?????????
     * @Date 2020/4/26 19:45
     * @Param [enter]
     **/
    private ProductWaitInWhInfoResult getProductWaitInWhInfoResultFromAllocate(ProductWaitInWhIdItemEnter enter) {

        //?????????????????????
        QueryWrapper<OpeAllocateB> opeAllocateBQueryWrapper = new QueryWrapper<>();
        opeAllocateBQueryWrapper.eq(OpeAllocateB.COL_ID, enter.getId());
        OpeAllocateB opeAllocateB = opeAllocateBService.getOne(opeAllocateBQueryWrapper);

        //?????????????????????
        if (StringUtils.isEmpty(opeAllocateB)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_B_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_B_ORDER_IS_NOT_EXIST.getMessage());
        }

        //???????????????
        QueryWrapper<OpeAllocate> opeAllocateQueryWrapper = new QueryWrapper<>();
        opeAllocateQueryWrapper.eq(OpeAllocate.COL_ID, opeAllocateB.getAllocateId());
        OpeAllocate opeAllocate = opeAllocateService.getOne(opeAllocateQueryWrapper);

        //???????????????
        if (StringUtils.isEmpty(opeAllocate)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }

        //?????????????????????????????????
        QueryWrapper<OpeAllocateBTrace> opeAllocateBTraceQueryWrapper = new QueryWrapper<>();
        opeAllocateBTraceQueryWrapper.eq(OpeAllocateBTrace.COL_ALLOCATE_B_ID, opeAllocateB.getId());
        if (!StringUtils.isEmpty(enter.getProductSerialNum())) {
            opeAllocateBTraceQueryWrapper.eq(OpeAllocateBTrace.COL_SERIAL_NUM, enter.getProductSerialNum());
        }
        OpeAllocateBTrace opeAllocateBTrace = opeAllocateBTraceService.getOne(opeAllocateBTraceQueryWrapper);

        //????????????????????????
        if (StringUtils.isEmpty(opeAllocateBTrace)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_TRACE_IS_EMPTY.getCode(), ExceptionCodeEnums.ALLOCATE_TRACE_IS_EMPTY.getMessage());
        }

        //?????????????????????????????????????????????
        QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.eq(OpeParts.COL_ID, opeAllocateB.getPartId());
        OpeParts opeParts = opePartsService.getOne(opePartsQueryWrapper);


        //???????????????
        if (StringUtils.isEmpty(opeParts)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //IdClass???????????????????????????
        OpeStockPurchas opeStockPurchas = null;
        if (opeParts.getIdClass()) {
            //???????????????????????????
            QueryWrapper<OpeStockPurchas> opeStockPurchasQueryWrapper = new QueryWrapper<>();
            opeStockPurchasQueryWrapper.eq(OpeStockPurchas.COL_SERIAL_NUMBER, enter.getProductSerialNum());
            opeStockPurchas = opeStockPurchasService.getOne(opeStockPurchasQueryWrapper);
            //??????????????????
            if (StringUtils.isEmpty(opeStockPurchas)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getCode(), ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getMessage());
            }
        }

        //??????????????????
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);

        //???????????????
        if (StringUtils.isEmpty(opeWhse)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WH_IS_EMPTY.getCode(), ExceptionCodeEnums.WH_IS_EMPTY.getMessage());
        }

        //????????????id???????????????
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opeAllocateB.getPartId());
        OpeStock opeStock = opeStockService.getOne(opeStockQueryWrapper);

        //????????????????????????????????????????????????????????????????????????
        if (opeStock != null) {
            //????????????+
            opeStock.setIntTotal(opeParts.getIdClass() ? (opeStock.getIntTotal() + 1) : (opeStock.getIntTotal() + enter.getInWhNum()));
            //???????????????+
            opeStock.setAvailableTotal(opeParts.getIdClass() ? (opeStock.getAvailableTotal() + 1) : (opeStock.getAvailableTotal() + enter.getInWhNum()));
        } else {
            //????????????
            opeStock = OpeStock.builder()
                    .id(idAppService.getId(SequenceName.OPE_STOCK))
                    .dr(0)
                    .intTotal(opeParts.getIdClass() ? 1 : enter.getInWhNum())
                    .outTotal(0)
                    .wornTotal(0)
                    .availableTotal(opeParts.getIdClass() ? 1 : enter.getInWhNum())
                    .lockTotal(0)
                    .whseId(opeWhse.getId())
                    .revision(0)
                    .userId(enter.getUserId())
                    .tenantId(enter.getTenantId())
                    .materielProductName(opeParts.getCnName())
                    .materielProductId(opeAllocateB.getPartId())
                    .materielProductType(BomCommonTypeEnums.getValueByCode(opeParts.getPartsType()))//??????????????????
                    .waitStoredTotal(0)
                    .waitProductTotal(0)
                    .updatedTime(new Date())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .createdBy(enter.getUserId())
                    .build();
        }

        //??????????????????????????????????????????????????????????????????
        if ((!StringUtils.isEmpty(opeAllocate.getPendingStorageTotal())) && (!StringUtils.isEmpty(opeAllocateB.getPendingStorageQty()))) {
            //?????????????????????????????????
            opeAllocate.setPendingStorageTotal(opeParts.getIdClass() ? (opeAllocate.getPendingStorageTotal() - 1) : (opeAllocate.getPendingStorageTotal() - enter.getInWhNum()));
            //???????????????????????????????????????
            opeAllocateB.setPendingStorageQty(opeParts.getIdClass() ? (opeAllocateB.getPendingStorageQty() - 1) : (opeAllocateB.getPendingStorageQty() - enter.getInWhNum()));
            //?????????????????????
            if (opeAllocate.getPendingStorageTotal() < 0 || opeAllocateB.getPendingStorageQty() < 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
            }
            //?????????????????????
            if (opeAllocate.getPendingStorageTotal() == 0) {
                opeAllocate.setStatus(AllocateOrderStatusEnums.INPRODUCTIONWH.getValue());
                //?????????????????????????????????
                SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
                BeanUtils.copyProperties(enter, saveNodeEnter);
                saveNodeEnter.setId(opeAllocate.getId());
                saveNodeEnter.setStatus(AllocateOrderStatusEnums.INPRODUCTIONWH.getValue());
                saveNodeEnter.setEvent(AllocateOrderEventEnums.INPRODUCTIONWH.getValue());
                saveNodeEnter.setMemo(null);
                receiptTraceService.saveAllocateNode(saveNodeEnter);
            }
        } else {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
        }

        //??????????????????????????????
        OpeStockBill opeStockBill = OpeStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .stockId(opeStock.getId())
                .direction(InOutWhEnums.IN.getValue())
                .status(StockBillStatusEnums.NORMAL.getValue())
                .total(opeParts.getIdClass() ? 1 : enter.getInWhNum())
                .userId(enter.getUserId())
                .tenantId(enter.getTenantId())
                .sourceId(opeAllocate.getId())
                .principalId(enter.getUserId())
                .sourceType(WhseTypeEnums.ALLOCATE.getValue())
                .operatineTime(new Date())
                .revision(0)
                .createdTime(new Date())
                .updatedTime(new Date())
                .operatineTime(new Date())
                .createdBy(enter.getUserId())
                .updatedBy(enter.getUserId())
                .build();

        //??????????????????????????????
        OpeStockProdPart opeStockProdPart = OpeStockProdPart.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_PROD_PART))
                .dr(0)
                .userId(enter.getUserId())
                .tenantId(enter.getTenantId())
                .status(StockProductPartStatusEnums.AVAILABLE.getValue())
                .stockId(opeStock.getId())
                .partId(opeParts.getId())
                .lot(opeAllocateBTrace.getBatchNo())
                .serialNumber(opeParts.getIdClass() ? opeStockPurchas.getSerialNumber() : null) //?????????
                .partsNumber(opeParts.getPartsNumber())
                .inStockBillId(opeStockBill.getId())
                .availableQty(opeParts.getIdClass() ? 1 : enter.getInWhNum())
                .outStockBillId(0L)
                .outStockTime(null)
                .outPrincipalId(0L)
                .principalId(enter.getUserId())
                .inStockTime(new Date())
                .revision(0)
                .inWhQty(opeParts.getIdClass() ? 1 : enter.getInWhNum())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();

        //?????????????????????
        opeAllocateService.updateById(opeAllocate);
        //????????????????????????
        opeAllocateBService.updateById(opeAllocateB);
        //??????????????????????????????
        opeStockBillService.save(opeStockBill);
        //??????????????????????????????
        opeStockProdPartService.save(opeStockProdPart);

        opeStock.setWaitStoredTotal(opeStock.getWaitStoredTotal() - enter.getInWhNum());
        opeStockService.saveOrUpdate(opeStock);

        //?????????????????????
        return ProductWaitInWhInfoResult.builder()
                .residueNum(opeAllocateB.getPendingStorageQty())
                .partNum(opeParts.getPartsNumber())
                .partName(opeParts.getCnName())
                .batchNum(opeAllocateBTrace.getBatchNo())  //?????????
                .serialNum(opeParts.getIdClass() ? opeAllocateBTrace.getSerialNum() : null)  //?????????
                .build();
    }

    /**
     * @return
     * @Author kyle
     * @Description //??????????????????????????????->?????????
     * @Date 2020/4/26 19:33
     * @Param [enter]
     **/
    private ProductWaitInWhInfoResult getProductWaitInWhInfoResultFromAssembly(ProductWaitInWhIdItemEnter enter) {

        //???????????????????????????
        QueryWrapper<OpeProductAssembly> opeProductAssemblyQueryWrapper = new QueryWrapper<>();
        opeProductAssemblyQueryWrapper.eq(OpeProductAssembly.COL_PRODUCT_SERIAL_NUM, enter.getProductSerialNum());
        OpeProductAssembly opeProductAssembly = opeProductAssemblyService.getOne(opeProductAssemblyQueryWrapper);

        if (StringUtils.isEmpty(opeProductAssembly)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getCode(), ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getMessage());
        }

        //???????????????????????????????????????
        QueryWrapper<OpeAssemblyQcItem> opeAssemblyQcItemQueryWrapper = new QueryWrapper<>();
        opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_QC_RESULT, QcStatusEnums.PASS.getValue());
        opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_SERIAL_NUM, enter.getProductSerialNum());
        OpeAssemblyQcItem opeAssemblyQcItem = opeAssemblyQcItemService.getOne(opeAssemblyQcItemQueryWrapper);
        if (opeAssemblyQcItem == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_INFO_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_INFO_IS_EMPTY.getMessage());
        }

        //?????????????????????
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
        opeAssemblyBOrderQueryWrapper.gt(OpeAssemblyBOrder.COL_IN_WAIT_WH_QTY, 0);
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);

        //???????????????????????????
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        //?????????????????????????????????0????????????
        if (StringUtils.isEmpty(opeAssemblyBOrder.getInWaitWhQty())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_IS_EMPTY.getMessage());
        }

        //???????????????
        QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, opeAssemblyBOrder.getAssemblyId());
        opeAssemblyOrderQueryWrapper.gt(OpeAssemblyOrder.COL_IN_WAIT_WH_TOTAL, 0);//??????????????????????????????
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);

        //??????????????????
        if (StringUtils.isEmpty(opeAssemblyOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }

        //??????????????????
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ASSEMBLY.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);

        //???????????????
        if (StringUtils.isEmpty(opeWhse)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WH_IS_EMPTY.getCode(), ExceptionCodeEnums.WH_IS_EMPTY.getMessage());
        }

        //????????????????????????????????????????????????
        QueryWrapper<OpePartsProduct> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.eq(OpeParts.COL_ID, opeAssemblyBOrder.getProductId());
        OpePartsProduct opePartsProduct = opePartsProductService.getOne(opePartsQueryWrapper);

        //???????????????
        if (StringUtils.isEmpty(opePartsProduct)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //????????????id???????????????
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opeAssemblyBOrder.getProductId());
        OpeStock opeStock = opeStockService.getOne(opeStockQueryWrapper);

        //????????????????????????????????????????????????????????????????????????
        if (!StringUtils.isEmpty(opeStock)) {
            //????????????+1
            opeStock.setIntTotal(opeStock.getIntTotal() + 1);
            //???????????????+1
            opeStock.setAvailableTotal(opeStock.getAvailableTotal() + 1);
        } else {
            //????????????
            opeStock = OpeStock.builder()
                    .id(idAppService.getId(SequenceName.OPE_STOCK))
                    .dr(0)
                    .intTotal(1)
                    .outTotal(0)
                    .whseId(opeWhse.getId())
                    .revision(0)
                    .userId(enter.getUserId())
                    .tenantId(enter.getTenantId())
                    .materielProductName(opeAssemblyBOrder.getEnName())
                    .materielProductId(opeAssemblyBOrder.getProductId())
                    .materielProductType(BomCommonTypeEnums.SCOOTER.getValue())  //???????????????
                    .availableTotal(1)
                    .lockTotal(0)
                    .wornTotal(0)
                    .waitProductTotal(0)
                    .waitStoredTotal(0)
                    .updatedTime(new Date())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .createdBy(enter.getUserId())
                    .build();

        }

        //??????????????????????????????
        OpeStockBill opeStockBill = OpeStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .stockId(opeStock.getId())
                .direction(InOutWhEnums.IN.getValue())
                .status(StockBillStatusEnums.NORMAL.getValue())
                .total(1)
                .userId(enter.getUserId())
                .tenantId(enter.getTenantId())
                .sourceId(opeAssemblyOrder.getId())
                .principalId(enter.getUserId())
                .sourceType(WhseTypeEnums.ASSEMBLY.getValue())
                .operatineTime(new Date())
                .revision(0)
                .createdTime(new Date())
                .updatedTime(new Date())
                .operatineTime(new Date())
                .createdBy(enter.getUserId())
                .updatedBy(enter.getUserId())
                .build();

        //??????????????????????????????????????????????????????????????????????????????????????????
        //????????????????????????????????????
        if ((opeAssemblyOrder.getInWaitWhTotal() != null) && (opeAssemblyBOrder.getInWaitWhQty() != null)) {
            //????????????????????????????????????
            opeAssemblyBOrder.setInWaitWhQty(opeAssemblyBOrder.getInWaitWhQty() - 1);
            //??????????????????????????????
            opeAssemblyOrder.setInWaitWhTotal(opeAssemblyOrder.getInWaitWhTotal() - 1);

            //?????????????????????
            if (opeAssemblyOrder.getInWaitWhTotal() < 0 || opeAssemblyBOrder.getInWaitWhQty() < 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
            }

            if (opeAssemblyOrder.getInWaitWhTotal() == 0) {
                //?????????????????????
                opeAssemblyOrder.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
                //?????????????????????????????????
                SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
                BeanUtils.copyProperties(enter, saveNodeEnter);
                saveNodeEnter.setId(opeAssemblyOrder.getId());
                saveNodeEnter.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
                saveNodeEnter.setEvent(AssemblyEventEnums.IN_PRODUCTION_WH.getValue());
                saveNodeEnter.setMemo(null);
                receiptTraceService.saveAssemblyNode(saveNodeEnter);
            }
        } else {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
        }

        if (opeAssemblyBOrder.getInWaitWhQty() == 0) {
            //????????????????????????
            opeAssemblyBOrder.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        }

        //??????????????????????????????
        OpeStockProdProduct opeStockProdProduct = OpeStockProdProduct.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_PROD_PRODUCT))
                .dr(0)
                .status(StockProductPartStatusEnums.AVAILABLE.getValue())
                .stockId(opeStock.getId())
                .productId(opeAssemblyBOrder.getProductId())
                .lot(opeAssemblyQcItem.getBatchNo())
                .serialNumber(opeProductAssembly.getProductSerialNum()) //?????????
                .productNumber(opeAssemblyBOrder.getProductNumber())
                .inStockBillId(opeStockBill.getId())
                .principalId(enter.getUserId())
                .availableQty(1)
                .outPrincipalId(0L)
                .outStockBillId(0L)
                .outStockTime(null)
                .inStockBillId(opeStockBill.getId())
                .inStockTime(new Date())
                .revision(0)
                .inWhQty(1)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();

        //??????????????????
        opeProductAssembly.setInwhStatus(Boolean.TRUE);
        opeProductAssembly.setUpdatedBy(enter.getUserId());
        opeProductAssembly.setUpdatedTime(new Date());
        opeProductAssemblyService.updateById(opeProductAssembly);

        //??????????????????????????????
        opeStockProdProductService.save(opeStockProdProduct);
        //??????????????????????????????
        opeStockBillService.save(opeStockBill);
        //??????????????????
        opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
        //?????????????????????
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //?????????????????????
        opeStock.setWaitStoredTotal(opeStock.getWaitStoredTotal() - 1);
        //????????????
        opeStockService.saveOrUpdate(opeStock);

        //???????????????????????????
        return ProductWaitInWhInfoResult.builder()
                .residueNum(opeAssemblyBOrder.getInWaitWhQty())
                .partName(opeAssemblyBOrder.getEnName())
                .productNum(opePartsProduct.getProductNumber())  //????????????
                .serialNum(enter.getProductSerialNum())  //?????????
                .proTime(opeAssemblyOrder.getCreatedTime())
                .build();
    }


}
