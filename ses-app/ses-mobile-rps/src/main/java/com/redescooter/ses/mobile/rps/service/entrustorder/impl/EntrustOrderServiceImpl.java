package com.redescooter.ses.mobile.rps.service.entrustorder.impl;

import com.redescooter.ses.api.common.enums.entrustorder.EntrustOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.service.RosEntrustOrderService;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeLogisticsOrderMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsCombinStockMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsPartsStockMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsScooterStockMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustOrderMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustPartsBMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dao.entrustorder.EntrustScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustCombinB;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustOrder;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustPartsB;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustScooterB;
import com.redescooter.ses.mobile.rps.dm.OpeLogisticsOrder;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.entrustorder.EntrustOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderDeliverParamDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.EntrustOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.QueryEntrustOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.entrustorder.QueryEntrustOrderResultDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2020/12/30 18:12
 */
@Slf4j
@Service
public class EntrustOrderServiceImpl implements EntrustOrderService {

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private RosEntrustOrderService rosEntrustOrderService;

    @Resource
    private EntrustOrderMapper entrustOrderMapper;

    @Resource
    private EntrustScooterBMapper entrustScooterBMapper;

    @Resource
    private EntrustCombinBMapper entrustCombinBMapper;

    @Resource
    private EntrustPartsBMapper entrustPartsBMapper;

    @Resource
    private EntrustProductSerialNumMapper entrustProductSerialNumMapper;

    @Resource
    private OpeLogisticsOrderMapper opeLogisticsOrderMapper;

    @Resource
    private ProductionPartsMapper partsMapper;

    @Autowired
    private OpeWmsScooterStockMapper opeWmsScooterStockMapper;

    @Autowired
    private OpeWmsCombinStockMapper opeWmsCombinStockMapper;

    @Autowired
    private OpeWmsPartsStockMapper opeWmsPartsStockMapper;

    @Autowired
    private OpeWmsStockSerialNumberMapper opeWmsStockSerialNumberMapper;

    @Override
    public Map<Integer, Integer> getEntrustOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResults = entrustOrderMapper.getEntrustOrderTypeCount();
        /**
         * {entrustType, totalCount}
         */
        Map<Integer, Integer> resultMap = countByStatusResults.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
        );

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (null == resultMap.get(item.getValue())) {
                resultMap.put(item.getValue(), 0);
            }
        }
        return resultMap;
    }

    @Override
    public PageResult<QueryEntrustOrderResultDTO> getEntrustOrderList(QueryEntrustOrderParamDTO paramDTO) {
        paramDTO.setStatus(paramDTO.getStatus() >= 1 ? 1:0);
        int count = entrustOrderMapper.countByEntrustOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }
        return PageResult.create(paramDTO, count, entrustOrderMapper.getEntrustOrderList(paramDTO));
    }

    @Override
    public EntrustOrderDetailDTO getEntrustOrderDetailById(IdEnter enter) {
        EntrustOrderDetailDTO entrustOrderDetail = entrustOrderMapper.getEntrustOrderDetailById(enter.getId());
        RpsAssert.isNull(entrustOrderDetail, ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getCode(),
                ExceptionCodeEnums.ORDER_IS_NOT_EXIST.getMessage());

        List<EntrustOrderProductDTO> productList = null;

        /**
         * ??????????????????????????? ---- 1????????? 2???????????? 3?????????
         */
        switch (entrustOrderDetail.getType()) {
            case 1:
                productList = entrustScooterBMapper.getEntrustOrderScooterByEntrustId(enter.getId());
                break;
            case 2:
                productList = entrustCombinBMapper.getEntrustOrderCombinByEntrustId(enter.getId());
                break;
            default:
                productList = entrustPartsBMapper.getEntrustOrderPartsByEntrustId(enter.getId());
                break;
        }

        entrustOrderDetail.setProductList(productList);
        return entrustOrderDetail;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult entrustOrderDeliver(EntrustOrderDeliverParamDTO paramDTO) {
        /**
         * ?????????????????????
         */
        OpeEntrustOrder opeEntrustOrder = entrustOrderMapper.getEntrustOrderById(paramDTO.getId());
        RpsAssert.isNull(opeEntrustOrder, ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.ENTRUST_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isFalse(EntrustOrderStatusEnum.TO_BE_DELIVERED.getStatus().equals(opeEntrustOrder.getEntrustStatus()),
                ExceptionCodeEnums.ENTRUST_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.ENTRUST_ORDER_STATUS_ERROR.getMessage());

        // ??????????????????????????????
        Integer deliveryQty;
        switch (opeEntrustOrder.getEntrustType()) {
            case 1:
                deliveryQty = entrustScooterBMapper.countEntrustScooterConsignorQtyByEntrustId(paramDTO.getId());
                break;
            case 2:
                deliveryQty = entrustCombinBMapper.countEntrustCombinConsignorQtyByEntrustId(paramDTO.getId());
                break;
            default:
                deliveryQty = entrustPartsBMapper.countEntrustPartsConsignorQtyByEntrustId(paramDTO.getId());
                break;
        }

        RpsAssert.isFalse(deliveryQty > opeEntrustOrder.getAlreadyConsignorQty(),
                ExceptionCodeEnums.DELIVERY_QTY_ERROR.getCode(), ExceptionCodeEnums.DELIVERY_QTY_ERROR.getMessage());

        /**
         * ??????????????????????????????
         */
//        opeEntrustOrder.setEntrustStatus(ConsignOrderStatusEnums.BE_SIGNED.getValue());
//        opeEntrustOrder.setActualDeliveryTime(new Date());
//        opeEntrustOrder.setAlreadyConsignorQty(deliveryQty);
//        opeEntrustOrder.setUpdatedBy(paramDTO.getUserId());
//        opeEntrustOrder.setUpdatedTime(new Date());
//        entrustOrderMapper.updateEntrustOrder(opeEntrustOrder);

        /**
         * ??????????????????
         */
        OpeLogisticsOrder opeLogisticsOrder = OpeLogisticsOrder.builder()
                .id(idAppService.getId(SequenceName.OPE_LOGISTICS_ORDER))
                .dr(0)
                .entrustId(paramDTO.getId())
                .logisticsCompany(paramDTO.getLogisticsCompany())
                .logisticsNo(paramDTO.getLogisticsNo())
                .remark(paramDTO.getRemark())
                .createdBy(paramDTO.getUserId())
                .createdTime(new Date())
                .updatedBy(paramDTO.getUserId())
                .updatedTime(new Date())
                .build();
        opeLogisticsOrderMapper.insert(opeLogisticsOrder);

        /**
         * ??????Aleks???????????????????????????????????????
         */
        IdEnter enter = new IdEnter(paramDTO.getId());
        rosEntrustOrderService.entrustOrderDeliver(enter);

        return new GeneralResult(paramDTO.getRequestId());
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        // ????????????????????????????????????????????????
        RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQty(),
                ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getCode(), ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getMessage());
        RpsAssert.isTrue(paramDTO.getIdClass() && StringUtils.isBlank(paramDTO.getSerialNum()),
                ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getMessage());

        Long userId = paramDTO.getUserId();
        Integer qty = paramDTO.getIdClass() ? 1 : paramDTO.getQty();

        // ????????????????????????
        if (paramDTO.getIdClass()) {
            OpeEntrustProductSerialNum opeEntrustProductSerialNum = entrustProductSerialNumMapper.getEntrustProductSerialNumBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNotNull(opeEntrustProductSerialNum, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());
        }

        /**
         * ???????????????????????????????????????
         */
        String serialNum = null;
        String tabletSn = null;
        String bluetooth = null;
        String lot = null;
        Long relationId = null;
        switch (paramDTO.getProductType()) {
            case 1:
                // ????????????????????????????????????,???????????????????????????,????????????????????????
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeEntrustScooterB opeEntrustScooterB = entrustScooterBMapper.getEntrustScooterById(paramDTO.getProductId());
                RpsAssert.isNull(opeEntrustScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // ?????????????????????????????????????????????
                serialNum = opeEntrustScooterB.getDef1();
                tabletSn = opeEntrustScooterB.getDef2();
                bluetooth = opeEntrustScooterB.getDef3();
                lot = opeEntrustScooterB.getDef4();

                if (StringUtils.isNotBlank(serialNum)) {
                    serialNum += "," + paramDTO.getSerialNum();
                } else {
                    serialNum = paramDTO.getSerialNum();
                }
                if (StringUtils.isNotBlank(tabletSn)) {
                    tabletSn += "," + paramDTO.getTabletSn();
                } else {
                    tabletSn = paramDTO.getTabletSn();
                }
                if (StringUtils.isNotBlank(bluetooth)) {
                    bluetooth += "," + paramDTO.getBluetoothMacAddress();
                } else {
                    bluetooth = paramDTO.getBluetoothMacAddress();
                }
                if (StringUtils.isNotBlank(lot)) {
                    lot += "," + paramDTO.getLot();
                } else {
                    lot = paramDTO.getLot();
                }

                opeEntrustScooterB.setConsignorQty(opeEntrustScooterB.getConsignorQty() + 1); // ???????????????+1
                opeEntrustScooterB.setUpdatedBy(userId);
                opeEntrustScooterB.setUpdatedTime(new Date());
                opeEntrustScooterB.setDef1(serialNum);
                opeEntrustScooterB.setDef2(tabletSn);
                opeEntrustScooterB.setDef3(bluetooth);
                opeEntrustScooterB.setDef4(lot);
                entrustScooterBMapper.updateEntrustScooter(opeEntrustScooterB);

                // ???????????????id
                /*Long groupId = opeEntrustScooterB.getGroupId();
                Long colorId = opeEntrustScooterB.getColorId();
                LambdaQueryWrapper<OpeWmsScooterStock> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(OpeWmsScooterStock::getDr, 0);
                wrapper.eq(OpeWmsScooterStock::getGroupId, groupId);
                wrapper.eq(OpeWmsScooterStock::getColorId, colorId);
                wrapper.last("limit 1");
                List<OpeWmsScooterStock> list = opeWmsScooterStockMapper.selectList(wrapper);
                if (CollectionUtils.isNotEmpty(list)) {
                    OpeWmsScooterStock scooterStock = list.get(0);
                    relationId = scooterStock.getId();
                }*/
                break;
            case 2:
                // ????????????????????????????????????,???????????????????????????,????????????????????????
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeEntrustCombinB opeEntrustCombinB = entrustCombinBMapper.getEntrustCombinById(paramDTO.getProductId());
                RpsAssert.isNull(opeEntrustCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                serialNum = opeEntrustCombinB.getDef1();
                tabletSn = opeEntrustCombinB.getDef2();
                bluetooth = opeEntrustCombinB.getDef3();
                lot = opeEntrustCombinB.getDef4();

                if (StringUtils.isNotBlank(serialNum)) {
                    serialNum += "," + paramDTO.getSerialNum();
                } else {
                    serialNum = paramDTO.getSerialNum();
                }
                if (StringUtils.isNotBlank(tabletSn)) {
                    tabletSn += "," + paramDTO.getTabletSn();
                } else {
                    tabletSn = paramDTO.getTabletSn();
                }
                if (StringUtils.isNotBlank(bluetooth)) {
                    bluetooth += "," + paramDTO.getBluetoothMacAddress();
                } else {
                    bluetooth = paramDTO.getBluetoothMacAddress();
                }
                if (StringUtils.isNotBlank(lot)) {
                    lot += "," + paramDTO.getLot();
                } else {
                    lot = paramDTO.getLot();
                }

                opeEntrustCombinB.setConsignorQty(opeEntrustCombinB.getConsignorQty() + 1);
                opeEntrustCombinB.setUpdatedBy(userId);
                opeEntrustCombinB.setUpdatedTime(new Date());
                opeEntrustCombinB.setDef1(serialNum);
                opeEntrustCombinB.setDef2(tabletSn);
                opeEntrustCombinB.setDef3(bluetooth);
                opeEntrustCombinB.setDef4(lot);
                entrustCombinBMapper.updateEntrustCombin(opeEntrustCombinB);

                // ???????????????id
                /*Long bomId = opeEntrustCombinB.getProductionCombinBomId();
                LambdaQueryWrapper<OpeWmsCombinStock> qw = new LambdaQueryWrapper<>();
                qw.eq(OpeWmsCombinStock::getDr, 0);
                qw.eq(OpeWmsCombinStock::getProductionCombinBomId, bomId);
                qw.last("limit 1");
                List<OpeWmsCombinStock> combinStockList = opeWmsCombinStockMapper.selectList(qw);
                if (CollectionUtils.isNotEmpty(combinStockList)) {
                    OpeWmsCombinStock combinStock = combinStockList.get(0);
                    relationId = combinStock.getId();
                }*/
                break;
            default:
                RpsAssert.isBlank(paramDTO.getPartsNo(), ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getMessage());
                // ??????????????????????????????????????????????????????????????????
                Integer idClass = partsMapper.getPartsIdClassById(paramDTO.getBomId(), paramDTO.getPartsNo());
                RpsAssert.isNull(idClass, ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                        ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
                boolean flag = 0 == idClass ? false : true;
                RpsAssert.isFalse(paramDTO.getIdClass() == flag, ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeEntrustPartsB opeEntrustPartsB = entrustPartsBMapper.getEntrustPartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeEntrustPartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // ?????????????????????????????????
                if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
                    opeEntrustPartsB.setConsignorQty(opeEntrustPartsB.getConsignorQty() + qty);
                } else {
                    // ??????????????????????????????, ??????????????????????????????????????????????????????????????????
                    RpsAssert.isTrue(!qty.equals(opeEntrustPartsB.getQty()),ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(),
                            ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());
                    RpsAssert.isTrue(opeEntrustPartsB.getConsignorQty() > 0,ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                            ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());

                    opeEntrustPartsB.setConsignorQty(qty);
                }

                serialNum = opeEntrustPartsB.getDef1();
                tabletSn = opeEntrustPartsB.getDef2();
                bluetooth = opeEntrustPartsB.getDef3();
                lot = opeEntrustPartsB.getDef4();

                if (StringUtils.isNotBlank(serialNum)) {
                    serialNum += "," + paramDTO.getSerialNum();
                } else {
                    serialNum = paramDTO.getSerialNum();
                }
                if (StringUtils.isNotBlank(tabletSn)) {
                    tabletSn += "," + paramDTO.getTabletSn();
                } else {
                    tabletSn = paramDTO.getTabletSn();
                }
                if (StringUtils.isNotBlank(bluetooth)) {
                    bluetooth += "," + paramDTO.getBluetoothMacAddress();
                } else {
                    bluetooth = paramDTO.getBluetoothMacAddress();
                }
                if (StringUtils.isNotBlank(lot)) {
                    lot += "," + paramDTO.getLot();
                } else {
                    lot = paramDTO.getLot();
                }

                opeEntrustPartsB.setUpdatedBy(userId);
                opeEntrustPartsB.setUpdatedTime(new Date());
                opeEntrustPartsB.setDef1(serialNum);
                opeEntrustPartsB.setDef2(tabletSn);
                opeEntrustPartsB.setDef3(bluetooth);
                opeEntrustPartsB.setDef4(lot);
                entrustPartsBMapper.updateEntrustPartsB(opeEntrustPartsB);

                // ???????????????id
                /*Long partsId = opeEntrustPartsB.getPartsId();
                LambdaQueryWrapper<OpeWmsPartsStock> lqw = new LambdaQueryWrapper<>();
                lqw.eq(OpeWmsPartsStock::getDr, 0);
                lqw.eq(OpeWmsPartsStock::getPartsId, partsId);
                lqw.last("limit 1");
                List<OpeWmsPartsStock> partsStockList = opeWmsPartsStockMapper.selectList(lqw);
                if (CollectionUtils.isNotEmpty(partsStockList)) {
                    OpeWmsPartsStock partsStock = partsStockList.get(0);
                    relationId = partsStock.getId();
                }*/
                break;
        }

        /**
         * ????????????????????????????????????(?????????????????????)
         */
        if (paramDTO.getIdClass()) {
            OpeEntrustProductSerialNum entrustProductSerialNum = new OpeEntrustProductSerialNum();
            entrustProductSerialNum.setId(idAppService.getId(SequenceName.OPE_ENTRUST_PRODUCT_SERIAL_NUM));
            entrustProductSerialNum.setRelationId(paramDTO.getProductId());
            entrustProductSerialNum.setRelationType(paramDTO.getProductType());
            entrustProductSerialNum.setLot(paramDTO.getLot());
            entrustProductSerialNum.setIdClass(StringUtils.isNotBlank(paramDTO.getSerialNum()) ? 1 : 0);
            entrustProductSerialNum.setProductId(paramDTO.getBomId());
            entrustProductSerialNum.setProductType(paramDTO.getProductType());
            entrustProductSerialNum.setSerialNum(paramDTO.getSerialNum());
            entrustProductSerialNum.setQty(qty);
            entrustProductSerialNum.setCreatedBy(userId);
            entrustProductSerialNum.setCreatedTime(new Date());
            entrustProductSerialNum.setUpdatedBy(userId);
            entrustProductSerialNum.setUpdatedTime(new Date());
            entrustProductSerialNumMapper.insertEntrustProductSerialNum(entrustProductSerialNum);
        }

        // ??????ope_wms_parts_stock???
        /*if (null != relationId) {
            OpeWmsStockSerialNumber number = new OpeWmsStockSerialNumber();
            number.setId(idAppService.getId(SequenceName.OPE_WMS_STOCK_SERIAL_NUMBER));
            number.setDr(0);
            number.setTenantId(paramDTO.getTenantId());
            number.setDeptId(paramDTO.getOpeDeptId());
            number.setRelationType(paramDTO.getProductType());
            number.setRelationId(relationId);
            number.setStockType(1);
            number.setRsn(serialNum);
            number.setStockStatus(1);
            number.setLotNum(paramDTO.getLot());
            number.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
            number.setCreatedBy(paramDTO.getUserId());
            number.setCreatedTime(new Date());
            opeWmsStockSerialNumberMapper.insert(number);
        }*/
        return new GeneralResult(paramDTO.getRequestId());
    }

}
