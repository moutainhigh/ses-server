package com.redescooter.ses.mobile.rps.service.purchasinwh.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.production.*;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingEventEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.purchasinwh.PurchasPutStorageMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.purchasinwh.PurchasPutStroageService;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class PurchasPutStorageServiceImpl implements PurchasPutStroageService {
    @Autowired
    private PurchasPutStorageMapper purchasPutStorageMapper;
    @Autowired
    private OpePurchasService opePurchasService;
    @Autowired
    private OpeStockService opeStockService;
    @Autowired
    private OpeStockBillService opeStockBillService;

    @Autowired
    private OpePurchasBService opePurchasBService;
    @Autowired
    private OpePartsService opePartsService;

    @Autowired
   private OpePurchasTraceService opePurchasTraceService;

    @Autowired
    private OpeWhseService opeWhseService;

    @Reference
    private IdAppService idAppService;

    @Autowired
    private OpeStockPurchasService opeStockPurchasService;

    @Autowired
    private OpePurchasBQcItemService opePurchasBQcItemService;

    @Autowired
    private OpePurchasBQcService opePurchasBQcService;

    @Transactional
    @Override
    public PageResult<PutStorageResult> purchasPutStroageList(PageEnter enter) {
        int count = purchasPutStorageMapper.purchasListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);

        }
        return PageResult.create(enter, count, purchasPutStorageMapper.putStorageResult(enter));
    }

    @Transactional
    @Override
    public PageResult<PurchasDetailsListResult> storageDetailsList(PurchasDetailsEnter enter) {
        int count = purchasPutStorageMapper.purchasDetailListCount(enter);
        if (count == 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //入库单数据保存
        List<OpeStockBill> saveOpeStockBillList = Lists.newArrayList();
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);

        }

        List<PurchasDetailsListResult> purchasDetailsListResults = purchasPutStorageMapper.storageDetailsResult(enter);
        if (purchasDetailsListResults.size()<=0){
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        return PageResult.create(enter, count, purchasDetailsListResults);
    }

    @Transactional
    @Override
    public WhetherIDResult whetherID(PurchasDetailsEnter enter) {
        WhetherIDResult idResult = purchasPutStorageMapper.WhetherID(enter);
        if (idResult == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        return idResult;
    }

    @Transactional
    @Override
    public HaveIdPartsResult haveIdPartsResult(PurchasDetailsEnter enter) {
        //库存数据更新
        List<OpeStock> saveStockList = Lists.newArrayList();
        //入库单数据保存
        List<OpeStockBill> saveOpeStockBillList = Lists.newArrayList();

        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //采购单状态更新
        OpePurchas opePurchas = opePurchasService.getById(opePurchasB.getPurchasId());
        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.QC_COMPLETED.getValue()) && !StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.RETURN.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        // 封装 库存、入库单数据
        saveStockBillSingle(enter, saveStockList, saveOpeStockBillList, opePurchas);

        //库存更新
        opeStockService.saveOrUpdateBatch(saveStockList);
        //入库单 保存
        opeStockBillService.saveBatch(saveOpeStockBillList);

        //采购部件入库数量更新
        opePurchasB.setInWaitWhQty(opePurchasB.getInWaitWhQty() - 1);
        opePurchasB.setUpdatedBy(enter.getUserId());
        opePurchasB.setUpdatedTime(new Date());
        opePurchasBService.updateById(opePurchasB);
        //采购总数量更新
        opePurchas.setInWaitWhTotal(opePurchas.getInWaitWhTotal() - 1);
        opePurchas.setId(opePurchasB.getPurchasId());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchas.setUpdatedTime(new Date());
        opePurchasService.updateById(opePurchas);

        OpePurchas purchas = opePurchasService.getById(opePurchasB.getPurchasId());
        if (purchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        } else if (purchas.getTotalQty() == 0) {
            //采购单状态更新
            purchas.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            purchas.setUpdatedBy(enter.getUserId());
            purchas.setUpdatedTime(new Date());
            opePurchasService.updateById(purchas);
        }

        //查询仓库id
        QueryWrapper<OpeWhse> opeWhse = new QueryWrapper<>();
        opeWhse.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        OpeWhse opeWhsegetid = opeWhseService.getOne(opeWhse);
        if (opeWhsegetid == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());

        }

        QueryWrapper<OpeStock> opeStockPurchasQueryWrapper = new QueryWrapper<>();
        opeStockPurchasQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opePurchasB.getPartId());
        opeStockPurchasQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhsegetid.getId());
        OpeStock opeStockData = opeStockService.getOne(opeStockPurchasQueryWrapper);
        //拿取部件信息
        OpeParts partsData = opePartsService.getById(opePurchasB.getPartId());
        //查询批次号
        OpePurchasBQcItem opePurchasBQcItem = opePurchasBQcItemService.getOne(new LambdaQueryWrapper<OpePurchasBQcItem>()
                .eq(OpePurchasBQcItem::getPurchasBId, opePurchasB.getId())
                .eq(OpePurchasBQcItem::getPartId, opePurchasB.getPartId())
                .eq(OpePurchasBQcItem::getQcResult, QcStatusEnums.PASS.getValue())
        );
        if (opePurchasBQcItem == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }

        OpePurchasBQc opePurchasBQc = opePurchasBQcService.getById(opePurchasBQcItem.getPurchasBQcId());
        if (opePurchasBQc == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }

        //入库条目数据增加
        OpeStockPurchas opeStockPurchas = OpeStockPurchas.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_PURCHAS))
                .dr(0)
                .status(ExceptionCodeEnums.STOCK_STATUS.getMessage())
                .stockId(opeStockData.getId())
                .partId(partsData.getId())
                .lot(opePurchasBQc.getBatchNo())
                .partNumber(partsData.getPartsNumber())
                .principalId(enter.getUserId())
                .serialNumber(opePurchasBQcItem.getSerialNum())
                .inStockTime(new Date())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();

        //增加仓库数据
        opeStockPurchasService.save(opeStockPurchas);
        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opePurchas.getId());
        saveNodeEnter.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
        saveNodeEnter.setEvent(PurchasingEventEnums.IN_PURCHASING_WH.getValue());
        saveNodeEnter.setMemo(null);
        this.savePurchasingNode(saveNodeEnter);
        HaveIdPartsResult haveIdPartsResult = purchasPutStorageMapper.haveIDPartsResult(enter);
        if (haveIdPartsResult==null){
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        return haveIdPartsResult;
    }

    @Transactional
    @Override
    public NotIdPartsSucceedResult notIdPartsSucceedResult(NotIdEnter enter) {

        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        OpePurchas opePurchas = opePurchasService.getById(opePurchasB.getPurchasId());
        //库存数据更新
        List<OpeStock> saveStockList = Lists.newArrayList();
        //入库单数据保存
        List<OpeStockBill> saveOpeStockBillList = Lists.newArrayList();

        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //采购单状态更新

        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.QC_COMPLETED.getValue()) && !StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.RETURN.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        // 封装 库存、入库单数据
        saveStockBill(enter, saveStockList, saveOpeStockBillList, opePurchas);

        //库存更新
        opeStockService.saveOrUpdateBatch(saveStockList);
        //入库单 保存
        opeStockBillService.saveBatch(saveOpeStockBillList);
        //采购部件入库数量更新
        opePurchasB.setInWaitWhQty(opePurchasB.getInWaitWhQty() - enter.getInWaitWhQty());
        opePurchasB.setUpdatedBy(enter.getUserId());
        opePurchasB.setUpdatedTime(new Date());
        opePurchasBService.updateById(opePurchasB);
        //采购总数量更新
        opePurchas.setInWaitWhTotal(opePurchas.getInWaitWhTotal() - enter.getInWaitWhQty());
        opePurchas.setId(opePurchasB.getPurchasId());
        opePurchas.setUpdatedBy(enter.getUserId());
        opePurchas.setUpdatedTime(new Date());
        opePurchasService.updateById(opePurchas);

        OpePurchas purchas = opePurchasService.getById(opePurchasB.getPurchasId());
        if (purchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        } else if (purchas.getInWaitWhTotal() == 0) {
            //采购单状态更新
            purchas.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            purchas.setUpdatedBy(enter.getUserId());
            purchas.setUpdatedTime(new Date());
            opePurchasService.updateById(purchas);
        }


        OpePurchasB storageData = opePurchasBService.getById(enter.getId());
        //查询仓库id
        QueryWrapper<OpeWhse> opeWhse = new QueryWrapper<>();
        opeWhse.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        OpeWhse opeWhsegetid = opeWhseService.getOne(opeWhse);
        QueryWrapper<OpeStock> opeStockPurchasQueryWrapper = new QueryWrapper<>();
        opeStockPurchasQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opePurchasB.getPartId());
        opeStockPurchasQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhsegetid.getId());
        OpeStock opeStockData = opeStockService.getOne(opeStockPurchasQueryWrapper);
        //拿取部件信息
        OpeParts partsData = opePartsService.getById(opePurchasB.getPartId());
        //查询批次号
        OpePurchasBQcItem opePurchasBQcItem = opePurchasBQcItemService.getOne(new LambdaQueryWrapper<OpePurchasBQcItem>().eq(OpePurchasBQcItem::getPurchasBId, opePurchasB.getId()).eq(OpePurchasBQcItem::getPartId, opePurchasB.getPartId()));
        if (opePurchasBQcItem == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }

        OpePurchasBQc opePurchasBQc = opePurchasBQcService.getById(opePurchasBQcItem.getPurchasBQcId());
        if (opePurchasBQc == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        //入库条目数据增加
        OpeStockPurchas opeStockPurchas = OpeStockPurchas.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_PURCHAS))
                .dr(0)
                .status(ExceptionCodeEnums.STOCK_STATUS.getMessage())
                .stockId(opeStockData.getId())
                .partId(partsData.getId())
                .lot(opePurchasBQc.getBatchNo())
                .partNumber(partsData.getPartsNumber())
                .principalId(enter.getUserId())
                .inStockTime(new Date())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();

        //增加仓库数据
        opeStockPurchasService.save(opeStockPurchas);
        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opePurchas.getId());
        saveNodeEnter.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
        saveNodeEnter.setEvent(PurchasingEventEnums.IN_PURCHASING_WH.getValue());
        saveNodeEnter.setMemo(null);
        this.savePurchasingNode(saveNodeEnter);
        NotIdPartsSucceedResult notIdPartsSucceedResult = purchasPutStorageMapper.notIDPartsSucceedListResult(enter);
        if (notIdPartsSucceedResult == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        return notIdPartsSucceedResult;
    }

    @Transactional
    @Override
    public NotIdPartsResult notIdPartsResult(PurchasDetailsEnter enter) {
        NotIdPartsResult notIdPartsResult = purchasPutStorageMapper.notIDPartsListResult(enter);
        if (notIdPartsResult==null){
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }
        return notIdPartsResult;
    }

    @Transactional
    @Override
    public GeneralResult savePurchasingNode(SaveNodeEnter enter) {
        opePurchasTraceService.save(OpePurchasTrace.builder()
                .id(idAppService.getId(SequenceName.OPE_PURCHAS_TRACE))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .purchasId(enter.getId())
                .status(enter.getStatus())
                .event(enter.getEvent())
                .eventTime(new Date())
                .memo(StringUtils.isBlank(enter.getMemo()) == true ? null : enter.getMemo())
                .createBy(enter.getUserId())
                .createTime(new Date())
                .updateBy(enter.getUserId())
                .updateTime(new Date())
                .build());
        return new GeneralResult(enter.getRequestId());
    }

    private void saveStockBillSingle(PurchasDetailsEnter enter, List<OpeStock> saveStockList, List<OpeStockBill> saveOpeStockBillList, OpePurchas opePurchas) {
        //采购条目
        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, opePurchas.getId());
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        List<OpePurchasB> purchasBList = opePurchasBService.list(opePurchasBQueryWrapper);

        List<Long> partIds = Lists.newArrayList();
        purchasBList.forEach(item -> {
            partIds.add(item.getPartId());
        });

        Collection<OpeParts> partsList = opePartsService.listByIds(partIds);

        //查询采购仓库
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, 0);
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, partIds);
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);

        for (OpePurchasB item : purchasBList) {
            Boolean stockExist = Boolean.FALSE;
            for (OpeStock stock : opeStockList) {
                if (item.getPartId().equals(stock.getMaterielProductId())) {
                    stockExist = Boolean.TRUE;
                    //有库存 库存累计
                    stock.setAvailableTotal(stock.getAvailableTotal() + item.getTotalCount());
                    stock.setIntTotal(stock.getIntTotal() + item.getTotalCount());
                    stock.setUpdatedBy(enter.getUserId());
                    stock.setUpdatedTime(new Date());
                    saveStockList.add(stock);

                    //入库单 生成
                    saveOpeStockBillList.add(
                            buildOpeStockBill(enter, item, stock)
                    );
                }
            }
            if (!stockExist) {
                //无库存
                OpeStock stock = OpeStock.builder()
                        .id(idAppService.getId(SequenceName.OPE_STOCK))
                        .dr(0)
                        .userId(0L)
                        .tenantId(0L)
                        .whseId(opeWhse.getId())
                        .intTotal(item.getTotalCount())
                        .availableTotal(item.getTotalCount())
                        .outTotal(0)
                        .wornTotal(0)
                        .materielProductId(item.getPartId())
                        .materielProductName(null)
                        .materielProductType(null)
                        .revision(0)
                        .updatedBy(enter.getUserId())
                        .updatedTime(new Date())
                        .createdBy(enter.getUserId())
                        .createdTime(new Date())
                        .build();
                saveStockList.add(stock);
                saveOpeStockBillList.add(
                        buildOpeStockBill(enter, item, stock)
                );
            }
        }

        if (CollectionUtils.isNotEmpty(saveStockList)) {
            saveStockList.forEach(item -> {
                partsList.forEach(part -> {
                    if (item.getMaterielProductId().equals(part.getId())) {
                        item.setMaterielProductName(part.getCnName());
                        item.setMaterielProductType(part.getPartsType());
                    }
                });
            });
        }
    }

    private void saveStockBill(NotIdEnter enter, List<OpeStock> saveStockList, List<OpeStockBill> saveOpeStockBillList, OpePurchas opePurchas) {
        //采购条目
        QueryWrapper<OpePurchasB> opePurchasBQueryWrapper = new QueryWrapper<>();
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_PURCHAS_ID, opePurchas.getId());
        opePurchasBQueryWrapper.eq(OpePurchasB.COL_DR, 0);
        List<OpePurchasB> purchasBList = opePurchasBService.list(opePurchasBQueryWrapper);

        List<Long> partIds = Lists.newArrayList();
        purchasBList.forEach(item -> {
            partIds.add(item.getPartId());
        });

        Collection<OpeParts> partsList = opePartsService.listByIds(partIds);

        //查询采购仓库
        QueryWrapper<OpeWhse> opeWhseQueryWrapper = new QueryWrapper<>();
        opeWhseQueryWrapper.eq(OpeWhse.COL_DR, 0);
        opeWhseQueryWrapper.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        OpeWhse opeWhse = opeWhseService.getOne(opeWhseQueryWrapper);
        if (opeWhse == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        QueryWrapper<OpeStock> opeStockQueryWrapper = new QueryWrapper<>();
        opeStockQueryWrapper.eq(OpeStock.COL_DR, 0);
        opeStockQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhse.getId());
        opeStockQueryWrapper.in(OpeStock.COL_MATERIEL_PRODUCT_ID, partIds);
        List<OpeStock> opeStockList = opeStockService.list(opeStockQueryWrapper);

        for (OpePurchasB item : purchasBList) {
            Boolean stockExist = Boolean.FALSE;
            for (OpeStock stock : opeStockList) {
                if (item.getPartId().equals(stock.getMaterielProductId())) {
                    stockExist = Boolean.TRUE;
                    //有库存 库存累计
                    stock.setAvailableTotal(stock.getAvailableTotal() + item.getTotalCount());
                    stock.setIntTotal(stock.getIntTotal() + item.getTotalCount());
                    stock.setUpdatedBy(enter.getUserId());
                    stock.setUpdatedTime(new Date());
                    saveStockList.add(stock);

                    //入库单 生成
                    saveOpeStockBillList.add(
                            NotbuildOpeStockBill(enter, item, stock)
                    );
                }
            }
            if (!stockExist) {
                //无库存
                OpeStock stock = OpeStock.builder()
                        .id(idAppService.getId(SequenceName.OPE_STOCK))
                        .dr(0)
                        .userId(0L)
                        .tenantId(0L)
                        .whseId(opeWhse.getId())
                        .intTotal(item.getTotalCount())
                        .availableTotal(item.getTotalCount())
                        .outTotal(0)
                        .wornTotal(0)
                        .materielProductId(item.getPartId())
                        .materielProductName(null)
                        .materielProductType(null)
                        .revision(0)
                        .updatedBy(enter.getUserId())
                        .updatedTime(new Date())
                        .createdBy(enter.getUserId())
                        .createdTime(new Date())
                        .build();
                saveStockList.add(stock);
                saveOpeStockBillList.add(
                        NotbuildOpeStockBill(enter, item, stock)
                );
            }
        }

        if (CollectionUtils.isNotEmpty(saveStockList)) {
            saveStockList.forEach(item -> {
                partsList.forEach(part -> {
                    if (item.getMaterielProductId().equals(part.getId())) {
                        item.setMaterielProductName(part.getCnName());
                        item.setMaterielProductType(part.getPartsType());
                    }
                });
            });
        }
    }

    private OpeStockBill NotbuildOpeStockBill(NotIdEnter enter, OpePurchasB item, OpeStock stock) {
        return OpeStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .stockId(stock.getId())
                .direction(InOutWhEnums.IN.getValue())
                .status(StockBillStatusEnums.NORMAL.getValue())
                .sourceId(enter.getId())
                .total(item.getTotalCount())
                .sourceType(SourceTypeEnums.PURCHAS.getValue())
                .principalId(enter.getUserId())
                .operatineTime(new Date())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }

    private OpeStockBill buildOpeStockBill(PurchasDetailsEnter enter, OpePurchasB item, OpeStock stock) {
        return OpeStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .tenantId(0L)
                .userId(enter.getUserId())
                .stockId(stock.getId())
                .direction(InOutWhEnums.IN.getValue())
                .status(StockBillStatusEnums.NORMAL.getValue())
                .sourceId(enter.getId())
                .total(item.getTotalCount())
                .sourceType(SourceTypeEnums.PURCHAS.getValue())
                .principalId(enter.getUserId())
                .operatineTime(new Date())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .build();
    }


}
