package com.redescooter.ses.web.ros.service.production.purchasingWh.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.production.wh.PurchasingWhTypeEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.enums.website.ProductModelEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.production.PurchasingWhServiceMapper;
import com.redescooter.ses.web.ros.dm.*;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.*;
import com.redescooter.ses.web.ros.service.production.purchasingWh.PurchasingWhService;
import com.redescooter.ses.web.ros.vo.production.wh.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName:PurchasingWhServiceImpl
 * @description: PurchasingWhServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 14:16
 */
@Service
public class PurchasingWhServiceImpl implements PurchasingWhService {

    @Autowired
    private PurchasingWhServiceMapper purchasingWhServiceMapper;

    @Autowired
    private OpePartsProductBService opePartsProductBService;

    @Autowired
    private OpePartsProductService opePartsProductService;

    @Autowired
    private OpeStockService opeStockService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Autowired
    private OpeStockPurchasService opeStockPurchasService;

    @Autowired
    private OpePurchasService opePurchasService;

    /**
     * 类型统计
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> countByType(GeneralEnter enter) {
        Map<String, Integer> map = Maps.newHashMap();

        OpeWhse opeWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.PURCHAS.getValue())).get(0);
        QueryWrapper<OpeStock> availableWrapper = new QueryWrapper<>();
        availableWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        availableWrapper.gt(OpeStock.COL_AVAILABLE_TOTAL, 0);

        //采购仓库
        int availableCount = opeStockService.count(availableWrapper);
        map.put(PurchasingWhTypeEnums.AVAILABLE.getValue(), availableCount);

        //质检中
        int qcCount = purchasingWhServiceMapper.countByTypeQcCount(enter);
        map.put(PurchasingWhTypeEnums.PURCHASING.getValue(), qcCount);

        //待入库
        int tobeStoredCount = purchasingWhServiceMapper.countByTypetobeStoredCount(enter);
        map.put(PurchasingWhTypeEnums.TO_BE_STORED.getValue(), tobeStoredCount);

        List<OpeWhse> opeWhseList = checkWhse(Lists.newArrayList(WhseTypeEnums.ALLOCATE.getValue(), WhseTypeEnums.ASSEMBLY.getValue()));

        OpeWhse allocateWh = null;
        OpeWhse assembleyWh = null;

        for (OpeWhse whse : opeWhseList) {
            if (StringUtils.equals(whse.getType(), WhseTypeEnums.ALLOCATE.getValue())) {
                allocateWh = whse;
            }
            if (StringUtils.equals(whse.getType(), WhseTypeEnums.ASSEMBLY.getValue())) {
                assembleyWh = whse;
            }
        }

        //出库列表统计 （调拨入库、组装入库）
        int outWhCountAssembly = purchasingWhServiceMapper.countByTypeOutWhCountAssembly(enter, allocateWh.getId());
        int outWhCountAllocate = purchasingWhServiceMapper.countByTypeOutWhCountAllocate(enter, assembleyWh.getId());

        map.put(PurchasingWhTypeEnums.OUT_WH.getValue(), outWhCountAssembly + outWhCountAllocate);

        for (PurchasingWhTypeEnums item : PurchasingWhTypeEnums.values()) {
            if (!map.containsKey(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    /**
     * 类型 列表
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, String> productTypeList(GeneralEnter enter) {
        Map<String, String> map = Maps.newHashMap();
        for (BomCommonTypeEnums item : BomCommonTypeEnums.values()) {
            map.put(item.getValue(), item.getCode());
        }
        map.remove(BomCommonTypeEnums.COMBINATION.getValue());
        return map;
    }

    /**
     * 可用列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AvailableListResult> availableList(WhEnter enter) {
        if (enter.getKeyword() != null && enter.getKeyword().length() > 50) {
            return PageResult.createZeroRowResult(enter);
        }
        if (StringUtils.isNotEmpty(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }

        OpeWhse opeWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.PURCHAS.getValue())).get(0);

        int count = purchasingWhServiceMapper.availableListCount(enter, opeWhse.getId());
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AvailableListResult> availableList = purchasingWhServiceMapper.availableList(enter, opeWhse.getId());

        if (CollectionUtils.isNotEmpty(availableList)) {
            // 查询质检批次号
            List<OpeStockPurchas> opeStockPurchasList = opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>()
                    .eq(OpeStockPurchas::getStatus, StockProductPartStatusEnums.AVAILABLE.getValue())
                    .in(OpeStockPurchas::getPartId, availableList.stream().map(AvailableListResult::getId).collect(Collectors.toList())));

            //批次号集合
            Map<Long, List<AvailableListBatchNResult>> batchNMap = new HashMap<>();
            opeStockPurchasList.forEach(item -> {
                //已存在 该部件
                if (batchNMap.containsKey(item.getPartId())) {
                    //判断是否存在批次号
                    for (AvailableListBatchNResult batch : batchNMap.get(item.getPartId())) {
                        //存在批次号 数量累加
                        if (StringUtils.equals(batch.getBatchN(), item.getLot())) {
                            batch.setQty(batch.getQty() + 1);
                            break;
                        }
                    }
                } else {
                    batchNMap.put(item.getPartId(), Lists.newArrayList(AvailableListBatchNResult.builder()
                            .id(item.getPartId())
                            .qty(item.getInWhQty())
                            .batchN(item.getLot())
                            .build()));
                }
            });

            //封装数据返回
            availableList.forEach(item -> {
                if (batchNMap.containsKey(item.getId())) {
                    item.setBatchNList(batchNMap.get(item.getId()));
                }
            });
        }
        return PageResult.create(enter, count, availableList);
    }

    /**
     * 质检中列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<QcingListResult> qcingList(WhEnter enter) {
        if (StringUtils.isNotEmpty(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }
        int count = purchasingWhServiceMapper.qcingListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasingWhServiceMapper.qcingList(enter));
    }

    /**
     * 待入库列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<TobeStoredResult> tobeStoredList(WhEnter enter) {
        if (StringUtils.isNotEmpty(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }
        if (StringUtils.isBlank(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }
        int count = purchasingWhServiceMapper.tobeStoredListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasingWhServiceMapper.tobeStoredList(enter));
    }

    /**
     * 出库列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<OutWhResult> outWhList(WhEnter enter) {
        if (StringUtils.isNotBlank(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }
        List<OpeWhse> assemblyWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.ASSEMBLY.getValue()));

        //组装仓库 数据
        int countAssembly = purchasingWhServiceMapper.outWhListAssemblyCount(enter, Lists.newArrayList(assemblyWhse.get(0).getId()));

        List<OutWhResult> whResultListAssembly = purchasingWhServiceMapper.outWhListAssembly(enter, Lists.newArrayList(assemblyWhse.get(0).getId()));

        //调拨仓库数据
        List<OpeWhse> allocateWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.ALLOCATE.getValue()));

        int countAllocate = purchasingWhServiceMapper.outWhListAllocateCount(enter, Lists.newArrayList(allocateWhse.get(0).getId()));

        if (countAllocate == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OutWhResult> whResultListAllocate = purchasingWhServiceMapper.outWhListAllocate(enter, Lists.newArrayList(allocateWhse.get(0).getId()));
        whResultListAllocate.addAll(whResultListAssembly);

        return PageResult.create(enter, countAllocate + countAssembly, whResultListAllocate);
    }

    /**
     * 废料列表
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<WasteResult> wasteList(WhEnter enter) {
//        int count = purchasingWhServiceMapper.wasteListCount(enter);
//        if (count == 0) {
//            return PageResult.createZeroRowResult(enter);
//        }
//        return PageResult.create(enter, count, purchasingWhServiceMapper.wasteList(enter));
        return PageResult.createZeroRowResult(enter);
    }

    /**
     * 可组装的产品列表
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> canAssemblyProductList(GeneralEnter enter) {
        //反参对象
        Map<String, Integer> result = new HashMap<>();
        for (ProductModelEnums item : ProductModelEnums.values()) {
            result.put(item.getValue(), 0);
        }

        //查询出所有商品及bom 规则
        Map<Long, List<OpePartsProductB>> productPartMap = Maps.newHashMap();

        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        List<OpePartsProduct> partsProductList = opePartsProductService.list(opePartsProductQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductList)) {
            return result;
        }
        List<Long> productIds = partsProductList.stream().map(OpePartsProduct::getId).collect(Collectors.toList());

        //查询bom 规则
        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_PRODUCT_ID, productIds);
        List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductBList)) {
            return result;
        }
        //过滤出map 产品结构
        Set<Long> partIds = partsProductBList.stream().map(OpePartsProductB::getPartsId).collect(Collectors.toSet());
        productPartMap = partsProductBList.stream().collect(Collectors.groupingBy(OpePartsProductB::getPartsProductId));

        //查询采购仓库
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        //查询库存
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, new ArrayList<>(partIds));
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        List<OpeStock> stockList = opeStockService.list(opeStockQueryWrapper);
        if (CollectionUtils.isEmpty(stockList)) {
            return result;
        }

        // 确认可组装的产品的最大值
        Map<Long, Integer> canAssembledMap = Maps.newHashMap();

        flag1:
        for (Map.Entry<Long, List<OpePartsProductB>> entry : productPartMap.entrySet()) {
            Long key = entry.getKey();
            List<OpePartsProductB> value = entry.getValue();
            Integer maxTotal = 0;
            int partTotal = 0;
            if (productIds.contains(key)) {

                flag2:
                for (OpePartsProductB item : value) {

                    flag3:
                    for (OpeStock stock : stockList) {
                        if (item.getPartsId().equals(stock.getMaterielProductId()) && (item.getPartsQty() != null && item.getPartsQty() > 0)) {

                            int canAss = Long.valueOf(stock.getAvailableTotal() / item.getPartsQty()).intValue();
                            //计算可以组装的车辆最大值
                            if (canAss > 0) {
                                maxTotal += canAss;
                                continue flag2;
                            }
                        }
                    }
                }
            }
            if (maxTotal > 0) {
                canAssembledMap.put(key, maxTotal);
            }
        }

        if (canAssembledMap.isEmpty()) {
            return result;
        }
        for (Map.Entry<Long, Integer> entry : canAssembledMap.entrySet()) {
            Long key = entry.getKey();
            Integer value = entry.getValue();
            for (OpePartsProduct item : partsProductList) {
                if (key.equals(item.getId())) {
                    if (!result.containsKey(item.getModel())) {
                        //对于未完善车辆型号的产品直接跳过
                        if (StringUtils.isEmpty(item.getModel())) {
                            continue;
                        }
                        result.put(item.getModel(), value);
                    } else {
                        result.put(item.getModel(), result.get(item.getModel()) + value);
                    }
                }
            }
        }
        //根据产品型号分类
        return result;
    }

    private List<OpeWhse> checkWhse(List<String> types) {
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.in(OpeWhse.COL_TYPE, types);
        List<OpeWhse> opeWhseList = opeWhseService.list(opeWhseQueryWrapper);
        if (opeWhseList.size() != types.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        return opeWhseList;
    }
}
