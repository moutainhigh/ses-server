package com.redescooter.ses.web.ros.service.production.purchasingWh.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionBomStatusEnums;
import com.redescooter.ses.api.common.enums.bom.ProductionPartsRelationTypeEnums;
import com.redescooter.ses.api.common.enums.production.wh.PurchasingWhTypeEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.enums.website.ProductModelEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.constant.StringManaConstant;
import com.redescooter.ses.web.ros.dao.production.PurchasingWhServiceMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsRelation;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.dm.OpeStock;
import com.redescooter.ses.web.ros.dm.OpeStockPurchas;
import com.redescooter.ses.web.ros.dm.OpeWhse;
import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import com.redescooter.ses.web.ros.service.base.OpePartsProductService;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsRelationService;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;
import com.redescooter.ses.web.ros.service.base.OpeStockPurchasService;
import com.redescooter.ses.web.ros.service.base.OpeStockService;
import com.redescooter.ses.web.ros.service.base.OpeWhseService;
import com.redescooter.ses.web.ros.service.production.purchasingWh.PurchasingWhService;
import com.redescooter.ses.web.ros.utils.NumberUtil;
import com.redescooter.ses.web.ros.vo.production.wh.AvailableListBatchNResult;
import com.redescooter.ses.web.ros.vo.production.wh.AvailableListResult;
import com.redescooter.ses.web.ros.vo.production.wh.OutWhResult;
import com.redescooter.ses.web.ros.vo.production.wh.QcingListResult;
import com.redescooter.ses.web.ros.vo.production.wh.TobeStoredResult;
import com.redescooter.ses.web.ros.vo.production.wh.WasteResult;
import com.redescooter.ses.web.ros.vo.production.wh.WhEnter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName:PurchasingWhServiceImpl
 * @description: PurchasingWhServiceImpl
 * @author: Alex
 * @Version???1.3
 * @create: 2020/04/03 14:16
 */
@Slf4j
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
    private OpeProductionScooterBomService opeProductionScooterBomService;

    @Autowired
    private OpeProductionPartsRelationService opeProductionPartsRelationService;

    /**
     * ????????????
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

        //????????????
        int availableCount = opeStockService.count(availableWrapper);
        map.put(PurchasingWhTypeEnums.AVAILABLE.getValue(), availableCount);

        //?????????
        int qcCount = purchasingWhServiceMapper.countByTypeQcCount(enter);
        map.put(PurchasingWhTypeEnums.PURCHASING.getValue(), qcCount);

        //?????????
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

        //?????????????????? ?????????????????????????????????
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
     * ?????? ??????
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
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<AvailableListResult> availableList(WhEnter enter) {
        if (NumberUtil.notNullAndGtFifty(enter.getKeyword())) {
            return PageResult.createZeroRowResult(enter);
        }
        OpeWhse opeWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.PURCHAS.getValue())).get(0);

        int count = purchasingWhServiceMapper.availableListCount(enter, opeWhse.getId());
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        List<AvailableListResult> availableList = purchasingWhServiceMapper.availableList(enter, opeWhse.getId());

        if (CollectionUtils.isNotEmpty(availableList)) {
            // ?????????????????????
            List<OpeStockPurchas> opeStockPurchasList = opeStockPurchasService.list(new LambdaQueryWrapper<OpeStockPurchas>()
                    .eq(OpeStockPurchas::getStatus, StockProductPartStatusEnums.AVAILABLE.getValue())
                    // ?????????partId ???????????????stockId
                    .in(OpeStockPurchas::getStockId, availableList.stream().map(AvailableListResult::getStockId).collect(Collectors.toList())));

            //???????????????
            Map<Long, List<AvailableListBatchNResult>> batchNMap = new HashMap<>();

            //????????????????????????
            for (OpeStockPurchas item : opeStockPurchasList) {
                if (batchNMap.containsKey(item.getStockId())) {

                    //?????????????????? ??????????????????????????? ?????????????????????
                    List<AvailableListBatchNResult> availableListBatchNList = batchNMap.get(item.getStockId());

                    Boolean existBatchN = Boolean.FALSE;

                    for (AvailableListBatchNResult batchN : availableListBatchNList) {
                        if (StringUtils.equals(item.getLot(), batchN.getBatchN())) {
                            existBatchN = Boolean.TRUE;
                            batchN.setQty(batchN.getQty() + 1);
                            break;
                        }
                    }
                    //?????????????????? ??????map ?????? ????????? ??????????????????
                    if (!existBatchN) {
                        //?????? ???map???value????????????????????????
                        availableListBatchNList.add(AvailableListBatchNResult.builder()
                                .id(item.getStockId())
                                .qty(item.getInWhQty())
                                .batchN(item.getLot())
                                .build());
                    }
                    batchNMap.put(item.getStockId(), availableListBatchNList);
                }

                //map???????????????????????? ??????map???????????????
                if (!batchNMap.containsKey(item.getStockId())) {
                    List<AvailableListBatchNResult> availableListBatchNList = new ArrayList<>();
                    availableListBatchNList.add(AvailableListBatchNResult.builder()
                            .id(item.getStockId())
                            .qty(item.getInWhQty())
                            .batchN(item.getLot())
                            .build());
                    batchNMap.put(item.getStockId(), availableListBatchNList);
                }
            }

            log.info("?????????????????????" + batchNMap.toString());
            //??????????????????
            availableList.forEach(item -> {
                // type ????????????
                item.setType(BomCommonTypeEnums.getEnumsByValue(item.getType()).getCode());
                if (batchNMap.containsKey(item.getStockId())) {
                    item.setBatchNList(batchNMap.get(item.getStockId()));
                }
                item.setType(BomCommonTypeEnums.getValueByCode(item.getType()));
            });
        }
        return PageResult.create(enter, count, availableList);
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<QcingListResult> qcingList(WhEnter enter) {
        int count = purchasingWhServiceMapper.qcingListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasingWhServiceMapper.qcingList(enter));
    }

    /**
     * ???????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<TobeStoredResult> tobeStoredList(WhEnter enter) {
        int count = purchasingWhServiceMapper.tobeStoredListCount(enter);
        if (NumberUtil.eqZero(count)) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasingWhServiceMapper.tobeStoredList(enter));
    }

    /**
     * ????????????
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<OutWhResult> outWhList(WhEnter enter) {
        List<OpeWhse> assemblyWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.ASSEMBLY.getValue()));
        //??????????????????
        List<OpeWhse> allocateWhse = checkWhse(Lists.newArrayList(WhseTypeEnums.ALLOCATE.getValue()));


        Integer num = purchasingWhServiceMapper.outWhCount(enter, Lists.newArrayList(assemblyWhse.get(0).getId()), Lists.newArrayList(allocateWhse.get(0).getId()));

        List<OutWhResult> results = purchasingWhServiceMapper.outWhList(enter, Lists.newArrayList(assemblyWhse.get(0).getId()), Lists.newArrayList(allocateWhse.get(0).getId()));

//        //???????????? ??????
//        int countAssembly = purchasingWhServiceMapper.outWhListAssemblyCount(enter, Lists.newArrayList(assemblyWhse.get(0).getId()));
//        log.info("?????????????????????"+countAssembly);
//        List<OutWhResult> whResultListAssembly = purchasingWhServiceMapper.outWhListAssembly(enter, Lists.newArrayList(assemblyWhse.get(0).getId()));
//        log.info("????????????????????????????????????"+whResultListAssembly.size());
//
//        int countAllocate = purchasingWhServiceMapper.outWhListAllocateCount(enter, Lists.newArrayList(allocateWhse.get(0).getId()));
//        log.info("?????????????????????"+countAllocate);
////        if (countAllocate == 0) {
////            return PageResult.createZeroRowResult(enter);
////        }
//        enter.setPageSize(enter.getPageSize()-whResultListAssembly.size());
//        List<OutWhResult> whResultListAllocate = purchasingWhServiceMapper.outWhListAllocate(enter, Lists.newArrayList(allocateWhse.get(0).getId()));
//        log.info("????????????????????????????????????"+whResultListAllocate.size());
//        whResultListAllocate.addAll(whResultListAssembly);

        return PageResult.create(enter, num, results);
    }

    /**
     * ????????????
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
     * ????????????????????????
     *
     * @param enter
     * @return
     */
    @Override
    public Map<String, Integer> canAssemblyProductList(GeneralEnter enter) {
        //????????????
        Map<String, Integer> result = new HashMap<>();
        for (ProductModelEnums item : ProductModelEnums.values()) {
            result.put(item.getValue(), 0);
        }

        //????????????????????????bom ??????
        Map<Long, List<OpeProductionPartsRelation>> productPartMap = new HashMap<>();

        QueryWrapper<OpeProductionScooterBom> opeProductionScooterBomQueryWrapper = new QueryWrapper<>();
        opeProductionScooterBomQueryWrapper.eq(OpeProductionScooterBom.COL_BOM_STATUS,
                ProductionBomStatusEnums.ACTIVE.getValue());
        List<OpeProductionScooterBom> partsProductList =
                opeProductionScooterBomService.list(opeProductionScooterBomQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductList)) {
            return result;
        }
        List<Long> productIds =
                partsProductList.stream().map(OpeProductionScooterBom::getId).collect(Collectors.toList());

        //??????bom ??????

        QueryWrapper<OpeProductionPartsRelation> opeProductionPartsRelatioQueryWrapper = new QueryWrapper<>();
        opeProductionPartsRelatioQueryWrapper.in(OpeProductionPartsRelation.COL_PRODUCTION_ID, productIds);
        opeProductionPartsRelatioQueryWrapper.in(OpeProductionPartsRelation.COL_PRODUCTION_TYPE,
                ProductionPartsRelationTypeEnums.SCOOTER_BOM.getValue());
        List<OpeProductionPartsRelation> partsProductBList =
                opeProductionPartsRelationService.list(opeProductionPartsRelatioQueryWrapper);
        if (CollectionUtils.isEmpty(partsProductBList)) {
            return result;
        }
        //?????????map ????????????
        Set<Long> partIds =
                partsProductBList.stream().map(OpeProductionPartsRelation::getPartsId).collect(Collectors.toSet());
        productPartMap =
                partsProductBList.stream().collect(Collectors.groupingBy(OpeProductionPartsRelation::getProductionId));

        //??????????????????
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        opeWhseQueryWrapper.last("limit 1");
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (StringManaConstant.entityIsNull(opeWhse)) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        //????????????
        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, new ArrayList<>(partIds));
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.gt(OpeStock.COL_AVAILABLE_TOTAL, 0);
        List<OpeStock> stockList = opeStockService.list(opeStockQueryWrapper);
        if (CollectionUtils.isEmpty(stockList)) {
            return result;
        }

        // ????????????????????????????????????
        Map<Long, Integer> canAssembledMap = Maps.newHashMap();

        flag1:
        for (Map.Entry<Long, List<OpeProductionPartsRelation>> entry : productPartMap.entrySet()) {
            Long key = entry.getKey();
            List<OpeProductionPartsRelation> value = entry.getValue();
            Integer maxTotal = 0;
            int total = 0;
            if (productIds.contains(key)) {

                flag2:
                for (OpeProductionPartsRelation item : value) {

                    flag3:
                    for (OpeStock stock : stockList) {
                        if (item.getPartsId().equals(stock.getMaterielProductId())) {

                            int canAss = Long.valueOf(stock.getAvailableTotal() / item.getPartsQty()).intValue();
                            if (0 == maxTotal) {
                                total++;
                                maxTotal = canAss;
                                continue flag2;
                            }
                            if (canAss <= maxTotal) {
                                total++;
                                maxTotal = canAss;
                                continue flag2;
                            }
                            if (0 == canAss) {
                                total++;
                                break flag2;
                            }
                        }
                    }
                }
            }
            if (total == value.size()) {
                canAssembledMap.put(key, maxTotal);
            }
        }

        if (canAssembledMap.isEmpty()) {
            return result;
        }
        //????????????????????????
        return result;
    }

    private List<OpeWhse> checkWhse(List<String> types) {
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, Constant.DR_FALSE);
        opeWhseQueryWrapper.in(OpeWhse.COL_TYPE, types);
        List<OpeWhse> opeWhseList = opeWhseService.list(opeWhseQueryWrapper);
        if (opeWhseList.size() != types.size()) {
            throw new SesWebRosException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }
        return opeWhseList;
    }
}
