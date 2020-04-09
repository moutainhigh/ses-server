package com.redescooter.ses.web.ros.service.production.assembly.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.PaymentTypeEnums;
import com.redescooter.ses.api.common.enums.production.ProductionTypeEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.production.assembly.OpeAssemblyBStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PayStatusEnums;
import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.dao.production.AssemblyServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeAssembiyOrderTrace;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBOrder;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrder;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPart;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPayment;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.dm.PartDetailDto;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeAssembiyOrderTraceService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyBOrderService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderPartService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderPaymentService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockBillService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.production.assembly.AssemblyService;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.PayEnter;
import com.redescooter.ses.web.ros.vo.production.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.PaymentItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.ProductionPartsEnter;
import com.redescooter.ses.web.ros.vo.production.StagingPaymentEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyListEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyResult;
import com.redescooter.ses.web.ros.vo.production.assembly.SaveAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.SetPaymentAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.StartPrepareEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.productItemResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:AssemblyServiceImpl
 * @description: AssemblyServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:04
 */
@Service
public class AssemblyServiceImpl implements AssemblyService {

    @Autowired
    private AssemblyServiceMapper assemblyServiceMapper;

    @Autowired
    private OpeSysUserProfileService opeSysUserProfileService;

    @Autowired
    private OpeFactoryService opeFactoryService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpePartsProductBService opePartsProductBService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeAssemblyOrderPaymentService opeAssemblyOrderPaymentService;

    @Autowired
    private OpeAssemblyOrderService opeAssemblyOrderService;

    @Autowired
    private OpeAssemblyBOrderService opeAssemblyOrderBService;

    @Autowired
    private OpeAssemblyOrderPartService opeAssemblyOrderPartService;

    @Autowired
    private OpeAssembiyOrderTraceService opeAssembiyOrderTraceService;

    @Autowired
    private OpeWhseService OpeWhseService;

    @Autowired
    private OpeStockBillService opeStockBillService;

    @Reference
    private IdAppService idAppService;

    /**
     * 状态统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByType(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResult = assemblyServiceMapper.countByTypes(enter);

        Map<String, Integer> result = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(countByStatusResult)) {
            countByStatusResult.forEach(item -> {
                result.put(item.getStatus(), item.getTotalCount());
            });
        }
        for (ProductionTypeEnums item : ProductionTypeEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), 0);
            }
        }
        return result;
    }

    /**
     * 状态列表
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> statusList(GeneralEnter enter) {
        Map<String, String> result = Maps.newHashMap();
        for (AssemblyStatusEnums item : AssemblyStatusEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), item.getCode());
            }
        }
        return result;
    }

    /**
     * 付款方式
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> paymentTypeList(GeneralEnter enter) {
        Map<String, String> result = Maps.newHashMap();
        for (PaymentTypeEnums item : PaymentTypeEnums.values()) {
            if (!result.containsKey(item.getValue())) {
                result.put(item.getValue(), item.getCode());
            }
        }
        return result;
    }

    /**
     * 查询可组装的商品列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveAssemblyProductResult> queryAssemblyProduct(SaveAssemblyProductEnter enter) {
        List<SaveAssemblyProductResult> result = Lists.newArrayList();

        //获取 产品bom规则
        Map<Long, List<OpePartsProductB>> productMap = Maps.newHashMap();

        List<OpePartsProduct> opePartsProduct = new ArrayList<>();
        //获取库存
        List<OpeStock> opeStockList = new ArrayList<>();
        if (buildProductMap(productMap, opePartsProduct, opeStockList)) {
            return new ArrayList<>();
        }
        //可进行组装的车辆Id
        List<Long> productIds = Lists.newArrayList();

        //减库存
        List<ProductionPartsEnter> productList = new ArrayList<>();
        if (StringUtils.isNotEmpty(enter.getProductList())) {
            try {
                productList = JSON.parseArray(enter.getProductList(), ProductionPartsEnter.class);
            } catch (Exception e) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }

            productList.forEach(product -> {
                if (!productMap.containsKey(product.getId())) {
                    return;
                }
                for (OpePartsProductB item : productMap.get(product.getId())) {
                    opeStockList.forEach(stock -> {
                        if (stock.getMaterielProductId().equals(item.getPartsId())) {
                            stock.setAvailableTotal(stock.getAvailableTotal() - item.getPartsQty() * product.getQty());
                            if (stock.getAvailableTotal() < 0) {
                                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                            }
                        }
                    });
                }
            });
        }

        //筛选出可组装的产品
        flag1:
        for (Map.Entry<Long, List<OpePartsProductB>> e : productMap.entrySet()) {
            Long k = e.getKey();
            //如果有任何 配件不满足库存要求 终止 该产品的查询
            int total = 0;
            flag2:
            for (OpePartsProductB item : e.getValue()) {
                Boolean existPart = Boolean.FALSE;

                flag3:
                for (OpeStock stock : opeStockList) {
                    if (item.getPartsId().equals(stock.getMaterielProductId())) {
                        if (stock.getAvailableTotal() / item.getPartsQty() != 0) {
                            total++;
                            existPart = Boolean.TRUE;
                            break flag3;
                        }
                    }
                }
            }
            if (total == e.getValue().size()) {
                productIds.add(k);
            }
        }

        // 确认可组装的产品的最大值
        Map<Long, Integer> canAssembledMap = Maps.newHashMap();

        flag1:
        for (Map.Entry<Long, List<OpePartsProductB>> entry : productMap.entrySet()) {
            Long key = entry.getKey();
            List<OpePartsProductB> value = entry.getValue();
            Integer maxTotal = 0;
            int total = 0;
            if (productIds.contains(key)) {

                flag2:
                for (OpePartsProductB item : value) {

                    flag3:
                    for (OpeStock stock : opeStockList) {
                        if (item.getPartsId().equals(stock.getMaterielProductId())) {

                            int canAss = Long.valueOf(stock.getAvailableTotal() / item.getPartsQty()).intValue();
                            if (maxTotal == 0) {
                                total++;
                                maxTotal = canAss;
                                continue flag2;
                            }
                            if (canAss < maxTotal) {
                                total++;
                                maxTotal = canAss;
                                continue flag2;
                            }
                            if (canAss > 0) {
                                total++;
                                continue flag2;
                            }
                        }
                    }
                }
            }
            if (total == value.size()) {
                canAssembledMap.put(key, maxTotal);
            }
        }

        opePartsProduct.forEach(item -> {
            SaveAssemblyProductResult productResult = null;
            if (productMap.containsKey(item.getId())) {
                productResult = SaveAssemblyProductResult.builder()
                        .id(item.getId())
                        .productN(item.getProductNumber())
                        .enName(item.getEnName())
                        .cnName(item.getCnName())
                        .stocks(0)
                        .build();
                if (canAssembledMap.containsKey(item.getId())) {
                    productResult.setStocks(canAssembledMap.get(item.getId()));
                }
            }
            result.add(productResult);
        });
        //去除结果集上的空集
        result.removeIf(item -> item == null);


        //给结果集添加已选择的数量
        for (SaveAssemblyProductResult item : result) {
            if (CollectionUtils.isNotEmpty(productList)) {
                for (ProductionPartsEnter product : productList) {
                    if (item.getId().equals(product.getId())) {
                        item.setSelectedQty(product.getQty());
                        break;
                    }
                }
            }
        }
        return result;
    }

    private Boolean buildProductMap(Map<Long, List<OpePartsProductB>> productMap, List<OpePartsProduct> opePartsProduct, List<OpeStock> opeStockList) {
        //查询所有组装商品
        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_DR, 0);
        opePartsProduct.addAll(opePartsProductService.list(opePartsProductQueryWrapper));

        if (CollectionUtils.isEmpty(opePartsProduct)) {
            return true;
        }
        List<Long> productIdsList = Lists.newArrayList();
        opePartsProduct.forEach(item -> {
            productIdsList.add(item.getId());
        });

        //查询整车组装配方
        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_DR, 0);
        opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_PRODUCT_ID, productIdsList);
        List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);

        if (CollectionUtils.isEmpty(partsProductBList)) {
            return true;
        }

        opePartsProduct.forEach(scooter -> {
            List<OpePartsProductB> productList = Lists.newArrayList();
            partsProductBList.forEach(item -> {
                if (scooter.getId().equals(item.getPartsProductId())) {
                    productList.add(item);
                }
            });
            if (CollectionUtils.isNotEmpty(productList)) {
                productMap.put(scooter.getId(), productList);
            }
        });

        //查询仓库信息
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, 0);
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockList.addAll(opeStockService.list(opeStockQueryWrapper));
        if (CollectionUtils.isEmpty(opeStockList)) {
            return true;
        }
        return false;
    }

    /**
     * 保存组装单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveAssembly(SaveAssemblyEnter enter) {
        // 出库单信息保存
        List<OpeStockBill> saveOpeStockBillList = Lists.newArrayList();
        //组装单子表
        List<OpeAssemblyBOrder> saveOpeAssemblyBOrderList = new ArrayList<OpeAssemblyBOrder>();
        //组装单
        OpeAssemblyOrder saveOpeAssemblyOrder = null;
        //组装单部件统计表
        List<OpeAssemblyOrderPart> saveOpeAssemblyOrderPartList = Lists.newArrayList();

        Long assemblyId = idAppService.getId(SequenceName.OPE_ASSEMBLY_ORDER);

        // 库存更新
        List<OpeStock> saveStockList = Lists.newArrayList();

        //商品信息转换
        List<ProductionPartsEnter> productList = null;
        try {
            productList = JSONArray.parseArray(enter.getProductList(), ProductionPartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        //收货人、代工厂校验
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, 0);
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID, enter.getConsigneeId());
        if (opeSysUserProfileService.getOne(opeSysUserProfileQueryWrapper) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        if (opeFactoryService.getById(enter.getFactoryId()) == null) {
            throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
        }

        // 查询每个产品所需部件数量
        Map<Long, Integer> partMap = Maps.newHashMap();

        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_DR, 0);
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        List<OpePartsProduct> opePartsProductList = opePartsProductService.list(opePartsProductQueryWrapper);
        if (CollectionUtils.isEmpty(opePartsProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<Long> productIds = new ArrayList<>();
        opePartsProductList.forEach(item -> {
            productIds.add(item.getId());
        });

        //检验产品是否存在
        productList.forEach(item -> {
            if (!productIds.contains(item.getId())) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
        });

        //查询产品对应的bom 规则
        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_DR, 0);
        opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_PRODUCT_ID, productIds);
        List<OpePartsProductB> productBList = opePartsProductBService.list(opePartsProductBQueryWrapper);
        if (CollectionUtils.isEmpty(productBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        // 封装 要组装的所有产品 所需part数量之和
        for (OpePartsProductB item : productBList) {
            for (ProductionPartsEnter product : productList) {
                if (item.getPartsProductId().equals(product.getId())) {
                    if (partMap.containsKey(item.getPartsId())) {
                        partMap.put(item.getPartsId(), partMap.get(item.getPartsId()) + item.getPartsQty() * product.getQty());
                    } else {
                        partMap.put(item.getPartsId(), item.getPartsQty() * product.getQty());
                    }
                }
            }
        }
        //查询 所有产品的产品报价


        //查询仓库
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        // 查询部件库存
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, 0);
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, new ArrayList<>(partMap.keySet()));
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);
        if (CollectionUtils.isEmpty(opeStockList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        // 将所有产品零部件 拆分 和库存进行校验
        partMap.forEach((key, value) -> {
            opeStockList.forEach(item -> {
                if (key.equals(item.getMaterielProductId())) {
                    if (value > item.getAvailableTotal()) {
                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                    }
                    //形成出库单
                    saveOpeStockBillList.add(buildStockBillRepeatedly(item.getId(), value, enter.getUserId(), assemblyId, InOutWhEnums.OUT.getValue()));

                    // 减库存
                    item.setAvailableTotal(item.getAvailableTotal() - value);
                    item.setOutTotal(item.getOutTotal() + value);
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                    saveStockList.add(item);
                }
            });
        });


        //获取到每个产品单价，封装 产品单价的Map
        Map<Long, BigDecimal> productUnitPrice = Maps.newHashMap();
        buildProductUnitPriceMap(opePartsProductList, productList, productBList, partMap, productUnitPrice);

        //封装 子表数据
        buildOpeAssemblyBOrderList(enter, saveOpeAssemblyBOrderList, assemblyId, productList, opePartsProductList, productUnitPrice);

        //封装 组装单
        saveOpeAssemblyOrder = buildOpeAssemblyOrder(enter, productList, assemblyId, productUnitPrice);

        //封装组装单 配件表数据
        buildSaveOpeAssemblyOrderPartList(enter, saveOpeAssemblyOrderPartList, assemblyId, saveStockList, partMap);

        //保存出库单
        opeStockBillService.saveBatch(saveOpeStockBillList);

        //保存组装单
        opeAssemblyOrderService.save(saveOpeAssemblyOrder);

        //保存组装单子表
        opeAssemblyOrderBService.saveBatch(saveOpeAssemblyBOrderList);

        // 保存组装单 配件统计表
        opeAssemblyOrderPartService.saveBatch(saveOpeAssemblyOrderPartList);

        //更新库存
        opeStockService.updateBatchById(saveStockList);
        //保存节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(assemblyId);
        saveNodeEnter.setStatus(AssemblyStatusEnums.PENDING.getValue());
        saveNodeEnter.setEvent(AssemblyStatusEnums.PENDING.getValue());
        saveNodeEnter.setMemo(null);
        this.saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 工厂列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<FactoryCommonResult> factoryList(GeneralEnter enter) {
        List<FactoryCommonResult> result = new ArrayList<>();
        QueryWrapper<OpeFactory> opeFactoryQueryWrapper = new QueryWrapper();
        opeFactoryQueryWrapper.eq(OpeFactory.COL_DR, 0);
        List<OpeFactory> opeFactoryList = opeFactoryService.list(opeFactoryQueryWrapper);
        opeFactoryList.forEach(item -> {
            result.add(FactoryCommonResult.builder()
                    .id(item.getId())
                    .factoryName(item.getFactoryName())
                    .contactFullName(item.getContactFullName())
                    .contactEmail(item.getContactEmail())
                    .contactPhone(item.getContactPhone())
                    .contactPhoneCode(item.getContactPhoneCountryCode())
                    .build());
        });
        return result;
    }

    /**
     * 收件人列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<ConsigneeResult> consigneeList(GeneralEnter enter) {
        List<ConsigneeResult> consigneeResultlist = new ArrayList<>();
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, 0);
        opeSysUserProfileQueryWrapper.ne(OpeSysUserProfile.COL_SYS_USER_ID, Constant.ADMINUSERID);
        List<OpeSysUserProfile> opeSysUserProfileList = opeSysUserProfileService.list(opeSysUserProfileQueryWrapper);
        opeSysUserProfileList.forEach(item -> {
            consigneeResultlist.add(ConsigneeResult.builder()
                    .id(item.getSysUserId())
                    .firstName(item.getFirstName())
                    .lastName(item.getLastName())
                    .phoneCountryCode(item.getCountryCode())
                    .phone(item.getTelNumber())
                    .email(item.getEmail())
                    .build());
        });
        return consigneeResultlist;
    }

    /**
     * 组装列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AssemblyResult> list(AssemblyListEnter enter) {
        //对type 进行拆分 组装statusList
        List<String> statusList = Lists.newArrayList();
        if (StringUtils.equals(enter.getType(), ProductionTypeEnums.TODO.getValue())) {
            for (AssemblyStatusEnums item : AssemblyStatusEnums.values()) {
                statusList.add(item.getValue());
            }
            statusList.remove(AssemblyStatusEnums.CANCELLED.getValue());
            statusList.remove(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        }
        if (StringUtils.equals(enter.getType(), ProductionTypeEnums.HISTORY.getValue())) {
            statusList.add(AssemblyStatusEnums.CANCELLED.getValue());
            statusList.add(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        }

        int count = assemblyServiceMapper.assemblyListCount(enter, statusList);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        List<AssemblyResult> assemblyResultList = assemblyServiceMapper.assemblyList(enter, statusList);
        if (CollectionUtils.isEmpty(assemblyResultList)) {
            return PageResult.createZeroRowResult(enter);
        }

        List<Long> assemblyIds = Lists.newArrayList();
        assemblyResultList.forEach(item -> {
            assemblyIds.add(item.getId());
        });

        //获取支付信息
        List<PaymentItemDetailResult> opeAssemblyOrderPaymentList = assemblyServiceMapper.paymentItemDetailListByAssIds(assemblyIds);

        for (AssemblyResult item : assemblyResultList) {
            //月结信息 赋值
            if (StringUtils.equals(item.getPaymentType(), PaymentTypeEnums.MONTHLY_PAY.getValue())) {
                for (PaymentItemDetailResult payment : opeAssemblyOrderPaymentList) {
                    if (item.getId().equals(payment.getBizId())) {
                        item.setStatementDate(payment.getEstimatedPaymentDate());
                        item.setDays(payment.getDayNum());
                    }
                }

            } else {
                //分期信息赋值
                int payTotal = 0;
                int unpayTotal = 0;
                List<PaymentItemDetailResult> paymentList = Lists.newArrayList();
                for (PaymentItemDetailResult payment : opeAssemblyOrderPaymentList) {
                    if (item.getId().equals(payment.getBizId())) {
                        if (StringUtils.equals(payment.getStatus(), PayStatusEnums.UNPAID.getValue())) {
                            unpayTotal++;
                        }
                        if (StringUtils.equals(payment.getStatus(), PayStatusEnums.PAID.getValue())) {
                            payTotal++;
                        }
                        paymentList.add(payment);
                    }
                }
                item.setStagTotal(payTotal + unpayTotal);
                item.setPaidstagNum(payTotal);
                item.setPaymentItemDetailResultList(paymentList);
            }
        }

        return PageResult.create(enter, count, assemblyResultList);
    }

    /**
     * 详情
     *
     * @param enter
     * @return
     */
    @Override
    public AssemblyResult detail(IdEnter enter) {
        //组装单校验
        checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.detail(enter);
    }


    /**
     * 组装单节点
     *
     * @param enter
     * @return
     */
    @Override
    public List<CommonNodeResult> assemblyNode(IdEnter enter) {
        checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.assemblyNode(enter);
    }

    /**
     * 组装单信息导出
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult export(IdEnter enter) {
        return null;
    }

    /**
     * 设置支付信息
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult setPaymentAssembly(SetPaymentAssemblyEnter enter) {

        //付款信息转换
        List<StagingPaymentEnter> paymentList = null;
        //支付条目
        List<OpeAssemblyOrderPayment> saveAssemblyOrderPayment = Lists.newArrayList();
        try {
            if (StringUtils.equals(PaymentTypeEnums.STAGING.getValue(), enter.getPaymentType())) {
                paymentList = JSONArray.parseArray(enter.getPaymentInfoList(), StagingPaymentEnter.class);
            }
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //组装单校验
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), null);
        if (opeAssemblyOrder.getTotalPrice() != null) {
            throw new SesWebRosException(ExceptionCodeEnums.DO_NOT_SET_THE_PRICE_REPEATEDLY.getCode(), ExceptionCodeEnums.DO_NOT_SET_THE_PRICE_REPEATEDLY.getMessage());
        }

        opeAssemblyOrder.setTotalPrice(enter.getTotalPrice());
        opeAssemblyOrder.setProcessingFee(opeAssemblyOrder.getProductPrice().multiply(enter.getProcessCost()).divide(new BigDecimal(100).setScale(4, BigDecimal.ROUND_HALF_UP)));
        opeAssemblyOrder.setProcessingFeeRatio(enter.getProcessCost());
        opeAssemblyOrder.setPaymentType(enter.getPaymentType());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //将 百分比缩小一百倍
        enter.setProcessCost(enter.getProcessCost().divide(new BigDecimal(100)));

        //支付条目数据封装
        buildPaymentItem(enter, paymentList, saveAssemblyOrderPayment, opeAssemblyOrder);
        //保存条目
        opeAssemblyOrderPaymentService.saveBatch(saveAssemblyOrderPayment);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 质检记录
     *
     * @param enter
     * @return
     */
    @Override
    public List<AssemblyQcResult> assemblyQcTrces(AssemblyQcEnter enter) {
        return null;
    }

    /**
     * 取消组装单
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult cancle(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.PENDING.getValue());

        //封装库存数据 进行数据回滚
        List<OpeStock> opeStockList = buildOpeStocks(enter, opeAssemblyOrder);
        //组装单表 数据更新
        opeAssemblyOrder.setStatus(AssemblyStatusEnums.CANCELLED.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //更新库存
        opeStockService.updateBatchById(opeStockList);

        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAssemblyOrder.getId());
        saveNodeEnter.setStatus(AssemblyStatusEnums.CANCELLED.getValue());
        saveNodeEnter.setEvent(AssemblyStatusEnums.CANCELLED.getValue());
        saveNodeEnter.setMemo(null);
        this.saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开始备料
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult startPrepare(StartPrepareEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.PENDING.getValue());

        opeAssemblyOrder.setStatus(AssemblyStatusEnums.PREPARE_MATERIAL.getValue());
        opeAssemblyOrder.setFactoryAnnex(enter.getAnnex());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAssemblyOrder.getId());
        saveNodeEnter.setStatus(AssemblyStatusEnums.PREPARE_MATERIAL.getValue());
        saveNodeEnter.setEvent(AssemblyStatusEnums.PREPARE_MATERIAL.getValue());
        saveNodeEnter.setMemo(null);
        this.saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开始组装
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult startAssembly(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.PREPARE_MATERIAL.getValue());

        opeAssemblyOrder.setStatus(AssemblyStatusEnums.ASSEMBLING.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAssemblyOrder.getId());
        saveNodeEnter.setStatus(AssemblyStatusEnums.ASSEMBLING.getValue());
        saveNodeEnter.setEvent(AssemblyStatusEnums.ASSEMBLING.getValue());
        saveNodeEnter.setMemo(null);
        this.saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 开始质检
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult startQc(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.ASSEMBLING.getValue());

        opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAssemblyOrder.getId());
        saveNodeEnter.setStatus(AssemblyStatusEnums.QC.getValue());
        saveNodeEnter.setEvent(AssemblyStatusEnums.QC.getValue());
        saveNodeEnter.setMemo(null);
        this.saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * Qc 完成
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult completeQc(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.QC.getValue());

        opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAssemblyOrder.getId());
        saveNodeEnter.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());
        saveNodeEnter.setEvent(AssemblyStatusEnums.QC_PASSED.getValue());
        saveNodeEnter.setMemo(null);
        this.saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 入库
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult inWh(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.QC_PASSED.getValue());
        //更新库存
        List<OpeStock> saveOpeStockList = Lists.newArrayList();
        //保存入库单
        List<OpeStockBill> saveStockBillList = Lists.newArrayList();
        //查询该组装单的产品
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_DR, 0);
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ASSEMBLY_ID, opeAssemblyOrder.getId());
        List<OpeAssemblyBOrder> assemblyBOrderList = opeAssemblyOrderBService.list(opeAssemblyBOrderQueryWrapper);

        Map<Long, Integer> productMap = Maps.newHashMap();
        assemblyBOrderList.forEach(item -> {
            productMap.put(item.getProductId(), item.getAssemblyQty());
        });

        //查询仓库
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ASSEMBLY.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        //查询商品信息
        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_DR, 0);
        opePartsProductQueryWrapper.in(OpePartsProduct.COL_ID, new ArrayList<>(productMap.keySet()));
        List<OpePartsProduct> partsProductList = opePartsProductService.list(opePartsProductQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        //查询库存
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, 0);
        opeStockQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, new ArrayList<>(productMap.keySet()));
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);

        for (Map.Entry<Long, Integer> entry : productMap.entrySet()) {
            Long key = entry.getKey();
            Integer value = entry.getValue();
            Boolean stokcExist = Boolean.FALSE;
            for (OpeStock item : opeStockList) {
                if (item.getMaterielProductId().equals(key)) {
                    item.setAvailableTotal(item.getAvailableTotal() + value);
                    item.setIntTotal(item.getIntTotal() + value);
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                    saveOpeStockList.add(item);
                    stokcExist = Boolean.FALSE;
                }
            }
            if (!stokcExist) {
                saveOpeStockList.add(
                        OpeStock.builder()
                                .id(idAppService.getId(SequenceName.OPE_STOCK))
                                .dr(0)
                                .userId(enter.getUserId())
                                .tenantId(0L)
                                .whseId(opeWhse.getId())
                                .intTotal(value)
                                .availableTotal(value)
                                .outTotal(0)
                                .wornTotal(0)
                                .materielProductId(key)
                                .materielProductName(null)
                                .materielProductType(BomCommonTypeEnums.SCOOTER.getValue())
                                .revision(0)
                                .updatedBy(enter.getUserId())
                                .updatedTime(new Date())
                                .createdBy(enter.getUserId())
                                .createdTime(new Date())
                                .build());
            }
        }

        for (OpeStock item : saveOpeStockList) {
            productMap.forEach((key, value) -> {
                //形成入库单
                saveStockBillList.add(buildStockBillRepeatedly(item.getId(), value, enter.getUserId(), enter.getId(), InOutWhEnums.IN.getValue()));
            });
            //完善产品名字
            if (StringUtils.isBlank(item.getMaterielProductName())) {

                for (OpePartsProduct product : partsProductList) {
                    if (item.getMaterielProductId().equals(product.getId())) {
                        item.setMaterielProductName(product.getCnName());
                        break;
                    }
                }
            }
        }

        // 库存更新
        opeStockService.saveOrUpdateBatch(saveOpeStockList);
        // 入库单 保存
        opeStockBillService.saveBatch(saveStockBillList);
        //订单数据更新
        opeAssemblyOrder.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);
        // 订单节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opeAssemblyOrder.getId());
        saveNodeEnter.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        saveNodeEnter.setEvent(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        saveNodeEnter.setMemo(null);
        this.saveNode(saveNodeEnter);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 保存节点
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult saveNode(SaveNodeEnter enter) {
        opeAssembiyOrderTraceService.save(OpeAssembiyOrderTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .userId(enter.getUserId())
                .opeAssembiyOrderId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 支付信息详情
     *
     * @param enter
     * @return
     */
    @Override
    public PaymentDetailResullt paymentDetail(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), null);

        //查询 支付信息
        List<PaymentItemDetailResult> paymentItemList = assemblyServiceMapper.paymentItemList(enter.getId());
        return PaymentDetailResullt.builder()
                .id(opeAssemblyOrder.getId())
                .totalPrice(opeAssemblyOrder.getTotalPrice())
                .status(opeAssemblyOrder.getStatus())
                .processCost(opeAssemblyOrder.getProcessingFee())
                .processCostRatio(opeAssemblyOrder.getProcessingFeeRatio())
                .paymentItemList(paymentItemList)
                .build();
    }

    /**
     * 支付
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult pay(PayEnter enter) {
        OpeAssemblyOrderPayment opeAssemblyOrderPayment = opeAssemblyOrderPaymentService.getById(enter.getId());
        if (opeAssemblyOrderPayment == null) {
            throw new SesWebRosException(ExceptionCodeEnums.OPEPURCHAS_PAYMENT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.OPEPURCHAS_PAYMENT_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(opeAssemblyOrderPayment.getPaymentStatus(), PayStatusEnums.PAID.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //验证是否该支付的是当前分期
        QueryWrapper<OpeAssemblyOrderPayment> opeAssemblyOrderPaymentQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderPaymentQueryWrapper.eq(OpeAssemblyOrderPayment.COL_OPE_ASSEMBLY_ORDER_ID, opeAssemblyOrderPayment.getOpeAssemblyOrderId());
        opeAssemblyOrderPaymentQueryWrapper.eq(OpeAssemblyOrderPayment.COL_DR, 0);
        opeAssemblyOrderPaymentQueryWrapper.eq(OpeAssemblyOrderPayment.COL_PAYMENT_STATUS, PayStatusEnums.UNPAID.getValue());
        opeAssemblyOrderPaymentQueryWrapper.lt(OpeAssemblyOrderPayment.COL_PAYMENT_PRIORITY, opeAssemblyOrderPayment.getPaymentPriority());

        if (opeAssemblyOrderPaymentService.count(opeAssemblyOrderPaymentQueryWrapper) > 0) {
            throw new SesWebRosException(ExceptionCodeEnums.PAY_IN_INSTALLMENTS.getCode(), (ExceptionCodeEnums.PAY_IN_INSTALLMENTS.getMessage()));
        }
        //支付金额过滤
        if (opeAssemblyOrderPayment.getAmount().subtract(enter.getAmount()).intValue() != 0) {
            throw new SesWebRosException(ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getCode(), (ExceptionCodeEnums.PAY_AMOUNT_IS_FALSE.getMessage()));
        }

        opeAssemblyOrderPayment.setPaymentStatus(PayStatusEnums.PAID.getValue());
        opeAssemblyOrderPayment.setInvoiceNum(enter.getInvoiceNum());
        opeAssemblyOrderPayment.setInvoicePicture(enter.getInvoicePicture());
        opeAssemblyOrderPayment.setPaymentTime(enter.getActualPaymentDate());
        opeAssemblyOrderPayment.setUpdatedBy(enter.getUserId());
        opeAssemblyOrderPayment.setUpdatedTime(new Date());
        opeAssemblyOrderPaymentService.updateById(opeAssemblyOrderPayment);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 组装单详情商品列表
     *
     * @param enter
     * @return
     */
    @Override
    public List<productItemResult> productItemList(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.productItemList(enter);
    }

    /**
     * 组装单校验
     *
     * @param id
     * @param status
     * @return
     */
    private OpeAssemblyOrder checkAssembly(Long id, String status) {
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(id);
        if (opeAssemblyOrder == null) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSEMBLY_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.isNotEmpty(status)) {
            if (!StringUtils.equals(status, opeAssemblyOrder.getStatus())) {
                throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
            }
        }
        return opeAssemblyOrder;
    }

    /**
     * 封装库存数据
     *
     * @param enter
     * @param opeAssemblyOrder
     * @return
     */
    private List<OpeStock> buildOpeStocks(IdEnter enter, OpeAssemblyOrder opeAssemblyOrder) {
        //查询组装单出库的部件
        QueryWrapper<OpeAssemblyOrderPart> opeAssemblyOrderPartQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderPartQueryWrapper.eq(OpeAssemblyOrderPart.COL_DR, 0);
        opeAssemblyOrderPartQueryWrapper.eq(OpeAssemblyOrderPart.COL_ASSEMBLY_ID, opeAssemblyOrder.getId());
        List<OpeAssemblyOrderPart> assemblyOrderPartList = opeAssemblyOrderPartService.list(opeAssemblyOrderPartQueryWrapper);

        List<Long> partIds = Lists.newArrayList();
        assemblyOrderPartList.forEach(item -> {
            partIds.add(item.getPartId());
        });

        //查询仓库
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, 0);
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, partIds);
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);

        if (CollectionUtils.isEmpty(opeStockList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }

        //回滚库存
        opeStockList.forEach(item -> {
            assemblyOrderPartList.forEach(part -> {
                if (item.getMaterielProductId().equals(part.getPartId())) {
                    item.setAvailableTotal(item.getAvailableTotal() + part.getTotalQty());
                    item.setOutTotal(item.getOutTotal() - part.getTotalQty());
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                }
            });
        });
        return opeStockList;
    }

    /**
     * 支付条目信息封装
     *
     * @param enter
     * @param paymentList
     * @param saveAssemblyOrderPayment
     * @param opeAssemblyOrder
     */
    private void buildPaymentItem(SetPaymentAssemblyEnter enter, List<StagingPaymentEnter> paymentList, List<OpeAssemblyOrderPayment> saveAssemblyOrderPayment, OpeAssemblyOrder opeAssemblyOrder) {
        QueryWrapper<OpeAssemblyOrderPart> opeAssemblyOrderPartQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderPartQueryWrapper.eq(OpeAssemblyOrderPart.COL_ASSEMBLY_ID, opeAssemblyOrder.getId());
        opeAssemblyOrderPartQueryWrapper.eq(OpeAssemblyOrderPart.COL_DR, 0);
        List<OpeAssemblyOrderPart> assemblyOrderPartList = opeAssemblyOrderPartService.list(opeAssemblyOrderPartQueryWrapper);

        List<Long> partIds = Lists.newArrayList();
        assemblyOrderPartList.forEach(item -> {
            partIds.add(item.getPartId());
        });
        //获取部品价格信息
        List<PartDetailDto> partDetailDtoList = assemblyServiceMapper.partDetailListByPartIds(partIds);
        if (CollectionUtils.isEmpty(partDetailDtoList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        //校验部品总价
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OpeAssemblyOrderPart item : assemblyOrderPartList) {
            for (PartDetailDto part : partDetailDtoList) {
                if (item.getPartId().equals(part.getPartId())) {
                    BigDecimal partPrice = part.getPrice().multiply(new BigDecimal(item.getTotalQty()));
                    totalPrice = totalPrice.add(partPrice);
                }
            }
        }

        if (StringUtils.equals(enter.getPaymentType(), PaymentTypeEnums.MONTHLY_PAY.getValue())) {
            //月结
            if (enter.getStatementdate() == null || enter.getDays() == 0 || enter.getDays() == null) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
            //生成支付节点
            saveAssemblyOrderPayment.add(
                    OpeAssemblyOrderPayment.builder()
                            .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_ORDER_PAYMENT))
                            .dr(0)
                            .tenantId(0L)
                            .userId(0L)
                            .opeAssemblyOrderId(opeAssemblyOrder.getId())
                            .paymentType(PaymentTypeEnums.MONTHLY_PAY.getValue())
                            .plannedPaymentTime(enter.getStatementdate())
                            .paymentDay(enter.getDays())
                            .paymentTime(null)
                            .paymentStatus(PayStatusEnums.UNPAID.getValue())
                            .paymentPriority(1)
                            .description(enter.getRemark())
                            .amount(enter.getTotalPrice())
                            .amountProportion(Constant.AMOUNTP_ROPORTION)
                            .invoiceNum(null)
                            .invoicePicture(null)
                            .revision(0)
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedBy(enter.getUserId())
                            .updatedTime(new Date())
                            .build()

            );
        }
        if (StringUtils.equals(enter.getPaymentType(), PaymentTypeEnums.STAGING.getValue())) {
            //分期
            //生成支付节点
            int totalRatio = 0;
            BigDecimal checkTotalPrice = BigDecimal.ZERO;
            for (int i = 0; i < paymentList.size(); i++) {
                saveAssemblyOrderPayment.add(
                        OpeAssemblyOrderPayment.builder()
                                .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_ORDER_PAYMENT))
                                .dr(0)
                                .tenantId(0L)
                                .userId(0L)
                                .opeAssemblyOrderId(opeAssemblyOrder.getId())
                                .paymentType(PaymentTypeEnums.STAGING.getValue())
                                .plannedPaymentTime(paymentList.get(i).getEstimatedPaymentDate())
                                .paymentDay(null)
                                .paymentTime(null)
                                .paymentStatus(PayStatusEnums.UNPAID.getValue())
                                .paymentPriority(i)
                                .description(paymentList.get(i).getRemark())
                                .amount(paymentList.get(i).getPrice())
                                .amountProportion(paymentList.get(i).getRatio())
                                .invoiceNum(null)
                                .invoicePicture(null)
                                .revision(0)
                                .createdBy(enter.getUserId())
                                .createdTime(new Date())
                                .updatedBy(enter.getUserId())
                                .updatedTime(new Date())
                                .build());
                totalRatio += paymentList.get(i).getRatio();
                checkTotalPrice = checkTotalPrice.add(paymentList.get(i).getPrice());
            }

            //百分比校验
            if (totalRatio != Constant.AMOUNTP_ROPORTION) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
            //金额校验
            if (!enter.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP).equals(checkTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
        }
        //校验 加工费和总价格
        BigDecimal processingFee = opeAssemblyOrder.getProductPrice().multiply(enter.getProcessCost());
        if (!processingFee.add(opeAssemblyOrder.getProductPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).equals(enter.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP))) {
            throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
        }
    }


    /**
     * 封装组装单数据
     *
     * @param enter
     * @param productList
     * @return
     */
    private OpeAssemblyOrder buildOpeAssemblyOrder(SaveAssemblyEnter enter, List<ProductionPartsEnter> productList, Long assemblyId, Map<Long, BigDecimal> productUnitPrice) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Long, BigDecimal> entry : productUnitPrice.entrySet()) {
            Long key = entry.getKey();
            BigDecimal value = entry.getValue();
            totalPrice = totalPrice.add(value);
        }

        OpeAssemblyOrder saveOpeAssemblyOrder = OpeAssemblyOrder.builder()
                .id(assemblyId)
                .dr(0)
                .userId(enter.getUserId())
                .tenantId(0L)
                .status(AssemblyStatusEnums.PENDING.getValue())
                .assemblyNumber("REDE" + RandomUtil.randomNumbers(7))
                .totalQty(productList.stream().mapToInt(ProductionPartsEnter::getQty).sum())
                .totalPrice(null)
                .processingFee(null)
                .processingFeeRatio(null)
                .paymentType(null)
                .productPrice(totalPrice.multiply(new BigDecimal(productList.stream().mapToInt(ProductionPartsEnter::getQty).sum())))
                .factoryId(enter.getFactoryId())
                .factoryAnnex(null)
                .consigneeId(enter.getConsigneeId())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
        return saveOpeAssemblyOrder;
    }

    /**
     * 获取每个产品的产品单价
     *
     * @param opePartsProductList
     * @param productBList
     * @param productUnitPrice
     */
    private void buildProductUnitPriceMap(List<OpePartsProduct> opePartsProductList, List<ProductionPartsEnter> productList, List<OpePartsProductB> productBList,
                                          Map<Long, Integer> partMap, Map<Long, BigDecimal> productUnitPrice) {
        //查询配件价格信息
        List<PartDetailDto> partDetailDtoList = assemblyServiceMapper.partDetailListByPartIds(new ArrayList<>(partMap.keySet()));
        if (CollectionUtils.isEmpty(partDetailDtoList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        List<Long> saveProductIds = new ArrayList<>();
        for (ProductionPartsEnter product : productList) {
            saveProductIds.add(product.getId());
        }
        opePartsProductList.removeIf(item -> !saveProductIds.contains(item.getId()));

        for (OpePartsProduct item : opePartsProductList) {
            int partQty = 0;
            BigDecimal totalPrice = BigDecimal.ZERO;

            flag2:
            for (OpePartsProductB part : productBList) {
                if (part.getPartsProductId().equals(item.getId())) {
                    partQty += part.getPartsQty();

                    flag3:
                    for (PartDetailDto price : partDetailDtoList) {
                        if (price.getPartId().equals(part.getPartsId())) {

                            totalPrice = totalPrice.add(price.getPrice().multiply(new BigDecimal(part.getPartsQty())));
                        }
                    }

                    if (partQty == item.getSumPartsQty()) {
                        break flag2;
                    }
                }

            }
            productUnitPrice.put(item.getId(), totalPrice);
        }
    }

    /**
     * 封装 组装单 配件统计表
     *
     * @param enter
     * @param saveOpeAssemblyOrderPartList
     * @param assemblyId
     * @param saveStockList
     * @param partMap
     */
    private void buildSaveOpeAssemblyOrderPartList(SaveAssemblyEnter enter, List<OpeAssemblyOrderPart> saveOpeAssemblyOrderPartList, Long assemblyId, List<OpeStock> saveStockList, Map<Long,
            Integer> partMap) {
        saveStockList.forEach(item -> {
            if (partMap.containsKey(item.getMaterielProductId())) {
                saveOpeAssemblyOrderPartList.add(
                        OpeAssemblyOrderPart.builder()
                                .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_ORDER_PART))
                                .dr(0)
                                .stockId(item.getId())
                                .partId(item.getMaterielProductId())
                                .assemblyId(assemblyId)
                                .totalQty(partMap.get(item.getMaterielProductId()))
                                .revision(0)
                                .createdBy(enter.getUserId())
                                .createdTime(new Date())
                                .updatedBy(enter.getUserId())
                                .updatedTime(new Date())
                                .build());
            }
        });
    }

    /**
     * 封装 组装单子表数据
     *
     * @param enter
     * @param saveOpeAssemblyBOrderList
     * @param assemblyId
     * @param productList
     * @param opePartsProductList
     * @param productUnitPrice
     */
    private void buildOpeAssemblyBOrderList(SaveAssemblyEnter enter, List<OpeAssemblyBOrder> saveOpeAssemblyBOrderList, Long assemblyId, List<ProductionPartsEnter> productList,
                                            List<OpePartsProduct> opePartsProductList, Map<Long, BigDecimal> productUnitPrice) {
        opePartsProductList.forEach(item -> {
            saveOpeAssemblyBOrderList.add(
                    OpeAssemblyBOrder.builder()
                            .id(idAppService.getId(SequenceName.OPE_ASSEMBLY_ORDER))
                            .dr(0)
                            .status(OpeAssemblyBStatusEnums.UNDONE.getValue())
                            .userId(enter.getUserId())
                            .tenantId(0L)
                            .assemblyId(assemblyId)
                            .productId(item.getId())
                            .assemblyBNumber("REDE" + RandomUtil.randomNumbers(6))
                            .productNumber(item.getProductNumber())
                            .enName(item.getEnName())
                            .price(null)
                            .completeQty(0)
                            .assemblyQty(0)
                            .revision(0)
                            .createdBy(enter.getUserId())
                            .createdTime(new Date())
                            .updatedBy(enter.getUserId())
                            .updatedTime(new Date())
                            .build()
            );
        });

        //子表完善总数量 、商品单价
        for (OpeAssemblyBOrder item : saveOpeAssemblyBOrderList) {
            for (ProductionPartsEnter product : productList) {
                if (item.getProductId().equals(product.getId())) {
                    item.setAssemblyQty(product.getQty());
                }
            }
        }
        saveOpeAssemblyBOrderList.forEach(item -> {
            if (productUnitPrice.containsKey(item.getProductId())) {
                item.setPrice(productUnitPrice.get(item.getProductId()));
            }
        });
    }

    /**
     * 入库单
     *
     * @param stockId
     * @param total
     * @param userId
     * @param bizId
     * @return
     */
    private OpeStockBill buildStockBillRepeatedly(Long stockId, Integer total, Long userId, Long bizId, String direction) {
        return OpeStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .tenantId(0L)
                .userId(userId)
                .stockId(stockId)
                .direction(direction)
                .status(StockBillStatusEnums.NORMAL.getValue())
                .sourceId(bizId)
                .total(total)
                .sourceType(SourceTypeEnums.ASSEMBLY.getValue())
                .principalId(userId)
                .operatineTime(new Date())
                .revision(0)
                .createdBy(userId)
                .createdTime(new Date())
                .updatedBy(userId)
                .updatedTime(new Date())
                .build();
    }
}
