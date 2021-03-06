package com.redescooter.ses.web.ros.service.production.assembly.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
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
import com.redescooter.ses.api.common.enums.production.assembly.AssemblyStatusEnums;
import com.redescooter.ses.api.common.enums.production.assembly.OpeAssemblyBStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PayStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.vo.CommonNodeResult;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.date.DateUtil;
import com.redescooter.ses.web.ros.constant.SequenceName;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.production.AssemblyServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeAssembiyOrderTrace;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBOrder;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBQc;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrder;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPart;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPayment;
import com.redescooter.ses.web.ros.dm.OpeAssemblyQcItem;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpeAssembiyOrderTraceService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyBOrderService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyBQcService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderPartService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderPaymentService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderService;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyQcItemService;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpeStockBillService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeSysUserProfileService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.production.assembly.AssemblyService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.bo.PartDetailDto;
import com.redescooter.ses.web.ros.vo.production.ConsigneeResult;
import com.redescooter.ses.web.ros.vo.production.FactoryCommonResult;
import com.redescooter.ses.web.ros.vo.production.PayEnter;
import com.redescooter.ses.web.ros.vo.production.PaymentDetailResullt;
import com.redescooter.ses.web.ros.vo.production.PaymentItemDetailResult;
import com.redescooter.ses.web.ros.vo.production.ProductionPartsEnter;
import com.redescooter.ses.web.ros.vo.production.StagingPaymentEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductEnter;
import com.redescooter.ses.web.ros.vo.production.allocate.SaveAssemblyProductResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyExportResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyListEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoItemEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcInfoResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemViewItemTemplateResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyQcItemViewResult;
import com.redescooter.ses.web.ros.vo.production.assembly.AssemblyResult;
import com.redescooter.ses.web.ros.vo.production.assembly.ProductAssemblyTraceItemResult;
import com.redescooter.ses.web.ros.vo.production.assembly.ProductAssemblyTraceResult;
import com.redescooter.ses.web.ros.vo.production.assembly.QcItemViewResult;
import com.redescooter.ses.web.ros.vo.production.assembly.SaveAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.SetPaymentAssemblyEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.StartPrepareEnter;
import com.redescooter.ses.web.ros.vo.production.assembly.productItemResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @ClassName:AssemblyServiceImpl
 * @description: AssemblyServiceImpl
 * @author: Alex
 * @Version???1.3
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

    @Autowired
    private OpeAssemblyBQcService opeAssemblyBQcService;

    @Autowired
    private OpeAssemblyQcItemService opeAssemblyQcItemService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private HttpServletResponse response;

    /**
     * ????????????
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
     * ????????????
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
     * ????????????
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
     * ??????????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<SaveAssemblyProductResult> queryAssemblyProduct(SaveAssemblyProductEnter enter) {
        List<SaveAssemblyProductResult> result = Lists.newArrayList();

        //?????? ??????bom??????
        Map<Long, List<OpePartsProductB>> productMap = Maps.newHashMap();

        List<OpePartsProduct> opePartsProduct = new ArrayList<>();
        //????????????
        List<OpeStock> opeStockList = new ArrayList<>();
        if (buildProductMap(productMap, opePartsProduct, opeStockList)) {
            return new ArrayList<>();
        }
        //????????????????????????Id
        List<Long> productIds = Lists.newArrayList();

        //?????????
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
                        Integer qty = product.getQty() == null ? 0 : product.getQty();

                        if (stock.getMaterielProductId().equals(item.getPartsId())) {
                            stock.setAvailableTotal(stock.getAvailableTotal() - item.getPartsQty() * qty);
                            if (stock.getAvailableTotal() < 0) {
                                throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                            }
                        }
                    });
                }
            });
        }

        //???????????????????????????
        flag1:
        for (Map.Entry<Long, List<OpePartsProductB>> e : productMap.entrySet()) {
            Long k = e.getKey();
            //??????????????? ??????????????????????????? ?????? ??????????????????
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

        // ????????????????????????????????????
        Map<Long, Integer> canAssembledMap = Maps.newHashMap();

        //????????????????????????0???????????????
        opeStockList.removeIf(item -> item.getAvailableTotal() == 0);

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
                            if (NumberUtil.eqZero(maxTotal)) {
                                maxTotal = canAss;
                            }
                            if (canAss < maxTotal) {
                                maxTotal = canAss;
                            }
                            if (0 != canAss) {
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
        //???????????????????????????
        result.removeIf(item -> item == null);


        //????????????????????????????????????
        for (SaveAssemblyProductResult item : result) {
            if (CollectionUtils.isNotEmpty(productList)) {
                for (ProductionPartsEnter product : productList) {
                    if (item.getId().equals(product.getId())) {
                        item.setSelectedQty(product.getQty() == null ? 0 : product.getQty());
                        break;
                    }
                }
            }
        }
        return result;
    }

    private Boolean buildProductMap(Map<Long, List<OpePartsProductB>> productMap, List<OpePartsProduct> opePartsProduct, List<OpeStock> opeStockList) {
        //????????????????????????
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

        //????????????????????????
        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_DR, Constant.DR_FALSE);
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

        //??????????????????
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, Constant.DR_FALSE);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, Constant.DR_FALSE);
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockList.addAll(opeStockService.list(opeStockQueryWrapper));
        return CollectionUtils.isEmpty(opeStockList);
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveAssembly(SaveAssemblyEnter enter) {
        // ?????????????????????
        List<OpeStockBill> saveOpeStockBillList = Lists.newArrayList();
        //???????????????
        List<OpeAssemblyBOrder> saveOpeAssemblyBOrderList = new ArrayList<OpeAssemblyBOrder>();
        //?????????
        OpeAssemblyOrder saveOpeAssemblyOrder = null;
        //????????????????????????
        List<OpeAssemblyOrderPart> saveOpeAssemblyOrderPartList = Lists.newArrayList();

        Long assemblyId = idAppService.getId(SequenceName.OPE_ASSEMBLY_ORDER);

        // ????????????
        List<OpeStock> saveStockList = Lists.newArrayList();

        //??????????????????
        List<ProductionPartsEnter> productList = null;
        try {
            productList = JSONArray.parseArray(enter.getProductList(), ProductionPartsEnter.class);
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }
        if (CollectionUtils.isEmpty(productList)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_PRODUCT_IS_EMPTY.getCode(),
                    ExceptionCodeEnums.ASSEMBLY_PRODUCT_IS_EMPTY.getMessage());
        }
        //???????????????????????????
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, Constant.DR_FALSE);
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_SYS_USER_ID, enter.getConsigneeId());
        opeSysUserProfileQueryWrapper.last("limit 1");
        if (StringManaConstant.entityIsNull(opeSysUserProfileService.getOne(opeSysUserProfileQueryWrapper))) {
            throw new SesWebRosException(ExceptionCodeEnums.USER_NOT_EXIST.getCode(), ExceptionCodeEnums.USER_NOT_EXIST.getMessage());
        }

        if (StringManaConstant.entityIsNull(opeFactoryService.getById(enter.getFactoryId()))) {
            throw new SesWebRosException(ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.FACTORY_IS_NOT_EXIST.getMessage());
        }

        // ????????????????????????????????????
        Map<Long, Integer> partMap = Maps.newHashMap();

        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_DR, Constant.DR_FALSE);
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        List<OpePartsProduct> opePartsProductList = opePartsProductService.list(opePartsProductQueryWrapper);
        if (CollectionUtils.isEmpty(opePartsProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        List<Long> productIds = new ArrayList<>();
        opePartsProductList.forEach(item -> {
            productIds.add(item.getId());
        });

        //????????????????????????
        productList.forEach(item -> {
            if (!productIds.contains(item.getId())) {
                throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
            }
        });

        //?????????????????????bom ??????
        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.eq(OpePartsProductB.COL_DR, Constant.DR_FALSE);
        opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_PRODUCT_ID, productIds);
        List<OpePartsProductB> productBList = opePartsProductBService.list(opePartsProductBQueryWrapper);
        if (CollectionUtils.isEmpty(productBList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }

        // ?????? ???????????????????????? ??????part????????????
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
        //?????? ???????????????????????????


        //????????????
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, Constant.DR_FALSE);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        // ??????????????????
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, Constant.DR_FALSE);
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, new ArrayList<>(partMap.keySet()));
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);
        if (CollectionUtils.isEmpty(opeStockList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }
        // ???????????????????????? ?????? ?????????????????????
        partMap.forEach((key, value) -> {
            opeStockList.forEach(item -> {
                if (key.equals(item.getMaterielProductId())) {
                    if (value > item.getAvailableTotal()) {
                        throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_SHORTAGE.getCode(), ExceptionCodeEnums.STOCK_IS_SHORTAGE.getMessage());
                    }
                    //???????????????
                    saveOpeStockBillList.add(buildStockBillRepeatedly(item.getId(), value, enter.getUserId(), assemblyId, InOutWhEnums.OUT.getValue()));

                    // ?????????
                    item.setAvailableTotal(item.getAvailableTotal() - value);
                    item.setOutTotal(item.getOutTotal() + value);
                    item.setUpdatedBy(enter.getUserId());
                    item.setUpdatedTime(new Date());
                    saveStockList.add(item);
                }
            });
        });


        //???????????????????????????????????? ???????????????Map
        Map<Long, BigDecimal> productUnitPrice = Maps.newHashMap();
        buildProductUnitPriceMap(opePartsProductList, productList, productBList, partMap, productUnitPrice);

        //?????? ????????????
        buildOpeAssemblyBOrderList(enter, saveOpeAssemblyBOrderList, assemblyId, productList, opePartsProductList, productUnitPrice);

        //?????? ?????????
        saveOpeAssemblyOrder = buildOpeAssemblyOrder(enter, productList, assemblyId, productUnitPrice);

        //??????????????? ???????????????
        buildSaveOpeAssemblyOrderPartList(enter, saveOpeAssemblyOrderPartList, assemblyId, saveStockList, partMap);

        //???????????????
        opeStockBillService.saveBatch(saveOpeStockBillList);

        //???????????????
        opeAssemblyOrderService.save(saveOpeAssemblyOrder);

        //?????????????????????
        opeAssemblyOrderBService.saveBatch(saveOpeAssemblyBOrderList);

        // ??????????????? ???????????????
        opeAssemblyOrderPartService.saveBatch(saveOpeAssemblyOrderPartList);

        //????????????
        opeStockService.updateBatchById(saveStockList);
        //????????????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<FactoryCommonResult> factoryList(GeneralEnter enter) {
        List<FactoryCommonResult> result = new ArrayList<>();
        QueryWrapper<OpeFactory> opeFactoryQueryWrapper = new QueryWrapper();
        opeFactoryQueryWrapper.eq(OpeFactory.COL_DR, Constant.DR_FALSE);
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
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<ConsigneeResult> consigneeList(GeneralEnter enter) {
        List<ConsigneeResult> consigneeResultlist = new ArrayList<>();
        QueryWrapper<OpeSysUserProfile> opeSysUserProfileQueryWrapper = new QueryWrapper<>();
        opeSysUserProfileQueryWrapper.eq(OpeSysUserProfile.COL_DR, Constant.DR_FALSE);
        opeSysUserProfileQueryWrapper.ne(OpeSysUserProfile.COL_FIRST_NAME, Constant.ADMIN_USER_NAME);
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

    @Override
    public PageResult<AssemblyResult> ordinaryList(AssemblyListEnter enter) {
        //???type ???????????? ??????statusList
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
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }

        List<AssemblyResult> assemblyResultList = assemblyServiceMapper.ordinaryList(enter, statusList);
        if (CollectionUtils.isEmpty(assemblyResultList)) {
            return PageResult.createZeroRowResult(enter);
        }

        return PageResult.create(enter, count, assemblyResultList);
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AssemblyResult> list(AssemblyListEnter enter) {
        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return PageResult.createZeroRowResult(enter);
        }
        //???type ???????????? ??????statusList
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
        if (NumberUtil.eqZero(count)) {
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

        //??????????????????
        List<PaymentItemDetailResult> opeAssemblyOrderPaymentList = assemblyServiceMapper.paymentItemDetailListByAssIds(assemblyIds);

        for (AssemblyResult item : assemblyResultList) {
            //???????????? ??????
            if (StringUtils.equals(item.getPaymentType(), PaymentTypeEnums.MONTHLY_PAY.getValue())) {
                for (PaymentItemDetailResult payment : opeAssemblyOrderPaymentList) {
                    if (item.getId().equals(payment.getBizId())) {
                        item.setStatementDate(payment.getEstimatedPaymentDate());
                        item.setDays(payment.getDayNum());
                    }
                }

            } else {
                //??????????????????
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
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    public AssemblyResult detail(IdEnter enter) {
        //???????????????
        checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.detail(enter);
    }

    @Override
    public AssemblyResult ordinaryDetail(IdEnter enter) {
        //???????????????
        checkAssembly(enter.getId(), null);
        AssemblyResult detail = assemblyServiceMapper.detail(enter);
        detail.setTotalPrice(null);
        detail.setStagTotal(null);
        detail.setProductPrice(null);
        detail.setProcessCost(null);
        detail.setProcessCostRatio(null);
        detail.setPaymentItemDetailResultList(null);
        return detail;
    }


    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<CommonNodeResult> assemblyNode(IdEnter enter) {
        checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.assemblyNode(enter);
    }

    @Override
    public List<CommonNodeResult> ordinaryAssemblyNode(IdEnter enter) {
        checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.ordinaryAssemblyNode(enter);
    }


    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> qcResultList(GeneralEnter enter) {
        Map<String, String> map = new HashMap<>();
        for (QcStatusEnums item : QcStatusEnums.values()) {
            map.put(item.getCode(), item.getValue());
        }
        map.remove(QcStatusEnums.QUALITY_INSPECTION);
        return map;
    }

    /**
     * ?????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public void export(IdEnter enter) {
        AssemblyResult assemblyResult = assemblyServiceMapper.detail(enter);
        if (StringManaConstant.entityIsNull(assemblyResult)) {
            throw new SesWebRosException(0, "");
        }
        AssemblyExportResult assemblyExportResult = new AssemblyExportResult();
        BeanUtil.copyProperties(assemblyResult, assemblyExportResult);
        if (StringManaConstant.entityIsNotNull(assemblyResult.getCreatedTime())) {
            assemblyExportResult.setCreatedTime(DateUtil.getYmdhm(assemblyResult.getCreatedTime()));
        } else {
            assemblyExportResult.setCreatedTime("--");
        }
        if (StringManaConstant.entityIsNotNull(assemblyResult.getStatementDate())) {
            assemblyExportResult.setStatementDate(DateUtil.getYmdhm(assemblyResult.getStatementDate()));
        } else {
            assemblyExportResult.setStatementDate("--");
        }
        List<AssemblyExportResult> list = new ArrayList<>();
        list.add(assemblyExportResult);
        try {
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
            ExportParams exportParams = new ExportParams();
            exportParams.setSheetName("???????????????");
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, AssemblyExportResult.class, list);
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            System.out.println("+++++++++++++++++++");
        }
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult setPaymentAssembly(SetPaymentAssemblyEnter enter) {

        //??????????????????
        List<StagingPaymentEnter> paymentList = null;
        //????????????
        List<OpeAssemblyOrderPayment> saveAssemblyOrderPayment = Lists.newArrayList();
        try {
            if (StringUtils.equals(PaymentTypeEnums.STAGING.getValue(), enter.getPaymentType())) {
                paymentList = JSONArray.parseArray(enter.getPaymentInfoList(), StagingPaymentEnter.class);
            }
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
        }

        //???????????????
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), null);
        if (StringManaConstant.entityIsNotNull(opeAssemblyOrder.getTotalPrice())) {
            throw new SesWebRosException(ExceptionCodeEnums.DO_NOT_SET_THE_PRICE_REPEATEDLY.getCode(), ExceptionCodeEnums.DO_NOT_SET_THE_PRICE_REPEATEDLY.getMessage());
        }

        opeAssemblyOrder.setTotalPrice(enter.getTotalPrice());
        opeAssemblyOrder.setProcessingFee(opeAssemblyOrder.getProductPrice().multiply(enter.getProcessCost()).divide(new BigDecimal(100).setScale(4, BigDecimal.ROUND_HALF_UP)));
        opeAssemblyOrder.setProcessingFeeRatio(enter.getProcessCost());
        opeAssemblyOrder.setPaymentType(enter.getPaymentType());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //??? ????????????????????????
        enter.setProcessCost(enter.getProcessCost().divide(new BigDecimal(100)));

        //????????????????????????
        buildPaymentItem(enter, paymentList, saveAssemblyOrderPayment, opeAssemblyOrder);
        //????????????
        opeAssemblyOrderPaymentService.saveBatch(saveAssemblyOrderPayment);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<AssemblyQcInfoResult> assemblyQcInfo(AssemblyQcInfoEnter enter) {
        //???????????????Id
        checkAssembly(enter.getId(), null);
        //???????????????????????????
//        int count = assemblyServiceMapper.assemblyQcInfoCount(enter);
//        if (count == 0) {
//            return new ;
//        }

        //????????? ???????????????
        List<AssemblyQcInfoResult> assemblyQcInfoResultList = assemblyServiceMapper.assemblyQcInfoList(enter);
        if (CollectionUtils.isEmpty(assemblyQcInfoResultList)) {
            return new ArrayList<>();
        }

        //????????????
        List<AssemblyQcItemResult> assemblyQcItemResultList = assemblyServiceMapper.assemblyQcInfoItemByIds(enter,
                assemblyQcInfoResultList.stream().map(AssemblyQcInfoResult::getId).collect(Collectors.toList()));

        //????????????????????????Boolean ??????
        if (CollectionUtils.isNotEmpty(assemblyQcItemResultList)) {
            List<Long> existIds = Lists.newArrayList();
            for (AssemblyQcInfoResult result : assemblyQcInfoResultList) {
                for (AssemblyQcItemResult item : assemblyQcItemResultList) {
                    if (existIds.contains(result.getId())) {
                        break;
                    } else {
                        if (item.getAssemblyBQcId().equals(result.getId())) {
                            result.setHasChildDate(Boolean.TRUE);
                            existIds.add(item.getAssemblyBQcId());
                            break;
                        }
                    }
                }
            }
        } else {
            for (AssemblyQcInfoResult result : assemblyQcInfoResultList) {
                result.setHasChildDate(Boolean.FALSE);
            }
        }
        return assemblyQcInfoResultList;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<AssemblyQcItemResult> assemblyQcInfoItem(AssemblyQcInfoItemEnter enter) {
        //??????????????????
        OpeAssemblyBQc opeAssemblyBQc = opeAssemblyBQcService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeAssemblyBQc)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_B_QC_RESULT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSEMBLY_B_QC_RESULT_IS_NOT_EXIST.getMessage());
        }
        return assemblyServiceMapper.assemblyQcInfoItem(enter);
    }

    /**
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public AssemblyQcItemViewResult assemblyQcItemView(IdEnter enter) {
        // ????????????????????????
        AssemblyQcItemViewResult result = assemblyServiceMapper.assemblyQcItemView(enter);
        if (StringManaConstant.entityIsNull(result)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_QC_ITEM_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSEMBLY_QC_ITEM_IS_NOT_EXIST.getMessage());
        }
        //??????????????????????????????
        List<QcItemViewResult> qcItemViewResultList = assemblyServiceMapper.qcItemViewResult(result.getSerialN());
        if (CollectionUtils.isEmpty(qcItemViewResultList)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_QC_RESULT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSEMBLY_QC_RESULT_IS_NOT_EXIST.getMessage());
        }
        result.setQcItemViewResultList(qcItemViewResultList);
        return result;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<AssemblyQcItemViewItemTemplateResult> viewItemTemplate(IdEnter enter) {
        OpeAssemblyQcItem assemblyQcItem = opeAssemblyQcItemService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(assemblyQcItem)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_QC_ITEM_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSEMBLY_QC_ITEM_IS_NOT_EXIST.getMessage());
        }
        return assemblyServiceMapper.viewItemTemplate(enter.getId());
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult cancle(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.PENDING.getValue());

        //?????????????????? ??????????????????
        List<OpeStock> opeStockList = buildOpeStocks(enter, opeAssemblyOrder);
        //???????????? ????????????
        opeAssemblyOrder.setStatus(AssemblyStatusEnums.CANCELLED.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //????????????
        opeStockService.updateBatchById(opeStockList);

        //??????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startPrepare(StartPrepareEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.PENDING.getValue());

        opeAssemblyOrder.setStatus(AssemblyStatusEnums.PREPARE_MATERIAL.getValue());
        opeAssemblyOrder.setFactoryAnnex(enter.getAnnex());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //??????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startAssembly(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.PREPARE_MATERIAL.getValue());

        opeAssemblyOrder.setStatus(AssemblyStatusEnums.ASSEMBLING.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //??????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startQc(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.ASSEMBLING.getValue());

        opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //??????
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
     * Qc ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult completeQc(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.QC.getValue());

        opeAssemblyOrder.setStatus(AssemblyStatusEnums.QC_PASSED.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);

        //??????
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
     * ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult inWh(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), AssemblyStatusEnums.QC_PASSED.getValue());
        //????????????
        List<OpeStock> saveOpeStockList = Lists.newArrayList();
        //???????????????
        List<OpeStockBill> saveStockBillList = Lists.newArrayList();
        //???????????????????????????
        QueryWrapper<OpeAssemblyBOrder> opeAssemblyBOrderQueryWrapper = new QueryWrapper<>();
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_DR, Constant.DR_FALSE);
        opeAssemblyBOrderQueryWrapper.eq(OpeAssemblyBOrder.COL_ASSEMBLY_ID, opeAssemblyOrder.getId());
        List<OpeAssemblyBOrder> assemblyBOrderList = opeAssemblyOrderBService.list(opeAssemblyBOrderQueryWrapper);

        Map<Long, Integer> productMap = Maps.newHashMap();
        assemblyBOrderList.forEach(item -> {
            productMap.put(item.getProductId(), item.getAssemblyQty());
        });

        //????????????
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, Constant.DR_FALSE);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ASSEMBLY.getValue());
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        //??????????????????
        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_DR, Constant.DR_FALSE);
        opePartsProductQueryWrapper.in(OpePartsProduct.COL_ID, new ArrayList<>(productMap.keySet()));
        List<OpePartsProduct> partsProductList = opePartsProductService.list(opePartsProductQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PRODUCT_IS_NOT_EXIST.getMessage());
        }

        //????????????
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, Constant.DR_FALSE);
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
                //???????????????
                saveStockBillList.add(buildStockBillRepeatedly(item.getId(), value, enter.getUserId(), enter.getId(), InOutWhEnums.IN.getValue()));
            });
            //??????????????????
            if (StringUtils.isBlank(item.getMaterielProductName())) {

                for (OpePartsProduct product : partsProductList) {
                    if (item.getMaterielProductId().equals(product.getId())) {
                        item.setMaterielProductName(product.getCnName());
                        break;
                    }
                }
            }
        }

        // ????????????
        opeStockService.saveOrUpdateBatch(saveOpeStockList);
        // ????????? ??????
        opeStockBillService.saveBatch(saveStockBillList);
        //??????????????????
        opeAssemblyOrder.setStatus(AssemblyStatusEnums.IN_PRODUCTION_WH.getValue());
        opeAssemblyOrder.setUpdatedBy(enter.getUserId());
        opeAssemblyOrder.setUpdatedTime(new Date());
        opeAssemblyOrderService.updateById(opeAssemblyOrder);
        // ????????????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
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
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PaymentDetailResullt paymentDetail(IdEnter enter) {
        OpeAssemblyOrder opeAssemblyOrder = checkAssembly(enter.getId(), null);

        //?????? ????????????
        List<PaymentItemDetailResult> paymentItemList = assemblyServiceMapper.paymentItemList(enter.getId());
        if (CollectionUtils.isNotEmpty(paymentItemList)) {
            for (PaymentItemDetailResult result : paymentItemList) {
                if (StringManaConstant.entityIsNotNull(result.getPlannedPaymentTime())) {
                    String date = DateUtil.getTimeStr(result.getPlannedPaymentTime(), "yyyy-MM-dd");
                    result.setYear(date.split("-")[0]);
                    try {
                        result.setMonth(DateUtil.numMonToEngMon(date.split("-")[1]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    result.setDay(date.split("-")[2]);
                }
            }
        }
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
     * ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult pay(PayEnter enter) {
        OpeAssemblyOrderPayment opeAssemblyOrderPayment = opeAssemblyOrderPaymentService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(opeAssemblyOrderPayment)) {
            throw new SesWebRosException(ExceptionCodeEnums.OPEPURCHAS_PAYMENT_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.OPEPURCHAS_PAYMENT_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(opeAssemblyOrderPayment.getPaymentStatus(), PayStatusEnums.PAID.getValue())) {
            throw new SesWebRosException(ExceptionCodeEnums.STATUS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_ILLEGAL.getMessage());
        }
        //???????????????????????????????????????
        QueryWrapper<OpeAssemblyOrderPayment> opeAssemblyOrderPaymentQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderPaymentQueryWrapper.eq(OpeAssemblyOrderPayment.COL_OPE_ASSEMBLY_ORDER_ID, opeAssemblyOrderPayment.getOpeAssemblyOrderId());
        opeAssemblyOrderPaymentQueryWrapper.eq(OpeAssemblyOrderPayment.COL_DR, Constant.DR_FALSE);
        opeAssemblyOrderPaymentQueryWrapper.eq(OpeAssemblyOrderPayment.COL_PAYMENT_STATUS, PayStatusEnums.UNPAID.getValue());
        opeAssemblyOrderPaymentQueryWrapper.lt(OpeAssemblyOrderPayment.COL_PAYMENT_PRIORITY, opeAssemblyOrderPayment.getPaymentPriority());

        if (0 < opeAssemblyOrderPaymentService.count(opeAssemblyOrderPaymentQueryWrapper)) {
            throw new SesWebRosException(ExceptionCodeEnums.PAY_IN_INSTALLMENTS.getCode(), (ExceptionCodeEnums.PAY_IN_INSTALLMENTS.getMessage()));
        }
        //??????????????????
        if (0 != opeAssemblyOrderPayment.getAmount().subtract(enter.getAmount()).intValue()) {
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
     * ???????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<productItemResult> productItemList(IdEnter enter) {
        checkAssembly(enter.getId(), null);
        return assemblyServiceMapper.productItemList(enter);
    }

    /**
     * ???????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<productItemResult> ordinaryProductItemList(IdEnter enter) {
        checkAssembly(enter.getId(), null);
        List<productItemResult> list = assemblyServiceMapper.productItemList(enter);

        if (CollectionUtil.isEmpty(list)) {
            list.forEach(productItemResult -> {
                productItemResult.setPrice(null);
            });
        }
        return list;
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductAssemblyTraceResult> productAssemblyTrace(IdEnter enter) {
        //???????????????
        checkAssembly(enter.getId(), null);
        List<ProductAssemblyTraceResult> productAssemblyTraceResults = assemblyServiceMapper.productAssemblyTrace(enter);
        if (CollectionUtils.isNotEmpty(productAssemblyTraceResults)) {
            //??????????????????
            List<ProductAssemblyTraceItemResult> productAssemblyTraceItemResultList =
                    assemblyServiceMapper.productAssemblyItemTraceByIds(productAssemblyTraceResults.stream().map(ProductAssemblyTraceResult::getId).collect(Collectors.toList()));
            //????????????????????????????????? ??????????????????
            for (ProductAssemblyTraceResult item : productAssemblyTraceResults) {
                int count = 0;
                for (ProductAssemblyTraceItemResult trace : productAssemblyTraceItemResultList) {
                    if (item.getId().equals(trace.getBorderId())) {
                        if (count > 1) {
                            item.setAssemblyFlag(Boolean.TRUE);
                            continue;
                        }
                        item.setAssemblyDate(trace.getAssemblyDate());
                    }
                }
            }
        }
        return productAssemblyTraceResults;
    }

    /**
     * ??????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public List<ProductAssemblyTraceItemResult> productAssemblyTraceItem(IdEnter enter) {
        OpeAssemblyBOrder assemblyBOrder = opeAssemblyOrderBService.getById(enter.getId());
        if (StringManaConstant.entityIsNull(assemblyBOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSEMBLY_IS_NOT_EXIST.getMessage());
        }
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(assemblyBOrder.getAssemblyId());
        if (StringManaConstant.entityIsNull(opeAssemblyOrder)) {
            throw new SesWebRosException(ExceptionCodeEnums.ASSEMBLY_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.ASSEMBLY_IS_NOT_EXIST.getMessage());
        }
        List<ProductAssemblyTraceItemResult> productAssemblyTraceItemResultList = assemblyServiceMapper.productAssemblyItemTrace(enter);
        return productAssemblyTraceItemResultList.stream().filter(item -> {
            item.setAssemblyTotal(assemblyBOrder.getAssemblyQty());
            return true;
        }).collect(Collectors.toList());
    }

    /**
     * ???????????????
     *
     * @param id
     * @param status
     * @return
     */
    private OpeAssemblyOrder checkAssembly(Long id, String status) {
        OpeAssemblyOrder opeAssemblyOrder = opeAssemblyOrderService.getById(id);
        if (StringManaConstant.entityIsNull(opeAssemblyOrder)) {
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
     * ??????????????????
     *
     * @param enter
     * @param opeAssemblyOrder
     * @return
     */
    private List<OpeStock> buildOpeStocks(IdEnter enter, OpeAssemblyOrder opeAssemblyOrder) {
        //??????????????????????????????
        QueryWrapper<OpeAssemblyOrderPart> opeAssemblyOrderPartQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderPartQueryWrapper.eq(OpeAssemblyOrderPart.COL_DR, Constant.DR_FALSE);
        opeAssemblyOrderPartQueryWrapper.eq(OpeAssemblyOrderPart.COL_ASSEMBLY_ID, opeAssemblyOrder.getId());
        List<OpeAssemblyOrderPart> assemblyOrderPartList = opeAssemblyOrderPartService.list(opeAssemblyOrderPartQueryWrapper);

        List<Long> partIds = Lists.newArrayList();
        assemblyOrderPartList.forEach(item -> {
            partIds.add(item.getPartId());
        });

        //????????????
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, Constant.DR_FALSE);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.ALLOCATE.getValue());
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, Constant.DR_FALSE);
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, partIds);
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);

        if (CollectionUtils.isEmpty(opeStockList)) {
            throw new SesWebRosException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
        }

        //????????????
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
     * ????????????????????????
     *
     * @param enter
     * @param paymentList
     * @param saveAssemblyOrderPayment
     * @param opeAssemblyOrder
     */
    private void buildPaymentItem(SetPaymentAssemblyEnter enter, List<StagingPaymentEnter> paymentList, List<OpeAssemblyOrderPayment> saveAssemblyOrderPayment, OpeAssemblyOrder opeAssemblyOrder) {
        QueryWrapper<OpeAssemblyOrderPart> opeAssemblyOrderPartQueryWrapper = new QueryWrapper<>();
        opeAssemblyOrderPartQueryWrapper.eq(OpeAssemblyOrderPart.COL_ASSEMBLY_ID, opeAssemblyOrder.getId());
        opeAssemblyOrderPartQueryWrapper.eq(OpeAssemblyOrderPart.COL_DR, Constant.DR_FALSE);
        List<OpeAssemblyOrderPart> assemblyOrderPartList = opeAssemblyOrderPartService.list(opeAssemblyOrderPartQueryWrapper);

        List<Long> partIds = Lists.newArrayList();
        assemblyOrderPartList.forEach(item -> {
            partIds.add(item.getPartId());
        });
        //????????????????????????
        List<PartDetailDto> partDetailDtoList = assemblyServiceMapper.partDetailListByPartIds(partIds);
        if (CollectionUtils.isEmpty(partDetailDtoList)) {
            throw new SesWebRosException(ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        }
        //??????????????????
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
            //??????
            if (StringManaConstant.entityIsNull(enter.getStatementdate()) || 0 == enter.getDays() || StringManaConstant.entityIsNull(enter.getDays())) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
            //??????????????????
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
            //??????
            //??????????????????
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

            //???????????????
            if (totalRatio != Constant.AMOUNTP_ROPORTION) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
            //????????????
            if (!enter.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP).equals(checkTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP))) {
                throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
            }
        }
        //?????? ?????????????????????
        BigDecimal processingFee = opeAssemblyOrder.getProductPrice().multiply(enter.getProcessCost());
        if (!processingFee.add(opeAssemblyOrder.getProductPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).equals(enter.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP))) {
            throw new SesWebRosException(ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getCode(), ExceptionCodeEnums.PAYMENT_INFO_IS_WRONG.getMessage());
        }
    }


    /**
     * ?????????????????????
     *
     * @param enter
     * @param productList
     * @return
     */
    private OpeAssemblyOrder buildOpeAssemblyOrder(SaveAssemblyEnter enter, List<ProductionPartsEnter> productList, Long assemblyId, Map<Long, BigDecimal> productUnitPrice) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        int productTotal = 0;
//        for (Map.Entry<Long, BigDecimal> entry : productUnitPrice.entrySet()) {
//            Long key = entry.getKey();
//            BigDecimal value = entry.getValue();
//            productList.forEach(item->{
//            });
//            totalPrice = totalPrice.add(value);
//        }

        for (ProductionPartsEnter item : productList) {
            if (productUnitPrice.containsKey(item.getId())) {
                totalPrice = totalPrice.add(productUnitPrice.get(item.getId()).multiply(new BigDecimal(item.getQty())));
                productTotal += item.getQty();
            }
        }

        OpeAssemblyOrder saveOpeAssemblyOrder = OpeAssemblyOrder.builder()
                .id(assemblyId)
                .dr(0)
                .userId(enter.getUserId())
                .tenantId(0L)
                .status(AssemblyStatusEnums.PENDING.getValue())
                .assemblyNumber("RED" + RandomUtil.randomNumbers(7))
                .totalQty(productTotal)
                .waitPreparationTotal(productTotal)
                .waitAssemblyTotal(productTotal)
                .laveWaitQcTotal(productTotal)
                .inWaitWhTotal(productTotal)
                .totalPrice(null)
                .processingFee(null)
                .processingFeeRatio(null)
                .paymentType(null)
                .productPrice(totalPrice)
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
     * ?????????????????????????????????
     *
     * @param opePartsProductList
     * @param productBList
     * @param productUnitPrice
     */
    private void buildProductUnitPriceMap(List<OpePartsProduct> opePartsProductList, List<ProductionPartsEnter> productList, List<OpePartsProductB> productBList,
                                          Map<Long, Integer> partMap, Map<Long, BigDecimal> productUnitPrice) {
        //????????????????????????
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
     * ?????? ????????? ???????????????
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
                                .waitPreparationQty(partMap.get(item.getMaterielProductId()))
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
     * ?????? ?????????????????????
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
                            .assemblybNumber("REDE" + RandomUtil.randomNumbers(6))
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

        //????????????????????? ???????????????
        for (OpeAssemblyBOrder item : saveOpeAssemblyBOrderList) {
            for (ProductionPartsEnter product : productList) {
                if (item.getProductId().equals(product.getId())) {
                    item.setAssemblyQty(product.getQty());
                    item.setWaitAssemblyQty(product.getQty());
                    item.setLaveWaitQcQty(product.getQty());
                    item.setInWaitWhQty(product.getQty());
                }
            }
        }
        saveOpeAssemblyBOrderList.forEach(item -> {
            if (productUnitPrice.containsKey(item.getProductId())) {
                item.setAssemblybNumber(item.getAssemblybNumber() + new Random().nextInt(100));
                item.setPrice(productUnitPrice.get(item.getProductId()));
            }
        });
    }

    /**
     * ?????????
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
