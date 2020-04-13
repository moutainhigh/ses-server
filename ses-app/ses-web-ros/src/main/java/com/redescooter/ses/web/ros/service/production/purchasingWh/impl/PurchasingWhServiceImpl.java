package com.redescooter.ses.web.ros.service.production.purchasingWh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.wh.PurchasingWhTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.production.PurchasingWhServiceMapper;
import com.redescooter.ses.web.ros.dm.OpePartsProduct;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.dm.OpePurchasBQc;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpePurchasService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.production.purchasingWh.PurchasingWhService;
import com.redescooter.ses.web.ros.vo.production.wh.AssemblyProductResult;
import com.redescooter.ses.web.ros.vo.production.wh.AvailableListBatchNResult;
import com.redescooter.ses.web.ros.vo.production.wh.AvailableListResult;
import com.redescooter.ses.web.ros.vo.production.wh.OutWhResult;
import com.redescooter.ses.web.ros.vo.production.wh.QcingListResult;
import com.redescooter.ses.web.ros.vo.production.wh.TobeStoredResult;
import com.redescooter.ses.web.ros.vo.production.wh.WasteResult;
import com.redescooter.ses.web.ros.vo.production.wh.WhEnter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        //采购仓库
        OpeWhse opeWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.PURCHAS.getValue())).get(0);
        QueryWrapper<OpeStock> availableWrapper = new QueryWrapper<>();
        availableWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        int availableCount = opeStockService.count(availableWrapper);
        map.put(PurchasingWhTypeEnums.AVAILABLE.getValue(), availableCount);

        int qcCount = purchasingWhServiceMapper.countByTypeQcCount(enter);
        map.put(PurchasingWhTypeEnums.PURCHASING.getValue(), qcCount);

        int tobeStoredCount = purchasingWhServiceMapper.countByTypetobeStoredCount(enter);
        map.put(PurchasingWhTypeEnums.TO_BE_STORED.getValue(), tobeStoredCount);

        List<OpeWhse> opeWhseList = checkWhse(Lists.newArrayList(WhseTypeEnums.ALLOCATE.getValue(), WhseTypeEnums.ASSEMBLY.getValue()));
        List<Long> whseIds = Lists.newArrayList();
        opeWhseList.forEach(item -> {
            whseIds.add(item.getId());
        });
        int outWhCount = purchasingWhServiceMapper.countByTypeOutWhCount(enter);
        map.put(PurchasingWhTypeEnums.OUT_WH.getValue(), outWhCount);

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
        if (StringUtils.isNotEmpty(enter.getType())) {
            enter.setType(BomCommonTypeEnums.getCodeByValue(enter.getType()));
        }

        OpeWhse opeWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.PURCHAS.getValue())).get(0);

        int count = purchasingWhServiceMapper.availableListCount(enter, opeWhse.getId());
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AvailableListResult> availableList = purchasingWhServiceMapper.availableList(enter, opeWhse.getId());

        // 查询质检批次号
        List<Long> partIds = Lists.newArrayList();

        availableList.stream().forEach(item -> {
            partIds.add(item.getId());
        });
        List<OpePurchasBQc> opePurchasBQcList = purchasingWhServiceMapper.PurchasBQc(partIds, PurchasingStatusEnums.IN_PURCHASING_WH.getValue());


        Map<Long, List<AvailableListBatchNResult>> batchNMap = Maps.newHashMap();
        availableList.forEach(item -> {
            List<AvailableListBatchNResult> batchNList = Lists.newArrayList();
            opePurchasBQcList.forEach(qc -> {
                if (item.getId().equals(qc.getPartsId())) {
                    if (CollectionUtils.isNotEmpty(batchNList)) {
                        batchNList.forEach(batch -> {
                            if (StringUtils.equals(batch.getBatchN(), qc.getBatchNo())) {
                                batch.setQty(batch.getQty() + qc.getPassCount());
                                batchNList.set(batchNList.indexOf(batch), batch);
                            }
                        });
                    }
                    batchNList.add(
                            AvailableListBatchNResult
                                    .builder()
                                    .id(qc.getPartsId())
                                    .batchN(qc.getBatchNo())
                                    .qty(qc.getPassCount())
                                    .build()
                    );
                }
            });
            batchNMap.put(item.getId(), batchNList);
        });

        //批次号 设置
        availableList.forEach(item -> {
            if (batchNMap.containsKey(item.getId())) {
                if (StringUtils.isNotEmpty(enter.getKeyword())) {
                    batchNMap.forEach((key, value) -> {
                        value.forEach(valueList -> {
                            if (valueList.getBatchN().contains(enter.getKeyword())) {
                                item.setBatchNList(new ArrayList<>(batchNMap.get(item.getId())));
                            }
                        });
                    });
                } else {
                    item.setBatchNList(new ArrayList<>(batchNMap.get(item.getId())));
                }
            }
        });

        availableList.removeIf(item -> CollectionUtils.isEmpty(item.getBatchNList()));
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
        if (StringUtils.isBlank(enter.getType())) {
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
//        List<OpeWhse> assemblyWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.ASSEMBLY.getValue()));
//
//        //组装仓库 数据
//        int countAssembly = purchasingWhServiceMapper.outWhListAssemblyCount(enter, Lists.newArrayList(assemblyWhse.get(0).getId()));
//
//        List<OutWhResult> whResultListAssembly = purchasingWhServiceMapper.outWhListAssembly(enter, Lists.newArrayList(assemblyWhse.get(0).getId()));
//
//        //调拨仓库数据
        List<OpeWhse> allocateWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.ALLOCATE.getValue()));

        int countAllocate = purchasingWhServiceMapper.outWhListAllocateCount(enter, Lists.newArrayList(allocateWhse.get(0).getId()));

        if (countAllocate == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<OutWhResult> whResultListAllocate = purchasingWhServiceMapper.outWhListAllocate(enter, Lists.newArrayList(allocateWhse.get(0).getId()));

        return PageResult.create(enter, countAllocate, whResultListAllocate);
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
    public List<AssemblyProductResult> canAssemblyProductList(GeneralEnter enter) {
        //查询出所有商品及bom 规则
        Map<Long, List<OpePartsProductB>> productPartMap = Maps.newHashMap();

        QueryWrapper<OpePartsProduct> opePartsProductQueryWrapper = new QueryWrapper<>();
        opePartsProductQueryWrapper.eq(OpePartsProduct.COL_PRODUCT_TYPE, BomCommonTypeEnums.SCOOTER.getValue());
        List<OpePartsProduct> partsProductList = opePartsProductService.list(opePartsProductQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductList)) {
            return new ArrayList<>();
        }
        List<Long> productIds = Lists.newArrayList();
        partsProductList.forEach(item -> {
            productIds.add(item.getId());
        });

        //查询bom 规则
        QueryWrapper<OpePartsProductB> opePartsProductBQueryWrapper = new QueryWrapper<>();
        opePartsProductBQueryWrapper.in(OpePartsProductB.COL_PARTS_PRODUCT_ID, productIds);
        List<OpePartsProductB> partsProductBList = opePartsProductBService.list(opePartsProductBQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductBList)) {
            return new ArrayList<>();
        }
        //过滤出map 产品结构
        Set<Long> partIds = Sets.newHashSet();
        partsProductBList.forEach(item -> {
            if (!productPartMap.containsKey(item.getPartsProductId())) {
                productPartMap.put(item.getPartsProductId(), Lists.newArrayList(item));
            } else {
                List<OpePartsProductB> opePartsProductBList = productPartMap.get(item.getPartsProductId());
                opePartsProductBList.add(item);
                productPartMap.put(item.getPartsProductId(), opePartsProductBList);
            }
            partIds.add(item.getPartsId());
        });

        //查询采购仓库
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
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
            return new ArrayList<>();
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
                        if (item.getPartsId().equals(stock.getMaterielProductId())) {

                            int canAss = Long.valueOf(stock.getAvailableTotal() / item.getPartsQty()).intValue();
                            if (maxTotal == 0) {
                                partTotal++;
                                maxTotal = canAss;
                                continue flag2;
                            }
                            if (canAss < maxTotal) {
                                partTotal++;
                                maxTotal = canAss;
                                continue flag2;
                            }
                            if (canAss > 0) {
                                partTotal++;
                                continue flag2;
                            }
                        }
                    }
                }
            }
            if (partTotal == value.size()) {
                canAssembledMap.put(key, maxTotal);
            }
        }

        List<AssemblyProductResult> result = new ArrayList<>();
        canAssembledMap.forEach((key, value) -> {
            partsProductList.forEach(item -> {
                if (key.equals(item.getId())) {
                    result.add(AssemblyProductResult.builder()
                            .id(key)
                            .productName(item.getCnName())
                            .qty(value)
                            .build());
                }
            });
        });

        result.removeIf(item -> item.getQty() == 0);
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
