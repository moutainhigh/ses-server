package com.redescooter.ses.mobile.rps.service.purchasinwh.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.production.SourceTypeEnums;
import com.redescooter.ses.api.common.enums.production.StockBillStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingEventEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.PurchasingStatusEnums;
import com.redescooter.ses.api.common.enums.production.purchasing.QcStatusEnums;
import com.redescooter.ses.api.common.enums.rps.StockProductPartStatusEnums;
import com.redescooter.ses.api.common.enums.whse.WhseTypeEnums;
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
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    private OpeProductionPartsService opeProductionPartsService;

    @Autowired
    private OpeWhseService opeWhseService;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private OpeStockPurchasService opeStockPurchasService;

    @Autowired
    private OpePurchasBQcItemService opePurchasBQcItemService;

    @Autowired
    private ReceiptTraceService receiptTraceService;

    /**
     * 采购仓库待入库信息
     *
     * @param
     * @return
     */
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

    @Transactional(rollbackFor = Exception.class)
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
        OpeProductionParts partsData = opeProductionPartsService.getById(opePurchasB.getPartId());
        if (partsData.getIdCalss() != 1) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        //生成入库单、库存数据更新
        OpeStockBill opeStockBill = saveStockBill(enter.getUserId(), opePurchasB, partsData);

        //入库单 保存
        opeStockBillService.save(opeStockBill);

        //查询仓库id
        QueryWrapper<OpeWhse> opeWhse = new QueryWrapper<>();
        opeWhse.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        opeWhse.eq(OpeWhse.COL_DR, 0);
        OpeWhse opeWhsegetid = opeWhseService.getOne(opeWhse);
        if (opeWhsegetid == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        //校验批次号
        OpePurchasBQcItem opePurchasBQcItem = opePurchasBQcItemService.getOne(new LambdaQueryWrapper<OpePurchasBQcItem>()
                .eq(OpePurchasBQcItem::getPurchasBId, opePurchasB.getId())
                .eq(OpePurchasBQcItem::getQcResult, QcStatusEnums.PASS.getValue())
                .eq(OpePurchasBQcItem::getSerialNum, enter.getSerialNum())
                .eq(OpePurchasBQcItem::getDr, 0)
        );

        if (opePurchasBQcItem == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getCode(), ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getMessage());
        }

        //入库条目数据增加
        OpeStockPurchas opeStockPurchas = buildOpeStockPurchas(enter.getUserId(), 1, partsData, opeStockBill, opePurchasBQcItem.getBatchNo(), enter.getSerialNum());
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

            //节点
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opePurchas.getId());
            saveNodeEnter.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            saveNodeEnter.setEvent(PurchasingEventEnums.IN_PURCHASING_WH.getValue());
            saveNodeEnter.setMemo(null);
            receiptTraceService.savePurchasingNode(saveNodeEnter);
        }

        return HaveIdPartsResult.builder()
                .id(opePurchasB.getId())
                .inWaitWhQty(opePurchasB.getInWaitWhQty())
                .build()
                ;
    }


    /**
     * 无Id 入库
     *
     * @param enter
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
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
        OpeProductionParts partsData = opeProductionPartsService.getById(opePurchasB.getPartId());
        if (partsData.getIdCalss() != 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        //生成入库单、库存数据更新
        OpeStockBill opeStockBill = saveStockBill(enter.getUserId(), opePurchasB, partsData);

        //入库单 保存
        opeStockBillService.save(opeStockBill);

        //查询仓库id
        QueryWrapper<OpeWhse> opeWhse = new QueryWrapper<>();
        opeWhse.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        opeWhse.eq(OpeWhse.COL_DR, 0);

        OpeWhse opeWhsegetid = opeWhseService.getOne(opeWhse);
        if (opeWhsegetid == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //查询批次号
        OpePurchasBQcItem opePurchasBQcItem = opePurchasBQcItemService.getOne(new LambdaQueryWrapper<OpePurchasBQcItem>()
                .eq(OpePurchasBQcItem::getPurchasBId, opePurchasB.getId())
                .eq(OpePurchasBQcItem::getBatchNo, enter.getBatchNo())
                .eq(OpePurchasBQcItem::getQcResult, QcStatusEnums.PASS.getValue())
                .eq(OpePurchasBQcItem::getDr, 0)
        );
        if (opePurchasBQcItem == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //入库条目数据增加
        OpeStockPurchas opeStockPurchas = buildOpeStockPurchas(enter.getUserId(), opePurchasB.getInWaitWhQty(), partsData, opeStockBill, opePurchasBQcItem.getBatchNo(), null);
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

            //节点
            SaveNodeEnter saveNodeEnter = new SaveNodeEnter();
            BeanUtils.copyProperties(enter, saveNodeEnter);
            saveNodeEnter.setId(opePurchas.getId());
            saveNodeEnter.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            saveNodeEnter.setEvent(PurchasingEventEnums.IN_PURCHASING_WH.getValue());
            saveNodeEnter.setMemo(null);
            receiptTraceService.savePurchasingNode(saveNodeEnter);
        }

        return NotIdPartsSucceedResult.builder()
                .id(opePurchasB.getId())
                .inWaitWhQty(opePurchasB.getInWaitWhQty())
                .build();
    }

    private OpeStockPurchas buildOpeStockPurchas(Long userId, int inwhCount, OpeProductionParts partsData,
                                                 OpeStockBill opeStockBill, String bathcNo, String serialNum) {
        OpeStockPurchas opeStockPurchas = OpeStockPurchas.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_PURCHAS))
                .dr(0)
                .status(StockProductPartStatusEnums.AVAILABLE.getValue())
                .stockId(opeStockBill.getStockId())
                .partId(partsData.getId())
                .lot(bathcNo)
                .partNumber(partsData.getPartsNo())
                .principalId(userId)
                .inStockTime(new Date())
                .serialNumber(partsData.getIdCalss() == 1 ? serialNum : null)
                .revision(0)
                .createdBy(userId)
                .createdTime(new Date())
                .updatedBy(userId)
                .updatedTime(new Date())
                .inStockBillId(opeStockBill.getId())
                .outPrincipalId(0L)
                .outStockBillId(0L)
                .outStockTime(null)
                .inWhQty(inwhCount)
                .availableQty(inwhCount)
                .build();
        return opeStockPurchas;
    }

    /**
     * 入库信息展示
     *
     * @param
     * @return
     */

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
        OpeProductionParts partsData = opeProductionPartsService.getById(opePurchasB.getPartId());
        if (partsData.getIdCalss() != 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());

        }
        return notIdPartsResult;
    }

    private OpeStockBill saveStockBill(Long userId, OpePurchasB opePurchasB, OpeProductionParts parts) {
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

        if (opeStock != null) {
            if (opePurchasB.getPartId().equals(opeStock.getMaterielProductId())) {
                //有库存 库存累计
                opeStock.setAvailableTotal(parts.getIdCalss() == 1 ? opeStock.getAvailableTotal() + 1
                        : opeStock.getAvailableTotal() + opePurchasB.getTotalCount());
                opeStock.setIntTotal(parts.getIdCalss() == 1 ? opeStock.getIntTotal() + 1
                        : opeStock.getIntTotal() + opePurchasB.getTotalCount());
                opeStock.setUpdatedBy(userId);
                opeStock.setUpdatedTime(new Date());
            }
        }

        if (opeStock == null) {
            //无库存
            opeStock = OpeStock.builder()
                    .id(idAppService.getId(SequenceName.OPE_STOCK))
                    .dr(0)
                    .userId(0L)
                    .tenantId(0L)
                    .whseId(opeWhse.getId())
                    .intTotal(parts.getIdCalss() == 1 ? 1 : opePurchasB.getTotalCount())
                    .availableTotal(parts.getIdCalss() == 1 ? 1 : opePurchasB.getTotalCount())
                    .outTotal(0)
                    .wornTotal(0)
                    .lockTotal(0)
                    .waitProductTotal(0)
                    .waitStoredTotal(0)
                    .materielProductId(opePurchasB.getPartId())
                    .materielProductName(parts.getCnName())
                    .materielProductType(String.valueOf(parts.getPartsType()))
                    .revision(0)
                    .updatedBy(userId)
                    .updatedTime(new Date())
                    .createdBy(userId)
                    .createdTime(new Date())
                    .build();
        }
        //库存更新
        opeStockService.saveOrUpdate(opeStock);
        //入库单 生成
        return buildOpeStockBill(userId, opePurchasB, opeStock.getId(), parts);
    }

    private OpeStockBill buildOpeStockBill(Long userId, OpePurchasB item, Long stockId, OpeProductionParts parts) {
        return OpeStockBill.builder()
                .id(idAppService.getId(SequenceName.OPE_STOCK_BILL))
                .dr(0)
                .tenantId(0L)
                .userId(userId)
                .stockId(stockId)
                .direction(InOutWhEnums.IN.getValue())
                .status(StockBillStatusEnums.NORMAL.getValue())
                .sourceId(item.getPurchasId())
                .total(parts.getIdCalss() == 1 ? 1 : item.getTotalCount())
                .sourceType(SourceTypeEnums.PURCHAS.getValue())
                .principalId(userId)
                .operatineTime(new Date())
                .revision(0)
                .createdBy(userId)
                .createdTime(new Date())
                .updatedBy(userId)
                .updatedTime(new Date())
                .build();
    }

//    /**
//     * 待生产 锚点
//     *
//     * @param qty
//     * @param parts
//     */
//    private void freedWaitProduct(OpeParts parts, int qty) {
//        //查询仓库
//        OpeWhse opeWhse = opeWhseService.getOne(new LambdaQueryWrapper<OpeWhse>().eq(OpeWhse::getType, WhseTypeEnums.PURCHAS.getValue()));
//        if (opeWhse == null) {
//            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
//        }
//
//        //查询 库存
//        OpeStock opeStock = opeStockService.getOne(new LambdaQueryWrapper<OpeStock>()
//                .eq(OpeStock::getMaterielProductId, parts.getId())
//                .eq(OpeStock::getMaterielProductType, BomCommonTypeEnums.getValueByCode(parts.getPartsType()))
//                .eq(OpeStock::getWhseId,opeWhse.getId()));
//        if (opeStock == null) {
//            throw new SesMobileRpsException(ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.STOCK_IS_NOT_EXIST.getMessage());
//        }
//        opeStock.setWaitProductTotal(opeStock.getWaitProductTotal() - qty);
//        opeStock.setUpdatedTime(new Date());
//        opeStockService.updateById(opeStock);
//    }

}
