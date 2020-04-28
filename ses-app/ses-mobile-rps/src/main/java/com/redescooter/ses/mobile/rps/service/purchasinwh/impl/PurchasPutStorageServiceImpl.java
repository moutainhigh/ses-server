package com.redescooter.ses.mobile.rps.service.purchasinwh.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.WhseTypeEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingEventEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.vo.SaveNodeEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.purchasinwh.PurchasPutStorageMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.ReceiptTraceService;
import com.redescooter.ses.mobile.rps.service.base.*;
import com.redescooter.ses.mobile.rps.service.purchasinwh.PurchasPutStroageService;
import com.redescooter.ses.mobile.rps.vo.purchasinwh.*;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

//import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
//import com.redescooter.ses.mobile.rps.service.base.impl.OpePurchasTraceService;

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
    @Autowired
    private ReceiptTraceService receiptTraceService;

    /**
     * 采购仓库待入库信息
     *
     * @param
     * @return
     */
    @Transactional
    @Override
    public PageResult<PutStorageResult> purchasPutStroageList(PageEnter enter) {
        int count = purchasPutStorageMapper.purchasListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, purchasPutStorageMapper.putStorageResult(enter));
    }

    /**
     * 采购仓库待入库详情信息
     *
     * @param
     * @return
     */
    @Transactional
    @Override
    public PageResult<PurchasDetailsListResult> storageDetailsList(PurchasDetailsEnter enter) {
        int count = purchasPutStorageMapper.purchasDetailListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<PurchasDetailsListResult> purchasDetailsListResults = purchasPutStorageMapper.storageDetailsResult(enter);
        if (purchasDetailsListResults.isEmpty()) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        return PageResult.create(enter, count, purchasDetailsListResults);
    }

    @Transactional
    @Override
    public HaveIdPartsResult haveIdPartsResult(HaveIdEnter enter) {

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
        //拿取部件信息
        OpeParts partsData = opePartsService.getById(opePurchasB.getPartId());
        if (partsData.getIdClass() != true) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }
        //库存数据更新
        OpeStock opeStock = new OpeStock();
        //入库单数据保存
        OpeStockBill saveOpeStockBill = new OpeStockBill();

        //生成入库单、库存数据更新
        saveStockBillSingle(enter, opeStock, saveOpeStockBill, opePurchasB);

        //库存更新
        opeStockService.saveOrUpdate(opeStock);
        //入库单 保存
        opeStockBillService.save(saveOpeStockBill);

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
        if (opeStockData == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //查询批次号
        OpePurchasBQcItem opePurchasBQcItem = opePurchasBQcItemService.getOne(new LambdaQueryWrapper<OpePurchasBQcItem>()
                .eq(OpePurchasBQcItem::getPurchasBId, opePurchasB.getId())
                .eq(OpePurchasBQcItem::getPartId, opePurchasB.getPartId())
                .eq(OpePurchasBQcItem::getQcResult, QcStatusEnums.PASS.getValue())
        );
        if (opePurchasBQcItem == null && opePurchasBQcItem.getSerialNum().equals(enter.getSerialNum())) {
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
                .status(StockProductPartStatusEnums.AVAILABLE.getValue())
                .stockId(opeStockData.getId())
                .partId(partsData.getId())
                .lot(opePurchasBQc.getBatchNo())
                .partNumber(partsData.getPartsNumber())
                .principalId(enter.getUserId())
                .serialNumber(enter.getSerialNum())
                .inStockTime(new Date())
                .revision(0)
                .createdBy(enter.getUserId())
                .createdTime(new Date())
                .updatedBy(enter.getUserId())
                .updatedTime(new Date())
                .inStockBillId(saveOpeStockBill.getId())
                .outPrincipalId(0L)
                .outStockBillId(0L)
                .outStockTime(null)
                .inWhQty(1)
                .build();
        //增加仓库数据
        opeStockPurchasService.save(opeStockPurchas);
        if (opePurchasB.getInWaitWhQty() != null && opePurchas.getInWaitWhTotal() != null) {
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
        } else {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (opePurchas.getInWaitWhTotal() == 0) {
            //采购单状态更新
            opePurchas.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
            opePurchasService.updateById(opePurchas);
        }

        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opePurchas.getId());
        saveNodeEnter.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
        saveNodeEnter.setEvent(PurchasingEventEnums.IN_PURCHASING_WH.getValue());
        saveNodeEnter.setMemo(null);
        receiptTraceService.savePurchasingNode(saveNodeEnter);
        return purchasPutStorageMapper.haveIdPartsResult(enter);
    }

    /**
     * 无Id 入库
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public NotIdPartsSucceedResult notIdInWh(NotIdEnter enter) {

        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        OpePurchas opePurchas = opePurchasService.getById(opePurchasB.getPurchasId());
        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //采购单状态更新
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.QC_COMPLETED.getValue()) && !StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.RETURN.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        OpeParts partsData = opePartsService.getById(opePurchasB.getPartId());
        if (partsData.getIdClass() != false) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }
        //库存数据更新
        OpeStock opeStock = new OpeStock();
        //入库单数据保存
        OpeStockBill saveOpeStockBill = new OpeStockBill();

        //生成入库单、库存数据更新
        saveStockBill(enter, opeStock, saveOpeStockBill, opePurchasB);

        //库存更新
        opeStockService.saveOrUpdate(opeStock);
        //入库单 保存
        opeStockBillService.save(saveOpeStockBill);

        //查询仓库id
        QueryWrapper<OpeWhse> opeWhse = new QueryWrapper<>();
        opeWhse.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        OpeWhse opeWhsegetid = opeWhseService.getOne(opeWhse);
        if (opeWhsegetid == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //库存查询
        QueryWrapper<OpeStock> opeStockPurchasQueryWrapper = new QueryWrapper<>();
        opeStockPurchasQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opePurchasB.getPartId());
        opeStockPurchasQueryWrapper.eq(OpeStock.COL_WHSE_ID, opeWhsegetid.getId());
        OpeStock opeStockData = opeStockService.getOne(opeStockPurchasQueryWrapper);
        if (opeStockData == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
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
                .status(StockProductPartStatusEnums.AVAILABLE.getValue())
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
                .inStockBillId(saveOpeStockBill.getId())
                .outPrincipalId(0L)
                .outStockBillId(0L)
                .outStockTime(null)
                .inWhQty(opePurchasB.getInWaitWhQty())
                .build();
        //增加仓库数据
        opeStockPurchasService.save(opeStockPurchas);

        if (opePurchasB.getInWaitWhQty() != null && opePurchas.getInWaitWhTotal() != null) {
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
        } else {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (opePurchas.getInWaitWhTotal() == 0) {
            //采购单状态更新
            opePurchas.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
            opePurchasService.updateById(opePurchas);
        }

        //节点
        SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
        BeanUtils.copyProperties(enter, saveNodeEnter);
        saveNodeEnter.setId(opePurchas.getId());
        saveNodeEnter.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
        saveNodeEnter.setEvent(PurchasingEventEnums.IN_PURCHASING_WH.getValue());
        saveNodeEnter.setMemo(null);
        receiptTraceService.savePurchasingNode(saveNodeEnter);

        return purchasPutStorageMapper.notIdPartsSucceedListResult(enter);
    }

    /**
     * 入库信息展示
     *
     * @param
     * @return
     */

    @Transactional
    @Override
    public NotIdPartsResult notIdPartsResult(NotIdDetailsEnter enter) {
        NotIdPartsResult notIdPartsResult = purchasPutStorageMapper.notIdpartslistresult(enter);
        if (notIdPartsResult == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }
        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }
        OpeParts partsData = opePartsService.getById(opePurchasB.getPartId());
        if (partsData.getIdClass() != false) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }
        return notIdPartsResult;
    }


    private void saveStockBillSingle(HaveIdEnter enter, OpeStock saveStock, OpeStockBill saveOpeStockBill, OpePurchasB opePurchasB) {

        OpeParts parts = opePartsService.getById(opePurchasB.getPartId());

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
        opeStockQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opePurchasB.getPartId());
        OpeStock opeStock = opeStockService.getOne(opeStockQueryWrapper);


        Boolean stockExist = Boolean.FALSE;
       if (opeStock!=null) {
           if (opePurchasB.getPartId().equals(opeStock.getMaterielProductId())) {
               stockExist = Boolean.TRUE;
               //有库存 库存累计
               opeStock.setAvailableTotal(opeStock.getAvailableTotal() + opePurchasB.getTotalCount());
               opeStock.setIntTotal(opeStock.getIntTotal() + opePurchasB.getTotalCount());
               opeStock.setUpdatedBy(enter.getUserId());
               opeStock.setUpdatedTime(new Date());
               BeanUtils.copyProperties(opeStock, saveStock);
               //入库单 生成
               BeanUtils.copyProperties(buildOpeStockBill(enter, opePurchasB, opeStock), saveOpeStockBill);
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
                    .intTotal(opePurchasB.getTotalCount())
                    .availableTotal(opePurchasB.getTotalCount())
                    .outTotal(0)
                    .wornTotal(0)
                    .materielProductId(opePurchasB.getPartId())
                    .materielProductName(null)
                    .materielProductType(null)
                    .revision(0)
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
                    .build();
            BeanUtils.copyProperties(stock, saveStock);
            BeanUtils.copyProperties(buildOpeStockBill(enter, opePurchasB, stock), saveOpeStockBill);
        }


        if (saveStock != null) {

            if (saveStock.getMaterielProductId().equals(parts.getId())) {
                saveStock.setMaterielProductName(parts.getCnName());
                saveStock.setMaterielProductType(parts.getPartsType());
            }

        }
    }

    private void saveStockBill(NotIdEnter enter, OpeStock saveStock, OpeStockBill saveOpeStockBill, OpePurchasB opePurchasB) {

        OpeParts parts = opePartsService.getById(opePurchasB.getPartId());

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
        opeStockQueryWrapper.eq(OpeStock.COL_MATERIEL_PRODUCT_ID, opePurchasB.getPartId());
        OpeStock opeStock = opeStockService.getOne(opeStockQueryWrapper);

        Boolean stockExist = Boolean.FALSE;
        if (opeStock!=null){
            if (opePurchasB.getPartId().equals(opeStock.getMaterielProductId())) {
                stockExist = Boolean.TRUE;
                //有库存 库存累计
                opeStock.setAvailableTotal(opeStock.getAvailableTotal() + opePurchasB.getTotalCount());
                opeStock.setIntTotal(opeStock.getIntTotal() + opePurchasB.getTotalCount());
                opeStock.setUpdatedBy(enter.getUserId());
                opeStock.setUpdatedTime(new Date());
                //todo 变量命名
                BeanUtils.copyProperties(opeStock, saveStock);
                //入库单 生成
                BeanUtils.copyProperties(NotbuildOpeStockBill(enter, opePurchasB, opeStock), saveOpeStockBill);
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
                    .intTotal(opePurchasB.getTotalCount())
                    .availableTotal(opePurchasB.getTotalCount())
                    .outTotal(0)
                    .wornTotal(0)
                    .materielProductId(opePurchasB.getPartId())
                    .materielProductName(null)
                    .materielProductType(null)
                    .revision(0)
                    .updatedBy(enter.getUserId())
                    .updatedTime(new Date())
                    .createdBy(enter.getUserId())
                    .createdTime(new Date())
                    .build();
            BeanUtils.copyProperties(stock, saveStock);
            BeanUtils.copyProperties(NotbuildOpeStockBill(enter, opePurchasB, stock), saveOpeStockBill);
        }

        if (saveStock != null) {

            if (saveStock.getMaterielProductId().equals(parts.getId())) {
                saveStock.setMaterielProductName(parts.getCnName());
                saveStock.setMaterielProductType(parts.getPartsType());
            }

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
                .sourceId(item.getPurchasId())
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

    private OpeStockBill buildOpeStockBill(HaveIdEnter enter, OpePurchasB item, OpeStock stock) {
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
