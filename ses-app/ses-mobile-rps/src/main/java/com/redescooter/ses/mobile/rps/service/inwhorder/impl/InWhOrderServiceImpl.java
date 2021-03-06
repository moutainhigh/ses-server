package com.redescooter.ses.mobile.rps.service.inwhorder.impl;

import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.production.InOutWhEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.InWhTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.NewInWhouseOrderStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.config.component.SaveWmsStockDataComponent;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhOrderMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dao.inwhorder.InWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.QcOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dao.wms.WmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.exception.SesMobileRpsException;
import com.redescooter.ses.mobile.rps.service.inwhorder.InWhOrderService;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.InWhOrderProductDetailDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.inwhorder.QueryInWhOrderResultDTO;
import com.redescooter.ses.mobile.rps.vo.outwhorder.QueryProductDetailParamDTO;
import com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound.CountByOrderTypeParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2021/1/15 18:09
 */
@Slf4j
@Service
public class InWhOrderServiceImpl implements InWhOrderService {

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private ScooterService scooterService;

    @Autowired
    private InWhOrderMapper inWhOrderMapper;

    @Autowired
    private InWhouseScooterBMapper inWhouseScooterBMapper;

    @Autowired
    private InWhouseCombinBMapper inWhouseCombinBMapper;

    @Autowired
    private InWhousePartsBMapper inWhousePartsBMapper;

    @Autowired
    private ProductionScooterBomMapper scooterBomMapper;

    @Autowired
    private ProductionCombinBomMapper combinBomMapper;

    @Autowired
    private ProductionPartsMapper partsMapper;

    @Autowired
    private InWhouseOrderSerialBindMapper inWhouseOrderSerialBindMapper;

    @Autowired
    private QcOrderSerialBindMapper qcOrderSerialBindMapper;

    @Autowired
    private WmsStockSerialNumberMapper wmsStockSerialNumberMapper;

    @Autowired
    private SaveWmsStockDataComponent saveWmsStockDataComponent;

    @Override
    public Map<Integer, Integer> getInWarehouseOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = inWhOrderMapper.getInWarehouseOrderTypeCount();
        /**
         * {orderType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
        );

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    @Override
    public Map<Integer, Integer> getInWarehouseTypeCount(CountByOrderTypeParamDTO paramDTO) {
        List<CountByStatusResult> countByStatusResultList = inWhOrderMapper.getInWarehouseTypeCount(paramDTO);
        /**
         * {inWhType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult:: getTotalCount)
        );

        for (InWhTypeEnums item : InWhTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        return map;
    }

    @Override
    public PageResult<QueryInWhOrderResultDTO> getInWarehouseOrderList(QueryInWhOrderParamDTO paramDTO) {
        int count = inWhOrderMapper.countByInWhOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }
        return PageResult.create(paramDTO, count, inWhOrderMapper.getInWarehouseOrderList(paramDTO));
    }

    @Override
    public InWhOrderDetailDTO getInWarehouseOrderDetailById(IdEnter enter) {
        InWhOrderDetailDTO inWhOrderDetail = inWhOrderMapper.getInWhOrderDetailById(enter.getId());
        RpsAssert.isNull(inWhOrderDetail, ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getCode(), ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getMessage());

        List<InWhOrderProductDTO> productList = null;
        /**
         * ??????????????????????????? 1?????? 2????????? 3??????
         */
        switch (inWhOrderDetail.getOrderType()) {
            case 1:
                productList = inWhouseScooterBMapper.getInWhOrderScooterByInWhId(inWhOrderDetail.getId());
                break;
            case 2:
                productList = inWhouseCombinBMapper.getInWhOrderCombinByInWhId(inWhOrderDetail.getId());
                break;
            default:
                productList = inWhousePartsBMapper.getInWhOrderPartsByInWhId(inWhOrderDetail.getId());
                break;
        }
        inWhOrderDetail.setProductList(productList);
        return inWhOrderDetail;
    }

    @Override
    public InWhOrderProductDetailDTO getProductDetailByProductId(QueryProductDetailParamDTO paramDTO) {
        InWhOrderProductDetailDTO inWhOrderProductDetail = null;

        /**
         * ???????????????????????????, 1?????? 2????????? 3??????
         */
        switch (paramDTO.getProductType()) {
            case 1:
                inWhOrderProductDetail = inWhouseScooterBMapper.getInWhOrderScooterById(paramDTO.getProductId());
                break;
            case 2:
                inWhOrderProductDetail = inWhouseCombinBMapper.getInWhOrderCombinById(paramDTO.getProductId());
                break;
            default:
                inWhOrderProductDetail = inWhousePartsBMapper.getInWhOrderPartsById(paramDTO.getProductId());
                break;
        }
        return inWhOrderProductDetail;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public SaveScanCodeResultDTO saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        SaveScanCodeResultDTO resultDTO = new SaveScanCodeResultDTO();
        // ????????????????????????????????????????????????
        RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQty(),
                ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getCode(), ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getMessage());
        RpsAssert.isTrue(paramDTO.getIdClass() && StringUtils.isBlank(paramDTO.getSerialNum()),
                ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getMessage());

        // ????????????
        Integer qty = paramDTO.getIdClass() ? 1 : paramDTO.getQty();
        Integer remainingQty = 0;
        String name;
        String defaultSerialNum = null;

        // ??????????????????
        if (paramDTO.getIdClass()) {
            OpeInWhouseOrderSerialBind opeInWhouseOrderSerialBind = inWhouseOrderSerialBindMapper
                    .getInWhouseOrderSerialBindBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNotNull(opeInWhouseOrderSerialBind, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());
        }

        /**
         * ?????????????????????????????????????????? 1?????? 2????????? 3??????
         */
        switch (paramDTO.getProductType()) {
            case 1:
                // ????????????????????????????????????,???????????????????????????,????????????????????????
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeInWhouseScooterB opeInWhouseScooterB = inWhouseScooterBMapper.getInWhouseScooterById(paramDTO.getProductId());
                RpsAssert.isNull(opeInWhouseScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // ???????????????????????????????????????
                opeInWhouseScooterB.setActInWhQty(opeInWhouseScooterB.getActInWhQty() + 1);
                opeInWhouseScooterB.setUpdatedBy(paramDTO.getUserId());
                opeInWhouseScooterB.setUpdatedTime(new Date());
                inWhouseScooterBMapper.updateInWhouseScooter(opeInWhouseScooterB);

                remainingQty = opeInWhouseScooterB.getInWhQty() - opeInWhouseScooterB.getActInWhQty();
                name = scooterBomMapper.getScooterModelById(paramDTO.getBomId());
                break;
            case 2:
                // ????????????????????????????????????,???????????????????????????,????????????????????????
                RpsAssert.isFalse(paramDTO.getIdClass(), ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                        ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

                OpeInWhouseCombinB opeInWhouseCombinB = inWhouseCombinBMapper.getInWhouseCombinById(paramDTO.getProductId());
                RpsAssert.isNull(opeInWhouseCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());

                // ??????????????????????????????????????????
                opeInWhouseCombinB.setActInWhQty(opeInWhouseCombinB.getActInWhQty() + 1);
                opeInWhouseCombinB.setUpdatedBy(paramDTO.getUserId());
                opeInWhouseCombinB.setUpdatedTime(new Date());
                inWhouseCombinBMapper.updateInWhouseCombin(opeInWhouseCombinB);

                remainingQty = opeInWhouseCombinB.getInWhQty() - opeInWhouseCombinB.getActInWhQty();
                name = combinBomMapper.getCombinCnNameById(paramDTO.getBomId());
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

                OpeInWhousePartsB opeInWhousePartsB = inWhousePartsBMapper.getInWhousePartsById(paramDTO.getProductId());
                RpsAssert.isNull(opeInWhousePartsB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
                // ECU???????????????????????????mac??????
                RpsAssert.isTrue(BomCommonTypeEnums.ECU_METER.getValue().equals(String.valueOf(opeInWhousePartsB.getPartsType()))
                        && StringUtils.isBlank(paramDTO.getBluetoothMacAddress()), ExceptionCodeEnums.BLUETOOTH_MAC_ADDRESS_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.BLUETOOTH_MAC_ADDRESS_IS_EMPTY.getMessage());

                // ???????????????????????????????????????
                if (paramDTO.getIdClass()) {
                    opeInWhousePartsB.setActInWhQty(opeInWhousePartsB.getActInWhQty() + qty);
                    defaultSerialNum = qcOrderSerialBindMapper.getDefaultSerialNumBySerialNum(paramDTO.getSerialNum());
                } else {
                    // ??????????????????????????????, ??????????????????????????????????????????????????????????????????
                    RpsAssert.isTrue(!qty.equals(opeInWhousePartsB.getInWhQty()),ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(),
                            ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());
                    RpsAssert.isTrue(opeInWhousePartsB.getActInWhQty() > 0,ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                            ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());

                    opeInWhousePartsB.setActInWhQty(qty);
                }
                opeInWhousePartsB.setUpdatedBy(paramDTO.getUserId());
                opeInWhousePartsB.setUpdatedTime(new Date());
                inWhousePartsBMapper.updateInWhouseParts(opeInWhousePartsB);

                remainingQty = opeInWhousePartsB.getInWhQty() - opeInWhousePartsB.getActInWhQty();
                name = partsMapper.getPartsCnNameById(paramDTO.getBomId());
                break;
        }

        RpsAssert.isBlank(name, ExceptionCodeEnums.PRODUCT_NAME_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_NAME_IS_EMPTY.getMessage());
        /**
         * ??????????????????????????????????????????
         */
        OpeInWhouseOrderSerialBind opeInWhouseOrderSerialBindNew = new OpeInWhouseOrderSerialBind();
        opeInWhouseOrderSerialBindNew.setId(idAppService.getId(SequenceName.OPE_IN_WHOUSE_ORDER_SERIAL_BIND));
        opeInWhouseOrderSerialBindNew.setOrderBId(paramDTO.getProductId());
        opeInWhouseOrderSerialBindNew.setOrderType(paramDTO.getProductType());
        opeInWhouseOrderSerialBindNew.setSerialNum(paramDTO.getSerialNum());
        opeInWhouseOrderSerialBindNew.setDefaultSerialNum(defaultSerialNum);
        opeInWhouseOrderSerialBindNew.setTabletSn(paramDTO.getTabletSn());
        opeInWhouseOrderSerialBindNew.setLot(paramDTO.getLot());
        opeInWhouseOrderSerialBindNew.setProductId(paramDTO.getBomId());
        opeInWhouseOrderSerialBindNew.setProductType(paramDTO.getProductType());
        opeInWhouseOrderSerialBindNew.setQty(qty);
        opeInWhouseOrderSerialBindNew.setBluetoothMacAddress(paramDTO.getBluetoothMacAddress());
        opeInWhouseOrderSerialBindNew.setCreatedBy(paramDTO.getUserId());
        opeInWhouseOrderSerialBindNew.setCreatedTime(new Date());
        opeInWhouseOrderSerialBindNew.setUpdatedBy(paramDTO.getUserId());
        opeInWhouseOrderSerialBindNew.setUpdatedTime(new Date());
        inWhouseOrderSerialBindMapper.insertInWhouseOrderSerialBind(opeInWhouseOrderSerialBindNew);

        /**
         * ??????????????????????????????
         */
        resultDTO.setQty(remainingQty);
        resultDTO.setName(name);
        resultDTO.setPartsNo(paramDTO.getPartsNo());
        resultDTO.setLot(paramDTO.getLot());
        resultDTO.setSerialNum(paramDTO.getSerialNum());
        resultDTO.setProductionDate(new Date());
        return resultDTO;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult confirmStorage(IdEnter enter) {
        OpeInWhouseOrder opeInWhouseOrder = inWhOrderMapper.getInWhOrderById(enter.getId());

        RpsAssert.isNull(opeInWhouseOrder, ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.IN_WH_ORDER_IS_NOT_EXISTS.getMessage());
        RpsAssert.isTrue(!NewInWhouseOrderStatusEnum.DRAFT.getValue().equals(opeInWhouseOrder.getInWhStatus()),
                ExceptionCodeEnums.IN_WH_ORDER_HAS_BEEN_STORED.getCode(), ExceptionCodeEnums.IN_WH_ORDER_HAS_BEEN_STORED.getMessage());

        // ???????????????
        boolean flag = true;
        try {
            /**
             * ????????????(??????????????????) 1?????? 2????????? 3??????
             */
            List<InWhOrderProductDTO> inWhOrderProductList = null;
            List<OpeInWhouseOrderSerialBind> inWhouseOrderSerialBinds = null;
            switch (opeInWhouseOrder.getOrderType()) {
                case 1:
                    inWhOrderProductList = inWhouseScooterBMapper.getInWhOrderScooterByInWhId(enter.getId());
                    // ?????????????????????????????????
                    checkHasActInWhQty(inWhOrderProductList);

                    List<Long> inWhScooterIds = inWhOrderProductList.stream().map(InWhOrderProductDTO::getId).collect(Collectors.toList());
                    inWhouseOrderSerialBinds = inWhouseOrderSerialBindMapper.batchGetInWhouseOrderSerialBindByOrderBIds(inWhScooterIds);

                    /**
                     * {bomId, List<InWhOrderProductDTO>}
                     */
                    Map<Long, List<InWhOrderProductDTO>> scooterMap = inWhOrderProductList.stream().collect(
                            Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                    );

                    saveWmsStockDataComponent.saveWmsScooterStockData(scooterMap, inWhouseOrderSerialBinds,null,
                            InOutWhEnums.IN.getValue(), enter.getUserId());
                    break;
                case 2:
                    inWhOrderProductList = inWhouseCombinBMapper.getInWhOrderCombinByInWhId(enter.getId());
                    // ?????????????????????????????????
                    checkHasActInWhQty(inWhOrderProductList);

                    List<Long> inWhCombinationIds = inWhOrderProductList.stream().map(InWhOrderProductDTO::getId).collect(Collectors.toList());
                    inWhouseOrderSerialBinds = inWhouseOrderSerialBindMapper.batchGetInWhouseOrderSerialBindByOrderBIds(inWhCombinationIds);

                    /**
                     * {bomId, List<InWhOrderProductDTO>}
                     */
                    Map<Long, List<InWhOrderProductDTO>> combinationMap = inWhOrderProductList.stream().collect(
                            Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                    );

                    saveWmsStockDataComponent.saveWmsCombinationStockData(combinationMap, inWhouseOrderSerialBinds,null,
                            InOutWhEnums.IN.getValue(),enter.getUserId());
                    break;
                default:
                    inWhOrderProductList = inWhousePartsBMapper.getInWhOrderPartsByInWhId(enter.getId());
                    // ?????????????????????????????????
                    checkHasActInWhQty(inWhOrderProductList);

                    List<Long> inWhPartsIds = inWhOrderProductList.stream().map(InWhOrderProductDTO::getId).collect(Collectors.toList());
                    inWhouseOrderSerialBinds = inWhouseOrderSerialBindMapper.batchGetInWhouseOrderSerialBindByOrderBIds(inWhPartsIds);

                    /**
                     * {bomId, List<InWhOrderProductDTO>}
                     */
                    Map<Long, List<InWhOrderProductDTO>> partsMap = inWhOrderProductList.stream().collect(
                            Collectors.groupingBy(InWhOrderProductDTO::getBomId)
                    );

                    saveWmsStockDataComponent.saveWmsPartsStockData(partsMap, inWhouseOrderSerialBinds,null,
                            InOutWhEnums.IN.getValue(), enter.getUserId());
                    break;
            }

            /**
             * ????????????????????????????????????????????????????????????????????????
             */
//                List<String> serialNumList = inWhouseOrderSerialBinds.stream().map(OpeInWhouseOrderSerialBind::getSerialNum).collect(Collectors.toList());
//                wmsStockSerialNumberMapper.batchUpdateStockStatusByRsnList(serialNumList, enter.getUserId(), new Date());

            // ???????????????????????? ???????????????
            opeInWhouseOrder.setInWhStatus(NewInWhouseOrderStatusEnum.ALREADY_IN_WHOUSE.getValue());
            opeInWhouseOrder.setUpdatedBy(enter.getUserId());
            opeInWhouseOrder.setUpdatedTime(new Date());
            inWhOrderMapper.updateInWhOrder(opeInWhouseOrder);

        } catch (Exception e) {
            flag = false;
            log.error("????????????????????????----{}", ExceptionUtils.getStackTrace(e));
            throw new SesMobileRpsException(ExceptionCodeEnums.WAREHOUSING_FAILED.getCode(),
                    ExceptionCodeEnums.WAREHOUSING_FAILED.getMessage());
        }

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * ?????????????????????????????????
     * @param inWhOrderProductList
     */
    private void checkHasActInWhQty(List<InWhOrderProductDTO> inWhOrderProductList) {
        int qty = 0;
        for (int i = 0; i < inWhOrderProductList.size(); i++) {
            qty += inWhOrderProductList.get(i).getActInWhQty();
        }
        RpsAssert.isTrue(qty <= 0, ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(), ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());
    }

}
