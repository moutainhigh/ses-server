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
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * ???????????????????????????
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
     * ?????????????????????????????????
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

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public HaveIdPartsResult haveIdPartsResult(HaveIdEnter enter) {

        OpePurchasB opePurchasB = opePurchasBService.getById(enter.getId());
        if (opePurchasB == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //?????????????????????
        OpePurchas opePurchas = opePurchasService.getById(opePurchasB.getPurchasId());
        if (opePurchas == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.QC_COMPLETED.getValue()) && !StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.RETURN.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        //??????????????????
        OpeProductionParts partsData = opeProductionPartsService.getById(opePurchasB.getPartId());
        if (partsData.getIdCalss() != 1) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        //????????????????????????????????????
        OpeStockBill opeStockBill = saveStockBill(enter.getUserId(), opePurchasB, partsData);

        //????????? ??????
        opeStockBillService.save(opeStockBill);

        //????????????id
        QueryWrapper<OpeWhse> opeWhse = new QueryWrapper<>();
        opeWhse.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        opeWhse.eq(OpeWhse.COL_DR, 0);
        OpeWhse opeWhsegetid = opeWhseService.getOne(opeWhse);
        if (opeWhsegetid == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
        }

        //???????????????
        OpePurchasBQcItem opePurchasBQcItem = opePurchasBQcItemService.getOne(new LambdaQueryWrapper<OpePurchasBQcItem>()
                .eq(OpePurchasBQcItem::getPurchasBId, opePurchasB.getId())
                .eq(OpePurchasBQcItem::getQcResult, QcStatusEnums.PASS.getValue())
                .eq(OpePurchasBQcItem::getSerialNum, enter.getSerialNum())
                .eq(OpePurchasBQcItem::getDr, 0)
        );

        if (opePurchasBQcItem == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getCode(), ExceptionCodeEnums.SERIAL_NUMBER_IS_WRONG.getMessage());
        }

        //????????????????????????
        OpeStockPurchas opeStockPurchas = buildOpeStockPurchas(enter.getUserId(), 1, partsData, opeStockBill, opePurchasBQcItem.getBatchNo(), enter.getSerialNum());
        //??????????????????
        opeStockPurchasService.save(opeStockPurchas);
        if (opePurchasB.getInWaitWhQty() != null && opePurchas.getInWaitWhTotal() != null) {
            //??????????????????????????????
            opePurchasB.setInWaitWhQty(opePurchasB.getInWaitWhQty() - 1);
            opePurchasB.setUpdatedBy(enter.getUserId());
            opePurchasB.setUpdatedTime(new Date());
            opePurchasBService.updateById(opePurchasB);
            //?????????????????????
            opePurchas.setInWaitWhTotal(opePurchas.getInWaitWhTotal() - 1);
            opePurchas.setId(opePurchasB.getPurchasId());
            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
            opePurchasService.updateById(opePurchas);
        } else {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (opePurchas.getInWaitWhTotal() == 0) {
            //?????????????????????
            opePurchas.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
            opePurchasService.updateById(opePurchas);

            //??????
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
     * ???Id ??????
     *
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
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
        //?????????????????????
        if (!StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.QC_COMPLETED.getValue()) && !StringUtils.equals(opePurchas.getStatus(), PurchasingStatusEnums.RETURN.getValue())) {
            throw new SesMobileRpsException(ExceptionCodeEnums.STATUS_IS_ILLEGAL.getCode(), ExceptionCodeEnums.STATUS_IS_ILLEGAL.getMessage());
        }
        OpeProductionParts partsData = opeProductionPartsService.getById(opePurchasB.getPartId());
        if (partsData.getIdCalss() != 0) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }

        //????????????????????????????????????
        OpeStockBill opeStockBill = saveStockBill(enter.getUserId(), opePurchasB, partsData);

        //????????? ??????
        opeStockBillService.save(opeStockBill);

        //????????????id
        QueryWrapper<OpeWhse> opeWhse = new QueryWrapper<>();
        opeWhse.eq(OpeWhse.COL_TYPE, WhseTypeEnums.PURCHAS.getValue());
        opeWhse.eq(OpeWhse.COL_DR, 0);

        OpeWhse opeWhsegetid = opeWhseService.getOne(opeWhse);
        if (opeWhsegetid == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //???????????????
        OpePurchasBQcItem opePurchasBQcItem = opePurchasBQcItemService.getOne(new LambdaQueryWrapper<OpePurchasBQcItem>()
                .eq(OpePurchasBQcItem::getPurchasBId, opePurchasB.getId())
                .eq(OpePurchasBQcItem::getBatchNo, enter.getBatchNo())
                .eq(OpePurchasBQcItem::getQcResult, QcStatusEnums.PASS.getValue())
                .eq(OpePurchasBQcItem::getDr, 0)
        );
        if (opePurchasBQcItem == null) {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        //????????????????????????
        OpeStockPurchas opeStockPurchas = buildOpeStockPurchas(enter.getUserId(), opePurchasB.getInWaitWhQty(), partsData, opeStockBill, opePurchasBQcItem.getBatchNo(), null);
        //??????????????????
        opeStockPurchasService.save(opeStockPurchas);

        if (opePurchasB.getInWaitWhQty() != null && opePurchas.getInWaitWhTotal() != null) {
            //??????????????????????????????
            opePurchasB.setInWaitWhQty(opePurchasB.getInWaitWhQty() - enter.getInWaitWhQty());
            opePurchasB.setUpdatedBy(enter.getUserId());
            opePurchasB.setUpdatedTime(new Date());
            opePurchasBService.updateById(opePurchasB);
            //?????????????????????
            opePurchas.setInWaitWhTotal(opePurchas.getInWaitWhTotal() - enter.getInWaitWhQty());
            opePurchas.setId(opePurchasB.getPurchasId());
            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
            opePurchasService.updateById(opePurchas);
        } else {
            throw new SesMobileRpsException(ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.PURCHAS_IS_NOT_EXIST.getMessage());
        }
        if (opePurchas.getInWaitWhTotal() == 0) {
            //?????????????????????
            opePurchas.setStatus(PurchasingStatusEnums.IN_PURCHASING_WH.getValue());
            opePurchas.setUpdatedBy(enter.getUserId());
            opePurchas.setUpdatedTime(new Date());
            opePurchasService.updateById(opePurchas);

            //??????
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
     * ??????????????????
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
        //??????????????????
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
                //????????? ????????????
                opeStock.setAvailableTotal(parts.getIdCalss() == 1 ? opeStock.getAvailableTotal() + 1
                        : opeStock.getAvailableTotal() + opePurchasB.getTotalCount());
                opeStock.setIntTotal(parts.getIdCalss() == 1 ? opeStock.getIntTotal() + 1
                        : opeStock.getIntTotal() + opePurchasB.getTotalCount());
                opeStock.setUpdatedBy(userId);
                opeStock.setUpdatedTime(new Date());
            }
        }

        if (opeStock == null) {
            //?????????
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
        //????????????
        opeStockService.saveOrUpdate(opeStock);
        //????????? ??????
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
//     * ????????? ??????
//     *
//     * @param qty
//     * @param parts
//     */
//    private void freedWaitProduct(OpeParts parts, int qty) {
//        //????????????
//        OpeWhse opeWhse = opeWhseService.getOne(new LambdaQueryWrapper<OpeWhse>().eq(OpeWhse::getType, WhseTypeEnums.PURCHAS.getValue()));
//        if (opeWhse == null) {
//            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getCode(),ExceptionCodeEnums.WAREHOUSE_IS_NOT_EXIST.getMessage());
//        }
//
//        //?????? ??????
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
