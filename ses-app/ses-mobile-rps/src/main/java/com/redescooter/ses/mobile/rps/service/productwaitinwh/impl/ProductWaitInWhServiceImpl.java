package com.redescooter.ses.mobile.rps.service.productwaitinwh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderEventEnums;
import com.redescooter.ses.api.common.enums.production.allocate.AllocateOrderStatusEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyEventEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.productwaitinwh.ProductWaitInWhServiceMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.mobile.rps.service.productwaitinwh.ProductWaitInWhService;
import com.redescooter.ses.mobile.rps.vo.productwaitinwh.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private OpeAssemblyBQcService opeAssemblyBQcService;

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

    @Reference
    private IdAppService idAppService;


    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、查询生产仓库待入库列表
     * @Date 2020/4/14 17:51
     * @Param [enter]
     */
    @Transactional
    @Override
    public PageResult<AllocateAndProductResult> productWaitInWhList(PageEnter enter) {
        int count = productWaitInWhServiceMapper.proWaitInWHListCount();
        List<ProductWaitInWhOneResult> productWaitInWhOneResultList = new ArrayList<>();
        //存放组装单和调拨单的可入库数据
        List<AllocateAndProductResult> allocateAndProductResultList = new ArrayList<>();
        AllocateAndProductResult allocateAndProductResult = new AllocateAndProductResult();

        //opeAssemblyOrderService对应的数据库表为空的时候直接返回
        if (count != 0) {
            QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
            //待入库数量大于0
            opeAssemblyOrderQueryWrapper.ge(OpeAssemblyOrder.COL_IN_WAIT_WH_TOTAL, 0);
            opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_STATUS, AssemblyStatusEnums.QC_PASSED.getValue());
            List<OpeAssemblyOrder> opeAssemblyOrderList = opeAssemblyOrderService.list(opeAssemblyOrderQueryWrapper);
            //返回结果集
            ProductWaitInWhOneResult productWaitInWhOneResult = null;
            if (!CollectionUtils.isEmpty(opeAssemblyOrderList)) {
                for (OpeAssemblyOrder opeAssemblyOrder : opeAssemblyOrderList) {
                    productWaitInWhOneResultList.add(
                            productWaitInWhOneResult = ProductWaitInWhOneResult.builder()
                                    .assemblyId(opeAssemblyOrder.getId())
                                    .waitInWHNum(opeAssemblyOrder.getInWaitWhTotal())
                                    .waitInWHStr(opeAssemblyOrder.getAssemblyNumber())
                                    .inWHTListTime(new Date())
                                    .sourceType(SourceTypeEnums.ASSEMBLY.getValue())//单据类型
                                    .build());
                }
                allocateAndProductResult.setProductWaitInWhOneResultList(productWaitInWhOneResultList);
            }
        }
        //获取调拨单的数据集合
        List<AllocateWaitInWhOneResult> allocateWaitInWhOneResultList = this.allocateWaitInWHList(enter);
        //展示列表为空
        if ((CollectionUtils.isEmpty(allocateWaitInWhOneResultList)) && (CollectionUtils.isEmpty(productWaitInWhOneResultList))){
            return PageResult.createZeroRowResult(enter);
        }
        //调拨单不能为空
        if (!CollectionUtils.isEmpty(allocateWaitInWhOneResultList)) {
            allocateAndProductResult.setAllocateWaitInWhOneResultList(allocateWaitInWhOneResultList);
        }

        //总的显示行数
        int total = 0;

        if (!CollectionUtils.isEmpty(allocateWaitInWhOneResultList)) {
            total += allocateWaitInWhOneResultList.size();
        }
        if (!CollectionUtils.isEmpty(productWaitInWhOneResultList)) {
            total += productWaitInWhOneResultList.size();
        }

        //组装单和调拨单可入库产品集合
        allocateAndProductResultList.add(allocateAndProductResult);
        return PageResult.create(enter, total, allocateAndProductResultList);
    }

    /**
     * @return
     * @Author kyle
     * @Description //调拨单入库
     * @Date 2020/4/26 13:41
     * @Param [enter]
     **/
    @Transactional
    public List<AllocateWaitInWhOneResult> allocateWaitInWHList(PageEnter enter) {
        QueryWrapper<OpeAllocate> opeAllocateQueryWrapper = new QueryWrapper<>();
        opeAllocateQueryWrapper.eq(OpeAllocate.COL_STATUS, AllocateOrderStatusEnums.ALLOCATE.getValue());
        List<OpeAllocate> opeAllocateList = opeAllocateService.list(opeAllocateQueryWrapper);
        //调拨单为空
        if (CollectionUtils.isEmpty(opeAllocateList)) {
            return null;
        }
        List<AllocateWaitInWhOneResult> allocateWaitInWhOneResultList = new ArrayList<>();
        AllocateWaitInWhOneResult allocateWaitInWHOneResult = null;
        if (opeAllocateList.size() == 0) {
            return null;
        } else {
            for (OpeAllocate opeAllocate : opeAllocateList) {
                allocateWaitInWhOneResultList.add(
                        allocateWaitInWHOneResult = AllocateWaitInWhOneResult.builder()
                                .allocateId(opeAllocate.getId())
                                .waitInWHNum(opeAllocate.getCount())
                                .waitInWHStr(opeAllocate.getAllocateNum())
                                .inWHTListTime(new Date())
                                .sourceType(SourceTypeEnums.ALLOCATE.getValue())//单据类型
                                .build());
            }
        }
        return allocateWaitInWhOneResultList;
    }


    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、根据主单id查询对应的待入库商品部件详情列表
     * @Date 2020/4/14 17:49
     * @Param [enter]
     */
    @Transactional
    @Override
    public <T> PageResult<T> productWaitWhItemList(ProductOrAllocateWaitInWhIdEnter enter) {
        if (StringUtils.isEmpty(enter)) {
            return PageResult.createZeroRowResult(enter);
        }

        List<ProductWaitInWhItemResult> proWaitWHItemListResult = new ArrayList<>();
        //返回结果集详情
        ProductWaitInWhItemResult productWaitInWhItemResult = null;

        //生产（组装）仓库
        if (enter.getSourceType().equals(SourceTypeEnums.ASSEMBLY.getValue())) {
            int count = productWaitInWhServiceMapper.proWaitWHItemListCount();
            if (count == 0) {
                return PageResult.createZeroRowResult(enter);
            } else {
                //查询组装单子单
                QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
                opeAssemblyBOrderQueryWrapper.gt(OpeAssemblyBOrder.COL_IN_WAIT_WH_QTY, 0);
                opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ASSEMBLY_ID, enter.getId());
                //查询组装单
                QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
                opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, enter.getId());
                opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_STATUS, AssemblyStatusEnums.QC_PASSED.getValue());
                OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);

                //抛组装单为空异常
                if (StringUtils.isEmpty(opeAssemblyOrder)) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
                }

                //通过组装单子表id查找
                List<OpeAssemblyBOrder> opeAssemblyBOrderList = opeAssemblyBOrderService.list(opeAssemblyBOrderQueryWrapper);

                if (!CollectionUtils.isEmpty(opeAssemblyBOrderList)) {
                    for (OpeAssemblyBOrder opeAssemblyBOrder : opeAssemblyBOrderList) {
                        //去查询调拨单对应的产品详细信息
                        QueryWrapper<OpePartsProduct> opePartsQueryWrapper = new QueryWrapper<>();
                        opePartsQueryWrapper.eq(OpeParts.COL_ID, opeAssemblyBOrder.getProductId());
                        OpePartsProduct opePartsProduct = opePartsProductService.getOne(opePartsQueryWrapper);

                        //部件不存在
                        if (StringUtils.isEmpty(opePartsProduct)) {
                            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                        }

                        proWaitWHItemListResult.add(
                                productWaitInWhItemResult = ProductWaitInWhItemResult.builder()
                                        .assemblyBId(opeAssemblyBOrder.getId())
                                        .assemblyStr(opeAssemblyOrder.getAssemblyNumber())
                                        .productId(opeAssemblyBOrder.getProductId())
                                        .productNum(opeAssemblyBOrder.getInWaitWhQty())
                                        .productStr(opeAssemblyBOrder.getProductNumber())
                                        .productName(opeAssemblyBOrder.getEnName())
                                        .sourceType(SourceTypeEnums.ALLOCATE.getValue())
                                        .build());
                    }
                } else {
                    return PageResult.createZeroRowResult(enter);
                }
            }
            return (PageResult<T>) PageResult.create(enter, count, proWaitWHItemListResult);
        }

        //调拨单子单
        if (enter.getSourceType().equals(SourceTypeEnums.ALLOCATE.getValue())) {
            //查询调拨单子单
            QueryWrapper<OpeAllocateB> opeAllocateBQueryWrapper = new QueryWrapper<>();
            opeAllocateBQueryWrapper.eq(OpeAllocateB.COL_ALLOCATE_ID, enter.getId());
            opeAllocateBQueryWrapper.gt(OpeAllocateB.COL_PENDING_STORAGE_QTY, 0);
            List<OpeAllocateB> opeAllocateBList = opeAllocateBService.list(opeAllocateBQueryWrapper);

            //查询调拨单
            QueryWrapper<OpeAllocate> opeAllocateQueryWrapper = new QueryWrapper<>();
            opeAllocateQueryWrapper.eq(OpeAllocate.COL_ID, enter.getId());
            opeAllocateQueryWrapper.eq(OpeAllocate.COL_STATUS, AllocateOrderStatusEnums.ALLOCATE.getValue());
            OpeAllocate allocate = opeAllocateService.getOne(opeAllocateQueryWrapper);

            //调拨单为空
            if (StringUtils.isEmpty(allocate)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
            }

            //调拨单子单为空
            if (CollectionUtils.isEmpty(opeAllocateBList)) {
                throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_B_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_B_ORDER_IS_NOT_EXIST.getMessage());
            }

            //返回结果集详情
            AllocateWaitInWhItemResult allocateWaitInWHItemResult = null;
            List<AllocateWaitInWhItemResult> allocateWaitInWhItemResultList = new ArrayList<>();
            for (OpeAllocateB opeAllocateB : opeAllocateBList) {
                //去查询调拨单对应的产品详细信息
                QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
                opePartsQueryWrapper.eq(OpeParts.COL_ID, opeAllocateB.getPartId());
                OpeParts opeParts = opePartsService.getOne(opePartsQueryWrapper);

                //部件不存在
                if (StringUtils.isEmpty(opeParts)) {
                    throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                }

                //查询调拨单的编号
                allocateWaitInWhItemResultList.add(
                        allocateWaitInWHItemResult = AllocateWaitInWhItemResult.builder()
                                .allocateBId(opeAllocateB.getId())
                                .partNum(opeAllocateB.getTotal())
                                .partName(opeParts.getCnName())
                                .partStr(opeParts.getPartsNumber())
                                .sourceType(SourceTypeEnums.ALLOCATE.getValue())
                                .build());
            }
            return (PageResult<T>) PageResult.create(enter, opeAllocateBList.size(), allocateWaitInWhItemResultList);
        }
        return PageResult.createZeroRowResult(enter);
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、根据子单id查询生产仓库待入库详情
     * @Date 2020/4/14 17:50
     * @Param [enter]
     */
    @Transactional
    @Override
    public AllocateWaitInWhItemResult allocateWaitInWhItem(IdEnter enter) {

        //查询调拨单子单
        QueryWrapper<OpeAllocateB> opeAllocateBQueryWrapper = new QueryWrapper<>();
        opeAllocateBQueryWrapper.eq(OpeAllocateB.COL_ID, enter.getId());
        opeAllocateBQueryWrapper.gt(OpeAllocateB.COL_PENDING_STORAGE_QTY, 0); //待入库数大于0
        OpeAllocateB opeAllocateB = opeAllocateBService.getOne(opeAllocateBQueryWrapper);

        //调拨单为空
        if (StringUtils.isEmpty(opeAllocateB)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_B_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_B_ORDER_IS_NOT_EXIST.getMessage());
        }

        //查询调拨单
        QueryWrapper<OpeAllocate> opeAllocateQueryWrapper = new QueryWrapper<>();
        opeAllocateQueryWrapper.eq(OpeAllocate.COL_ID, opeAllocateB.getAllocateId());
        opeAllocateQueryWrapper.eq(OpeAllocate.COL_STATUS, AllocateOrderStatusEnums.ALLOCATE.getValue());
        OpeAllocate allocate = opeAllocateService.getOne(opeAllocateQueryWrapper);

        //调拨单子单为空
        if (StringUtils.isEmpty(allocate)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }

        //去查询调拨单对应的产品详细信息
        QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.eq(OpeParts.COL_ID, opeAllocateB.getPartId());
        OpeParts opeParts = opePartsService.getOne(opePartsQueryWrapper);

        //部件不存在
        if (StringUtils.isEmpty(opeParts)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        return AllocateWaitInWhItemResult.builder()
                .allocateBId(opeAllocateB.getId())
                .allocateNum(allocate.getAllocateNum())
                .partId(opeAllocateB.getPartId())
                .partNum(opeAllocateB.getTotal())
                .partName(opeParts.getFrName())
                .sourceType(SourceTypeEnums.ALLOCATE.getValue())
                .build();
    }

    /**
     * @param enter
     * @return
     * @Author kyle
     * @Description //1、提交生产仓库入库信息
     * @Date 2020/4/14 17:52
     * @Param [enter]
     */
    @Transactional
    @Override
    public ProductWaitInWhInfoResult productWaitInWhInfoIn(ProductWaitInWhIdItemEnter enter) {

        //本次应该入库的数量和实际入库数量不符
        if (!enter.getInWhNum().equals(enter.getShouldInWhNum())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
        }

        //组装单入库
        if (enter.getSourceType().equals(SourceTypeEnums.ASSEMBLY.getValue())) {
            return this.getProductWaitInWhInfoResultFromAssembly(enter);
        }

        //调拨单入库
        if (enter.getSourceType().equals(SourceTypeEnums.ALLOCATE.getValue())) {
            return this.getProductWaitInWhInfoResultFromAllocate(enter);
        }

        return null;

    }

    /**
     * @return
     * @Author kyle
     * @Description //提交生产仓库入库信息->调拨单
     * @Date 2020/4/26 19:45
     * @Param [enter]
     **/
    private ProductWaitInWhInfoResult getProductWaitInWhInfoResultFromAllocate(ProductWaitInWhIdItemEnter enter) {
        //返回结果集
        ProductWaitInWhInfoResult productWaitInWhInfoResult = null;

        //验证序列号是否存在
        QueryWrapper<OpeStockPurchas> opeStockPurchasQueryWrapper = new QueryWrapper<>();
        opeStockPurchasQueryWrapper.eq(OpeStockPurchas.COL_SERIAL_NUMBER,enter.getProductSerialNum());
        OpeStockPurchas opeStockPurchas = opeStockPurchasService.getOne(opeStockPurchasQueryWrapper);

        //序列号不存在
        if(StringUtils.isEmpty(opeStockPurchas)){
            throw new SesMobileRpsException(ExceptionCodeEnums.SERIAL_NUMBER_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUMBER_IS_EMPTY.getMessage());
        }

        //查询调拨单子单
        QueryWrapper<OpeAllocateB> opeAllocateBQueryWrapper = new QueryWrapper<>();
        opeAllocateBQueryWrapper.eq(OpeAllocateB.COL_ID, enter.getId());
        OpeAllocateB opeAllocateB = opeAllocateBService.getOne(opeAllocateBQueryWrapper);

        //调拨单子单为空
        if (StringUtils.isEmpty(opeAllocateB)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_B_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_B_ORDER_IS_NOT_EXIST.getMessage());
        }

        //查询调拨单
        QueryWrapper<OpeAllocate> opeAllocateQueryWrapper = new QueryWrapper<>();
        opeAllocateQueryWrapper.eq(OpeAllocate.COL_ID, opeAllocateB.getAllocateId());
        OpeAllocate opeAllocate = opeAllocateService.getOne(opeAllocateQueryWrapper);

        //调拨单为空
        if (StringUtils.isEmpty(opeAllocate)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ALLOCATE_ORDER_IS_NOT_EXIST.getMessage());
        }

        //查询对应的调拨备料记录
        QueryWrapper<OpeAllocateBTrace> opeAllocateBTraceQueryWrapper = new QueryWrapper<>();
        opeAllocateBTraceQueryWrapper.eq(OpeAllocateBTrace.COL_ALLOCATE_B_ID, opeAllocateB.getId());
        OpeAllocateBTrace opeAllocateBTrace = opeAllocateBTraceService.getOne(opeAllocateBTraceQueryWrapper);

        //调拨备料记录为空
        if (StringUtils.isEmpty(opeAllocateBTrace)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ALLOCATE_TRACE_IS_EMPTY.getCode(), ExceptionCodeEnums.ALLOCATE_TRACE_IS_EMPTY.getMessage());
        }

        //去查询调拨单对应的产品详细信息
        QueryWrapper<OpeParts> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.eq(OpeParts.COL_ID, opeAllocateB.getPartId());
        OpeParts opeParts = opePartsService.getOne(opePartsQueryWrapper);

        //部件不存在
        if (StringUtils.isEmpty(opeParts)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //查找仓库类型
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);

        //仓库不存在
        if (StringUtils.isEmpty(opeWhse)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WH_IS_EMPTY.getCode(), ExceptionCodeEnums.WH_IS_EMPTY.getMessage());
        }

        //根据仓库id查询库存表
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opeAllocateB.getPartId());
        OpeStock opeStock = opeStockService.getOne(opeStockQueryWrapper);

        //查询仓库中是否有该产品，有就数量叠加，没有就新增
        if (!StringUtils.isEmpty(opeStock)) {
            //入库总数+
            opeStock.setIntTotal(opeParts.getIdClass() ? (opeStock.getIntTotal() + 1) : (opeStock.getIntTotal() + enter.getInWhNum()));
            //剩余库存数+
            opeStock.setIntTotal(opeParts.getIdClass() ? (opeStock.getIntTotal() + 1) : (opeStock.getIntTotal() + enter.getInWhNum()));
            opeStockService.updateById(opeStock);
        } else {
            //新建库存
            opeStock = OpeStock.builder()
                    .id(idAppService.getId(SequenceName.OPE_STOCK))
                    .dr(0)
                    .intTotal(opeParts.getIdClass() ? 1 : enter.getInWhNum())
                    .outTotal(0)
                    .whseId(opeWhse.getId())
                    .revision(0)
                    .userId(enter.getUserId())
                    .tenantId(enter.getTenantId())
                    .materielProductName(opeParts.getCnName())
                    .materielProductId(opeAllocateB.getPartId())
                    .materielProductType(opeParts.getPartsType())//产品物料类型
                    .availableTotal(opeParts.getIdClass() ? 1 : enter.getInWhNum())
                    .updatedTime(new Date())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .createdBy(enter.getUserId())
                    .build();
            //更新库存
            opeStockService.save(opeStock);
        }

        //判断调拨单的状态是否改变，记录调拨单状态变更
        if ((!StringUtils.isEmpty(opeAllocate.getPendingStorageTotal())) && (!StringUtils.isEmpty(opeAllocateB.getPendingStorageQty()))) {
            //修改调拨单的待入库数量
            opeAllocate.setPendingStorageTotal(opeParts.getIdClass() ? (opeAllocate.getPendingStorageTotal() - 1) : (opeAllocate.getPendingStorageTotal() - enter.getInWhNum()));
            //修改调拨单子单的待入库数量
            opeAllocateB.setPendingStorageQty(opeParts.getIdClass() ? (opeAllocateB.getPendingStorageQty() - 1) : (opeAllocateB.getPendingStorageQty() - enter.getInWhNum()));
            //待入库总数错误
            if (opeAllocate.getPendingStorageTotal() < 0 || opeAllocateB.getPendingStorageQty() < 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
            }
            //调拨单入库完成
            if (opeAllocate.getPendingStorageTotal() == 0) {
                opeAllocate.setStatus(AllocateOrderStatusEnums.INPRODUCTIONWH.getValue());
                //修改订单状态该表的节点
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

        //创建一条生产入库信息
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

        //创建商品成品库的数据
        OpeStockProdPart opeStockProdPart = OpeStockProdPart.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_PROD_PART))
                .dr(0)
                .userId(enter.getUserId())
                .tenantId(enter.getTenantId())
                .status(StockProductPartStatusEnums.AVAILABLE.getValue())
                .stockId(opeStock.getId())
                .partId(opeParts.getId())
                .lot(opeAllocateBTrace.getBatchNo())
                .serialNumber(opeStockPurchas.getSerialNumber()) //序列号
                .partsNumber(opeParts.getPartsNumber())
                .inStockBillId(opeStockBill.getId())
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

        //保存调拨单状态
        opeAllocateService.updateById(opeAllocate);
        //保存调拨子单状态
        opeAllocateBService.updateById(opeAllocateB);
        //保存一条生产入库信息
        opeStockBillService.save(opeStockBill);
        //保存生产成品库的数据
        opeStockProdPartService.save(opeStockProdPart);

        //返回成功结果集
        return ProductWaitInWhInfoResult.builder()
                .residueNum(opeAllocateB.getPendingStorageQty())
                .partNum(opeParts.getPartsNumber())
                .partName(opeParts.getCnName())
                .batchNum(opeAllocateBTrace.getBatchNo())  //批次号
                .serialNum(opeParts.getIdClass() ? opeAllocateBTrace.getSerialNum() : null)  //序列号
                .build();
    }

    /**
     * @return
     * @Author kyle
     * @Description //提交生产仓库入库信息->组装单
     * @Date 2020/4/26 19:33
     * @Param [enter]
     **/
    private ProductWaitInWhInfoResult getProductWaitInWhInfoResultFromAssembly(ProductWaitInWhIdItemEnter enter) {
        //返回结果集
        ProductWaitInWhInfoResult productWaitInWhInfoResult = null;

        //验证序列号是否存在
        QueryWrapper<OpeProductAssembly> opeProductAssemblyQueryWrapper = new QueryWrapper<>();
        opeProductAssemblyQueryWrapper.eq(OpeProductAssembly.COL_PRODUCT_SERIAL_NUM,enter.getProductSerialNum());
        OpeProductAssembly opeProductAssembly = opeProductAssemblyService.getOne(opeProductAssemblyQueryWrapper);

        //序列号不存在
        if(StringUtils.isEmpty(opeProductAssembly)){
            throw new SesMobileRpsException(ExceptionCodeEnums.SERIAL_NUMBER_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUMBER_IS_EMPTY.getMessage());
        }

        //查询对应的质检记录
        QueryWrapper<OpeAssemblyBQc> opeAssemblyBQcQueryWrapper = new QueryWrapper<>();
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_ASSEMBLY_B_ID, enter.getId());
        opeAssemblyBQcQueryWrapper.eq(OpeAssemblyBQc.COL_STATUS, AssemblyStatusEnums.QC_PASSED.getValue());
        OpeAssemblyBQc opeAssemblyBQc = opeAssemblyBQcService.getOne(opeAssemblyBQcQueryWrapper);

        //抛质检记录为空异常
        OpeAssemblyQcItem opeAssemblyQcItem = null;
        if (StringUtils.isEmpty(opeAssemblyBQc)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.QC_INFO_IS_EMPTY.getCode(), ExceptionCodeEnums.QC_INFO_IS_EMPTY.getMessage());
        } else {
            //通过质检项查询质检结果id
            QueryWrapper<OpeAssemblyQcItem> opeAssemblyQcItemQueryWrapper = new QueryWrapper<>();
            opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_ASSEMBLY_B_ID, enter.getId());
            opeAssemblyQcItemQueryWrapper.eq(OpeAssemblyQcItem.COL_ASSEMBLY_B_QC_ID, opeAssemblyBQc.getId());
            opeAssemblyQcItem = opeAssemblyQcItemService.getOne(opeAssemblyQcItemQueryWrapper);
        }

        //获取组装单子单
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ID, enter.getId());
        opeAssemblyBOrderQueryWrapper.gt(OpeAssemblyBOrder.COL_IN_WAIT_WH_QTY, 0);
        OpeAssemblyBOrder opeAssemblyBOrder = opeAssemblyBOrderService.getOne(opeAssemblyBOrderQueryWrapper);

        //抛组装子单为空异常
        if (StringUtils.isEmpty(opeAssemblyBOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getCode(), ExceptionCodeEnums.OPE_B_ORDER_IS_EMPTY.getMessage());
        }

        //如果待入库产品的数量为0，抛异常
        if (StringUtils.isEmpty(opeAssemblyBOrder.getInWaitWhQty())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_IS_EMPTY.getMessage());
        }

        //获取组装单
        QueryWrapper<OpeAssemblyOrder> opeAssemblyOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderQueryWrapper.eq(OpeAssemblyOrder.COL_ID, opeAssemblyBOrder.getAssemblyId());
        opeAssemblyOrderQueryWrapper.gt(OpeAssemblyOrder.COL_IN_WAIT_WH_TOTAL, 0);//部件待入库数不能为空
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getOne(opeAssemblyOrderQueryWrapper);

        //组装单不存在
        if (StringUtils.isEmpty(opeAssemblyOrder)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getCode(), ExceptionCodeEnums.ASSEMNLY_ORDER_IS_EXIST.getMessage());
        }

        //查找仓库类型
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ASSEMBLY.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);

        //仓库不存在
        if (StringUtils.isEmpty(opeWhse)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WH_IS_EMPTY.getCode(), ExceptionCodeEnums.WH_IS_EMPTY.getMessage());
        }

        //去查询组装单单对应的产品详细信息
        QueryWrapper<OpePartsProduct> opePartsQueryWrapper = new QueryWrapper<>();
        opePartsQueryWrapper.eq(OpeParts.COL_ID, opeAssemblyBOrder.getProductId());
        OpePartsProduct opePartsProduct = opePartsProductService.getOne(opePartsQueryWrapper);

        //产品不存在
        if (StringUtils.isEmpty(opePartsProduct)) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        //根据仓库id查询库存表
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opeAssemblyBOrder.getProductId());
        OpeStock opeStock = opeStockService.getOne(opeStockQueryWrapper);

        //查询仓库中是否有该产品，有就数量叠加，没有就新增
        if (!StringUtils.isEmpty(opeStock)) {
            //入库总数+1
            opeStock.setIntTotal(opeStock.getIntTotal() + 1);
            //剩余库存数+1
            opeStock.setAvailableTotal(opeStock.getAvailableTotal() + 1);
            opeStockService.updateById(opeStock);
        } else {
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
                    .materielProductType(BomCommonTypeEnums.SCOOTER.getCode())  //类型是整车
                    .availableTotal(1)
                    .wornTotal(0)
                    .updatedTime(new Date())
                    .createdTime(new Date())
                    .updatedBy(enter.getUserId())
                    .createdBy(enter.getUserId())
                    .build();
            //更新库存
            opeStockService.save(opeStock);
        }

        //更新一条生产入库信息
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

        //把入库成功的产品对应的组装单和组装单子单的待入库数量进行修改
        //修改组装单的总待入库数量
        if ((!StringUtils.isEmpty(opeAssemblyOrder.getInWaitWhTotal())) && (!StringUtils.isEmpty(opeAssemblyBOrder.getInWaitWhQty()))) {
            //修改组装单子单的待入库数
            opeAssemblyBOrder.setInWaitWhQty(opeAssemblyBOrder.getInWaitWhQty() - 1);
            //修改组装单的待入库数
            opeAssemblyOrder.setInWaitWhTotal(opeAssemblyOrder.getInWaitWhTotal() - 1);

            //待入库总数错误
            if (opeAssemblyOrder.getInWaitWhTotal() < 0 || opeAssemblyBOrder.getInWaitWhQty() < 0) {
                throw new SesMobileRpsException(ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getCode(), ExceptionCodeEnums.WAIT_IN_WH_NUM_ERROR.getMessage());
            }

            if (opeAssemblyOrder.getInWaitWhTotal() == 0) {
                //组装单入库完成
                opeAssemblyOrder.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
                //修改订单状态该表的节点
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
            //组装子单入库完成
            opeAssemblyBOrder.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        }

        //创建生产成品库的数据
        OpeStockProdProduct opeStockProdProduct = OpeStockProdProduct.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_PROD_PRODUCT))
                .dr(0)
                .status(StockProductPartStatusEnums.AVAILABLE.getValue())
                .stockId(opeStock.getId())
                .productId(opeAssemblyBOrder.getProductId())
                .lot(opeAssemblyBQc.getBatchNo())
                .serialNumber(opeProductAssembly.getProductSerialNum()) //序列号
                .productNumber(opeAssemblyBOrder.getProductNumber())
                .inStockBillId(opeStockBill.getId())
                .principalId(enter.getUserId())
                .outPrincipalId(0L)
                .outStockBillId(0L)
                .outStockTime(null)
                .inStockBillId(0L)
                .inStockTime(new Date())
                .revision(0)
                .inWhQty(1)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();

        //保存生产成品库的数据
        opeStockProdProductService.save(opeStockProdProduct);
        //保存一条生产入库信息
        opeStockBillService.save(opeStockBill);
        //保存子单状态
        opeAssemblyBOrderService.updateById(opeAssemblyBOrder);
        //保存组装单状态
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //返回入库成功结果集
        return ProductWaitInWhInfoResult.builder()
                .residueNum(opeAssemblyBOrder.getInWaitWhQty())
                .partName(opeAssemblyBOrder.getEnName())
                .productNum(opePartsProduct.getProductNumber())  //车辆编码
                .serialNum(opeAssemblyQcItem.getSerialNum())  //序列号
                .proTime(opeAssemblyOrder.getCreatedTime())
                .build();
    }
}
