package com.redescooter.ses.mobile.rps.service.combinorder.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.bom.BomCommonTypeEnums;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.OrderTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.ProductTypeEnums;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.CombinListStatusEnum;
import com.redescooter.ses.api.common.enums.restproductionorder.assembly.NewCombinOrderStatusEnums;
import com.redescooter.ses.api.common.service.RosCombinOrderService;
import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.mobile.rps.config.RpsAssert;
import com.redescooter.ses.mobile.rps.constant.SequenceName;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinOrderCombinBMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinOrderScooterBMapper;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionPartsRelationMapper;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinationListCombinMapper;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinationListPartsSerialBindMapper;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinationListRelationPartsMapper;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinationListScooterMapper;
import com.redescooter.ses.mobile.rps.dao.combinorder.CombinationOrderMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.QcOrderMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.QcOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCodebaseRsn;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListCombinB;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrder;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderCombinB;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderScooterB;
import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;
import com.redescooter.ses.mobile.rps.dm.OpeProductionParts;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPartsRelation;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrderSerialBind;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.base.OpeCodebaseRsnService;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderSerialBindService;
import com.redescooter.ses.mobile.rps.service.combinorder.CombinationOrderService;
import com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.CombinationListDetailDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.CombinationOrderDetailDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.QueryCombinationOrderParamDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.QueryCombinationOrderResultDTO;
import com.redescooter.ses.mobile.rps.vo.combinorder.QueryCombinationPartsListParamDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author assert
 * @date 2021/1/27 11:12
 */
@Slf4j
@Service
public class CombinationOrderServiceImpl implements CombinationOrderService {

    @DubboReference
    private RosCombinOrderService rosCombinOrderService;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private ScooterService scooterService;

    @Autowired
    private CombinationOrderMapper combinationOrderMapper;

    @Autowired
    private CombinationListScooterMapper combinationListScooterMapper;

    @Autowired
    private CombinationListCombinMapper combinationListCombinMapper;

    @Autowired
    private CombinationListRelationPartsMapper combinationListRelationPartsMapper;

    @Autowired
    private CombinationListPartsSerialBindMapper combinationListPartsSerialBindMapper;

    @Autowired
    private ProductionScooterBomMapper scooterBomMapper;

    @Autowired
    private ProductionCombinBomMapper combinBomMapper;

    @Autowired
    private QcOrderSerialBindMapper qcOrderSerialBindMapper;

    @Autowired
    private OpeCombinOrderScooterBMapper opeCombinOrderScooterBMapper;

    @Autowired
    private OpeCombinOrderCombinBMapper opeCombinOrderCombinBMapper;

    @Autowired
    private OpeProductionPartsRelationMapper opeProductionPartsRelationMapper;

    @Autowired
    private ProductionPartsMapper productionPartsMapper;

    @Autowired
    private OpeOrderSerialBindService opeOrderSerialBindService;

    @Autowired
    private ProductionPartsMapper partsMapper;

    @Autowired
    private QcOrderMapper qcOrderMapper;

    @Autowired
    private OpeCodebaseRsnService opeCodebaseRsnService;

    @Override
    public Map<Integer, Integer> getCombinationOrderTypeCount(GeneralEnter enter) {
        List<CountByStatusResult> countByStatusResultList = combinationOrderMapper.getCombinationOrderTypeCount();
        /**
         * {combinationOrderType, totalCount}
         */
        Map<Integer, Integer> map = countByStatusResultList.stream().collect(
                Collectors.toMap(r -> Integer.valueOf(r.getStatus()), CountByStatusResult::getTotalCount)
        );

        for (ProductTypeEnums item : ProductTypeEnums.values()) {
            if (null == map.get(item.getValue())) {
                map.put(item.getValue(), 0);
            }
        }
        // ???????????????????????????
        map.remove(ProductTypeEnums.PARTS.getValue());
        return map;
    }

    @Override
    public PageResult<QueryCombinationOrderResultDTO> getCombinationOrderList(QueryCombinationOrderParamDTO paramDTO) {
        int count = combinationOrderMapper.countByCombinationOrder(paramDTO);
        if (0 == count) {
            return PageResult.createZeroRowResult(paramDTO);
        }
        return PageResult.create(paramDTO, count, combinationOrderMapper.getCombinationOrderList(paramDTO));
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startCombination(IdEnter enter) {
        CombinationOrderDetailDTO combinationOrderDetail = combinationOrderMapper.getCombinationOrderDetailById(enter.getId());
        RpsAssert.isNull(combinationOrderDetail, ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isFalse(NewCombinOrderStatusEnums.PREPARATION_COMPLETED.getValue().equals(combinationOrderDetail.getStatus()),
                ExceptionCodeEnums.COMBINATION_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.COMBINATION_ORDER_STATUS_ERROR.getMessage());

        // ???????????????????????????????????????
        OpeCombinOrder opeCombinOrder = new OpeCombinOrder();
        opeCombinOrder.setId(combinationOrderDetail.getId());
        opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.ASSEMBLING.getValue());
        opeCombinOrder.setCombinStartDate(new Date());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        combinationOrderMapper.updateCombinationOrder(opeCombinOrder);

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public CombinationOrderDetailDTO getCombinationOrderDetailById(IdEnter enter) {
        CombinationOrderDetailDTO combinationOrderDetail = combinationOrderMapper.getCombinationOrderDetailById(enter.getId());
        RpsAssert.isNull(combinationOrderDetail, ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getMessage());

        /**
         * ????????????????????????????????? 1?????? 2?????????
         */
        List<CombinationListDTO> combinationList = null;
        switch (combinationOrderDetail.getType()) {
            case 1:
                combinationList = combinationListScooterMapper.getCombinationListScooterByCombinId(enter.getId());
                break;
            default:
                combinationList = combinationListCombinMapper.getCombinationListCombinByCombinId(enter.getId());
                break;
        }

        combinationOrderDetail.setCombinationList(combinationList);
        return combinationOrderDetail;
    }

    @Override
    public CombinationListDetailDTO getCombinationOrderPartsList(QueryCombinationPartsListParamDTO paramDTO) {
        CombinationListDetailDTO combinationListDetail = null;

        /**
         * ??????????????????????????? 1?????? 2?????????
         */
        switch (paramDTO.getProductType()) {
            case 1:
                combinationListDetail = combinationListScooterMapper.getCombinationListScooterDetailById(paramDTO.getProductId());
                break;
            default:
                combinationListDetail = combinationListCombinMapper.getCombinationListCombinDetailById(paramDTO.getProductId());
                break;
        }

        return combinationListDetail;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        // ????????????????????????????????????????????????
        RpsAssert.isTrue(!paramDTO.getIdClass() && null == paramDTO.getQty(),
                ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getCode(), ExceptionCodeEnums.SCAN_CODE_QTY_ERROR.getMessage());
        RpsAssert.isTrue(paramDTO.getIdClass() && StringUtils.isBlank(paramDTO.getSerialNum()),
                ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getCode(), ExceptionCodeEnums.SERIAL_NUM_IS_EMPTY.getMessage());

        // ?????????????????????
        RpsAssert.isBlank(paramDTO.getPartsNo(), ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PARTS_NO_IS_EMPTY.getMessage());
        // ??????????????????????????????????????????????????????????????????
        Integer idClass = partsMapper.getPartsIdClassById(paramDTO.getBomId(), paramDTO.getPartsNo());
        RpsAssert.isNull(idClass, ExceptionCodeEnums.PART_IS_NOT_EXIST.getCode(),
                ExceptionCodeEnums.PART_IS_NOT_EXIST.getMessage());
        boolean flag = 0 == idClass ? false : true;
        RpsAssert.isFalse(paramDTO.getIdClass() == flag, ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getCode(),
                ExceptionCodeEnums.PRODUCT_ID_CLASS_ERROR.getMessage());

        Integer qty = paramDTO.getIdClass() ? 1 : paramDTO.getQty();

        OpeCombinListRelationParts opeCombinListRelationParts = combinationListRelationPartsMapper
                .getCombinationListRelationPartsByRIdAndBId(paramDTO.getProductId(), paramDTO.getBomId());
        RpsAssert.isNull(opeCombinListRelationParts, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
        // ECU???????????????????????????mac??????
        RpsAssert.isTrue(BomCommonTypeEnums.ECU_METER.getValue().equals(String.valueOf(opeCombinListRelationParts.getPartsType()))
                        && StringUtils.isBlank(paramDTO.getBluetoothMacAddress()), ExceptionCodeEnums.BLUETOOTH_MAC_ADDRESS_IS_EMPTY.getCode(),
                ExceptionCodeEnums.BLUETOOTH_MAC_ADDRESS_IS_EMPTY.getMessage());

        /**
         * ?????????????????????????????????????????????
         */
        if (paramDTO.getIdClass()) {
            OpeCombinListPartsSerialBind opeCombinListPartsSerialBind = combinationListPartsSerialBindMapper
                    .getCombinListPartsSerialBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNotNull(opeCombinListPartsSerialBind, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());

            opeCombinListRelationParts.setScanCodeQty(opeCombinListRelationParts.getScanCodeQty() + qty);
        } else {
            // ???????????????????????????????????????????????????
            RpsAssert.isTrue(!qty.equals(opeCombinListRelationParts.getQty()), ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(),
                    ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());
            RpsAssert.isTrue(opeCombinListRelationParts.getScanCodeQty() > 0, ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());

            opeCombinListRelationParts.setScanCodeQty(qty);
        }
        opeCombinListRelationParts.setUpdatedBy(paramDTO.getUserId());
        opeCombinListRelationParts.setUpdatedTime(new Date());
        combinationListRelationPartsMapper.updateCombinListRelationParts(opeCombinListRelationParts);

        /**
         * ??????????????????????????????????????????
         */
        if (paramDTO.getIdClass()) {
            OpeQcOrderSerialBind opeQcOrderSerialBind = qcOrderSerialBindMapper.getQcOrderSerialBindBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNull(opeQcOrderSerialBind, ExceptionCodeEnums.NOT_COMPLETED_QC.getCode(),
                    ExceptionCodeEnums.NOT_COMPLETED_QC.getMessage());

            OpeCombinListPartsSerialBind opeCombinListPartsSerialBind = new OpeCombinListPartsSerialBind();
            opeCombinListPartsSerialBind.setId(idAppService.getId(SequenceName.OPE_COMBIN_LIST_PARTS_SERIAL_BIND));
            opeCombinListPartsSerialBind.setDr(0);
            opeCombinListPartsSerialBind.setOrderBId(paramDTO.getProductId());
            opeCombinListPartsSerialBind.setOrderType(paramDTO.getProductType());
            opeCombinListPartsSerialBind.setSerialNum(paramDTO.getSerialNum());
            // ?????????????????????(???????????????)
            opeCombinListPartsSerialBind.setDefaultSerialNum(opeQcOrderSerialBind.getDefaultSerialNum());
            opeCombinListPartsSerialBind.setBluetoothMacAddress(opeQcOrderSerialBind.getBluetoothMacAddress());
            opeCombinListPartsSerialBind.setLot(paramDTO.getLot());
            opeCombinListPartsSerialBind.setProductId(paramDTO.getBomId());
            opeCombinListPartsSerialBind.setProductType(ProductTypeEnums.PARTS.getValue());
            opeCombinListPartsSerialBind.setQty(qty);
            opeCombinListPartsSerialBind.setCreatedBy(paramDTO.getUserId());
            opeCombinListPartsSerialBind.setCreatedTime(new Date());
            opeCombinListPartsSerialBind.setUpdatedBy(paramDTO.getUserId());
            opeCombinListPartsSerialBind.setUpdatedTime(new Date());
            combinationListPartsSerialBindMapper.insertCombinListPartsSerialBind(opeCombinListPartsSerialBind);
        }

        return new GeneralResult(paramDTO.getRequestId());
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public SaveScanCodeResultDTO completeCombination(QueryCombinationPartsListParamDTO paramDTO) {
        SaveScanCodeResultDTO resultDTO = new SaveScanCodeResultDTO();
        // ????????????
        String serialNum = null;
        Long bomId = null;
        Long combinationId = null;

        /**
         * ??????????????????????????? 1?????? 2?????????
         */
        int scanCodeQty = combinationListRelationPartsMapper.getScanCodeQtyByRelationId(paramDTO.getProductId());
        switch (paramDTO.getProductType()) {
            case 1:
                OpeCombinListScooterB opeCombinListScooterB = combinationListScooterMapper.getCombinationListScooterById(paramDTO.getProductId());
                // ?????????????????????
                RpsAssert.isNull(opeCombinListScooterB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
                RpsAssert.isTrue(CombinListStatusEnum.ASSEMBLED.getStatus().equals(opeCombinListScooterB.getCombinListStatus()),
                        ExceptionCodeEnums.COMPLETED_COMBINATION.getCode(), ExceptionCodeEnums.COMPLETED_COMBINATION.getMessage());
                RpsAssert.isTrue(scanCodeQty < opeCombinListScooterB.getQty(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(),
                        ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());

                // ??????bomId
                bomId = opeCombinListScooterB.getScooterBomId();
                // ?????????id
                combinationId = opeCombinListScooterB.getCombinId();

                // ????????????????????????????????????
                opeCombinListScooterB.setCombinListStatus(CombinListStatusEnum.ASSEMBLED.getStatus());
                opeCombinListScooterB.setUpdatedBy(paramDTO.getUserId());
                opeCombinListScooterB.setUpdatedTime(new Date());
                combinationListScooterMapper.updateCombinationListScooter(opeCombinListScooterB);

                // ?????????????????????????????????
                serialNum = getProductSerialNum(paramDTO.getProductType());
                resultDTO.setSerialNum(serialNum);

                // ????????????rsn???
                OpeCodebaseRsn codebaseRsn = new OpeCodebaseRsn();
                codebaseRsn.setId(idAppService.getId(SequenceName.OPE_CODEBASE_RSN));
                codebaseRsn.setDr(Constant.DR_FALSE);
                codebaseRsn.setRsn(serialNum);
                codebaseRsn.setStatus(1);
                codebaseRsn.setCreatedBy(paramDTO.getUserId());
                codebaseRsn.setCreatedTime(new Date());
                opeCodebaseRsnService.save(codebaseRsn);

                // ??????ECU?????????????????????????????????
                OpeCombinListPartsSerialBind opeCombinListPartsSerialBind = combinationListPartsSerialBindMapper.getEcuPartsSerialBindByOrderBId(paramDTO.getProductId());
                RpsAssert.isNull(opeCombinListPartsSerialBind, ExceptionCodeEnums.SCOOTER_HAS_NO_ECU.getCode(),
                        ExceptionCodeEnums.SCOOTER_HAS_NO_ECU.getMessage());

                resultDTO.setName(scooterBomMapper.getScooterModelById(opeCombinListScooterB.getScooterBomId()));
                resultDTO.setQty(combinationListScooterMapper.getQuantityToBeAssembledByCombinationId(opeCombinListScooterB.getCombinId()));
                resultDTO.setBluetoothMacAddress(opeCombinListPartsSerialBind.getBluetoothMacAddress());
                resultDTO.setTabletSn(opeCombinListPartsSerialBind.getDefaultSerialNum());

                break;
            default:
                OpeCombinListCombinB opeCombinListCombinB = combinationListCombinMapper.getCombinationListCombinationById(paramDTO.getProductId());
                // ?????????????????????
                RpsAssert.isNull(opeCombinListCombinB, ExceptionCodeEnums.PRODUCT_IS_EMPTY.getCode(),
                        ExceptionCodeEnums.PRODUCT_IS_EMPTY.getMessage());
                RpsAssert.isTrue(CombinListStatusEnum.ASSEMBLED.getStatus().equals(opeCombinListCombinB.getCombinListStatus()),
                        ExceptionCodeEnums.COMPLETED_COMBINATION.getCode(), ExceptionCodeEnums.COMPLETED_COMBINATION.getMessage());
                RpsAssert.isTrue(scanCodeQty < opeCombinListCombinB.getQty(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(),
                        ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());

                // ?????????bomId
                bomId = opeCombinListCombinB.getProductionCombinBomId();
                // ?????????id
                combinationId = opeCombinListCombinB.getCombinId();

                // ???????????????????????????????????????
                opeCombinListCombinB.setCombinListStatus(CombinListStatusEnum.ASSEMBLED.getStatus());
                opeCombinListCombinB.setUpdatedBy(paramDTO.getUserId());
                opeCombinListCombinB.setUpdatedTime(new Date());
                combinationListCombinMapper.updateCombinationListCombination(opeCombinListCombinB);

                resultDTO.setName(combinBomMapper.getCombinCnNameById(opeCombinListCombinB.getProductionCombinBomId()));
                resultDTO.setQty(combinationListCombinMapper.getQuantityToBeAssembledByCombinationId(opeCombinListCombinB.getCombinId()));
                resultDTO.setSerialNum(getProductSerialNum(paramDTO.getProductType()));

                break;
        }

        // ???????????????
        String lot = getProductLot(combinationId);
        /**
         * ?????????????????????????????????????????????
         */
        OpeOrderSerialBind opeOrderSerialBind = new OpeOrderSerialBind();
        opeOrderSerialBind.setId(idAppService.getId(SequenceName.OPE_ORDER_SERIAL_BIND));
        opeOrderSerialBind.setDr(0);
        opeOrderSerialBind.setOrderBId(paramDTO.getProductId());
        opeOrderSerialBind.setOrderType(paramDTO.getProductType() == 1 ? 10 : 11);
        opeOrderSerialBind.setSerialNum(serialNum);
        opeOrderSerialBind.setLot(lot);
        opeOrderSerialBind.setProductId(bomId);
        opeOrderSerialBind.setProductType(paramDTO.getProductType());
        opeOrderSerialBind.setQty(1);
        opeOrderSerialBind.setBluetoothMacAddress(resultDTO.getBluetoothMacAddress());
        opeOrderSerialBind.setCreatedBy(paramDTO.getUserId());
        opeOrderSerialBind.setCreatedTime(new Date());
        opeOrderSerialBind.setUpdatedBy(paramDTO.getUserId());
        opeOrderSerialBind.setUpdatedTime(new Date());
        opeOrderSerialBind.setDef1(resultDTO.getTabletSn());
        opeOrderSerialBindService.insertOrUpdateSelective(opeOrderSerialBind);

        /**
         * ???????????????????????????????????????
         */
        resultDTO.setLot(lot);
        // ????????????????????????????????????????????????
        resultDTO.setProductionDate(new Date());
        resultDTO.setPrintFlag(true);
        return resultDTO;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult submitQc(IdEnter enter) {
        OpeCombinOrder opeCombinOrder = combinationOrderMapper.getCombinationOrderById(enter.getId());
        RpsAssert.isNull(opeCombinOrder, ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getMessage());

        /**
         * ?????????????????????????????????????????????????????????????????????
         */
        int count = 0;
        if (ProductTypeEnums.SCOOTER.getValue().equals(opeCombinOrder.getCombinType())) {
            count = combinationListScooterMapper.getQuantityToBeAssembledByCombinationId(opeCombinOrder.getId());
        } else {
            count = combinationListCombinMapper.getQuantityToBeAssembledByCombinationId(opeCombinOrder.getId());
        }
        RpsAssert.isTrue(count > 0, ExceptionCodeEnums.COMBINATION_NOT_COMPLETED.getCode(),
                ExceptionCodeEnums.COMBINATION_NOT_COMPLETED.getMessage());

        /**
         * ????????????????????????????????????????????????,??????????????????
         */
        int isExistsQcOrder = qcOrderMapper.isExistsQcOrderByRelationIdByType(opeCombinOrder.getId(), OrderTypeEnums.COMBIN_ORDER.getValue());
        RpsAssert.isTrue(isExistsQcOrder > 0, ExceptionCodeEnums.QC_SUBMITTED.getCode(),
                ExceptionCodeEnums.QC_SUBMITTED.getMessage());

        // ??????????????????????????????????????????
        opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.ASSEMBL_FINISH.getValue());
        opeCombinOrder.setCombinEndDate(new Date());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        combinationOrderMapper.updateCombinationOrder(opeCombinOrder);

        /**
         * ??????Chris???????????????????????????
         */
        rosCombinOrderService.generatorQcOrderByCombin(enter);

        return new GeneralResult(enter.getRequestId());
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult generateCombinationOrderList(IdEnter enter) {
        List<OpeCombinListRelationParts> opeCombinListRelationPartList = new ArrayList<>();
        OpeCombinOrder opeCombinOrder = combinationOrderMapper.getCombinationOrderById(enter.getId());

        /**
         * ????????????????????????????????? 1?????? 2?????????
         */
        switch (opeCombinOrder.getCombinType()) {
            case 1:
                QueryWrapper<OpeCombinOrderScooterB> scooterQueryWrapper = new QueryWrapper<>();
                scooterQueryWrapper.eq(OpeCombinOrderScooterB.COL_DR, "0");
                scooterQueryWrapper.eq(OpeCombinOrderScooterB.COL_COMBIN_ID, opeCombinOrder.getId());

                List<OpeCombinOrderScooterB> opeCombinOrderScooterList = opeCombinOrderScooterBMapper.selectList(scooterQueryWrapper);
                List<OpeCombinListScooterB> opeCombinListScooterList = new ArrayList<>();

                /**
                 * ??????????????????????????????????????????????????????
                 */
                for (int i = 0; i < opeCombinOrderScooterList.size(); i++) {
                    QueryWrapper<OpeProductionPartsRelation> partsRelationQueryWrapper = new QueryWrapper<>();
                    partsRelationQueryWrapper.eq(OpeProductionPartsRelation.COL_DR, "0");
                    partsRelationQueryWrapper.eq(OpeProductionPartsRelation.COL_PRODUCTION_ID, opeCombinOrderScooterList.get(i).getScooterBomId());
                    List<OpeProductionPartsRelation> opeProductionPartsRelationList = opeProductionPartsRelationMapper
                            .selectList(partsRelationQueryWrapper);

                    for (int j = 0; j < opeCombinOrderScooterList.get(i).getQty(); j++) {
                        OpeCombinListScooterB opeCombinListScooterB = new OpeCombinListScooterB();
                        Long combinListScooterId = idAppService.getId(SequenceName.OPE_COMBIN_LIST_SCOOTER_B);
                        opeCombinListScooterB.setId(combinListScooterId);
                        opeCombinListScooterB.setDr(0);
                        opeCombinListScooterB.setCombinId(opeCombinOrderScooterList.get(i).getCombinId());
                        opeCombinListScooterB.setGroupId(opeCombinOrderScooterList.get(i).getGroupId());
                        opeCombinListScooterB.setColorId(opeCombinOrderScooterList.get(i).getColorId());
                        opeCombinListScooterB.setScooterBomId(opeCombinOrderScooterList.get(i).getScooterBomId());
                        opeCombinListScooterB.setCombinListStatus(CombinListStatusEnum.UN_ASSEMBLED.getStatus());
                        opeCombinListScooterB.setQty(getPartsTotalQty(opeProductionPartsRelationList));
                        opeCombinListScooterB.setRemark(opeCombinOrderScooterList.get(i).getRemark());
                        opeCombinListScooterB.setCreatedBy(enter.getUserId());
                        opeCombinListScooterB.setCreatedTime(new Date());
                        opeCombinListScooterB.setUpdatedBy(enter.getUserId());
                        opeCombinListScooterB.setUpdatedTime(new Date());
                        opeCombinListScooterList.add(opeCombinListScooterB);
                        // ??????????????????????????????
                        opeCombinListRelationPartList.addAll(buildCombinListRelationParts(opeProductionPartsRelationList,
                                enter.getUserId(), combinListScooterId, opeCombinOrder.getCombinType()));
                    }
                }

                // ????????????????????????
                combinationListScooterMapper.batchInsertCombinationListScooter(opeCombinListScooterList);
                break;
            default:
                QueryWrapper<OpeCombinOrderCombinB> combinQueryWrapper = new QueryWrapper<>();
                combinQueryWrapper.eq(OpeCombinOrderCombinB.COL_DR, "0");
                combinQueryWrapper.eq(OpeCombinOrderCombinB.COL_COMBIN_ID, opeCombinOrder.getId());

                List<OpeCombinOrderCombinB> opeCombinOrderCombinList = opeCombinOrderCombinBMapper.selectList(combinQueryWrapper);
                List<OpeCombinListCombinB> opeCombinListCombinList = new ArrayList<>();

                /**
                 * ?????????????????????????????????????????????????????????
                 */
                for (int i = 0; i < opeCombinOrderCombinList.size(); i++) {
                    QueryWrapper<OpeProductionPartsRelation> partsRelationQueryWrapper = new QueryWrapper<>();
                    partsRelationQueryWrapper.eq(OpeProductionPartsRelation.COL_DR, "0");
                    partsRelationQueryWrapper.eq(OpeProductionPartsRelation.COL_PRODUCTION_ID, opeCombinOrderCombinList.get(i).getProductionCombinBomId());
                    List<OpeProductionPartsRelation> opeProductionPartsRelationList = opeProductionPartsRelationMapper
                            .selectList(partsRelationQueryWrapper);

                    for (int j = 0; j < opeCombinOrderCombinList.get(i).getQty(); j++) {
                        OpeCombinListCombinB opeCombinListCombinB = new OpeCombinListCombinB();
                        Long combinListCombinId = idAppService.getId(SequenceName.OPE_COMBIN_LIST_COMBIN_B);
                        opeCombinListCombinB.setId(combinListCombinId);
                        opeCombinListCombinB.setDr(0);
                        opeCombinListCombinB.setCombinId(opeCombinOrderCombinList.get(i).getCombinId());
                        opeCombinListCombinB.setCombinName(opeCombinOrderCombinList.get(i).getCombinName());
                        opeCombinListCombinB.setCombinNo(opeCombinOrderCombinList.get(i).getCombinNo());
                        opeCombinListCombinB.setProductionCombinBomId(opeCombinOrderCombinList.get(i).getProductionCombinBomId());
                        opeCombinListCombinB.setCombinListStatus(CombinListStatusEnum.UN_ASSEMBLED.getStatus());
                        opeCombinListCombinB.setQty(getPartsTotalQty(opeProductionPartsRelationList));
                        opeCombinListCombinB.setRemark(opeCombinOrderCombinList.get(i).getRemark());
                        opeCombinListCombinB.setCreatedBy(enter.getUserId());
                        opeCombinListCombinB.setCreatedTime(new Date());
                        opeCombinListCombinB.setUpdatedBy(enter.getUserId());
                        opeCombinListCombinB.setUpdatedTime(new Date());
                        opeCombinListCombinList.add(opeCombinListCombinB);
                        // ??????????????????????????????
                        opeCombinListRelationPartList.addAll(buildCombinListRelationParts(opeProductionPartsRelationList,
                                enter.getUserId(), combinListCombinId, opeCombinOrder.getCombinType()));
                    }
                }

                // ???????????????????????????
                combinationListCombinMapper.batchInsertCombinationListCombination(opeCombinListCombinList);
                break;
        }

        /**
         * ??????????????????????????????
         */
        combinationListRelationPartsMapper.batchInsertCombinationListRelationParts(opeCombinListRelationPartList);

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * ???????????????????????????????????????
     *
     * @param opeProductionPartsRelationList
     * @param userId                         ??????id
     * @param relationId                     ???????????????id
     * @param relationType                   ????????????????????? 1?????? 2?????????
     * @return
     */
    private List<OpeCombinListRelationParts> buildCombinListRelationParts(List<OpeProductionPartsRelation> opeProductionPartsRelationList,
                                                                          Long userId, Long relationId, Integer relationType) {
        List<OpeCombinListRelationParts> opeCombinListRelationPartsList = new ArrayList<>();
        for (int i = 0; i < opeProductionPartsRelationList.size(); i++) {
            OpeProductionParts opeProductionParts = productionPartsMapper
                    .getProductionPartsByBomId(opeProductionPartsRelationList.get(i).getPartsId());

            OpeCombinListRelationParts opeCombinListRelationParts = new OpeCombinListRelationParts();
            BeanUtils.copyProperties(opeProductionPartsRelationList.get(i), opeCombinListRelationParts);

            opeCombinListRelationParts.setId(idAppService.getId(SequenceName.OPE_COMBIN_LIST_RELATION_PARTS));
            opeCombinListRelationParts.setDr(0);
            opeCombinListRelationParts.setRelationId(relationId);
            opeCombinListRelationParts.setRelationType(relationType);
            opeCombinListRelationParts.setQty(opeProductionPartsRelationList.get(i).getPartsQty());
            // ?????????????????????????????????(???????????????????????????????????????????????????)
            opeCombinListRelationParts.setPartsType(opeProductionParts.getPartsType());
            opeCombinListRelationParts.setIdCalss(opeProductionParts.getIdCalss());
            opeCombinListRelationParts.setScanCodeQty(0);
            opeCombinListRelationParts.setCreatedBy(userId);
            opeCombinListRelationParts.setCreatedTime(new Date());
            opeCombinListRelationParts.setUpdatedBy(userId);
            opeCombinListRelationParts.setUpdatedTime(new Date());
            opeCombinListRelationPartsList.add(opeCombinListRelationParts);
        }
        return opeCombinListRelationPartsList;
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param opeProductionPartsRelationList
     * @return
     */
    private int getPartsTotalQty(List<OpeProductionPartsRelation> opeProductionPartsRelationList) {
        int qty = 0;
        for (int i = 0; i < opeProductionPartsRelationList.size(); i++) {
            qty += opeProductionPartsRelationList.get(i).getPartsQty();
        }
        return qty;
    }

    /**
     * ?????????????????????
     *
     * @param productType ???????????? 1?????? 2?????????
     * @return
     */
    private String getProductSerialNum(Integer productType) {
        boolean scooterNoIsExists = true;
        String serialNum = generateProductSerialNumber(productType);

        // ?????????????????????????????????????????????,????????????????????????
        if (ProductTypeEnums.SCOOTER.getValue().equals(productType)) {
            // ???????????????????????????????????????(?????????????????????????????????????????????)
            List<String> scooterNoList = scooterService.getToDayScooterNos();
            if (CollectionUtils.isNotEmpty(scooterNoList)) {
                while (scooterNoIsExists) {
                    // ????????????????????????????????????????????????
                    if (scooterNoList.contains(serialNum)) {
                        serialNum = generateProductSerialNumber(productType);
                    } else {
                        scooterNoIsExists = false;
                    }
                }
            }
        }
        return serialNum;
    }

    /**
     * ?????????????????????
     *
     * @param productType ???????????? 1?????? 2?????????
     * @return
     */
    private String generateProductSerialNumber(Integer productType) {
        Calendar cal = Calendar.getInstance();
        // ???????????????
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String productRange = null;
        String structureType = null;
        /**
         * ?????????????????????????????????, ??????????????????????????????????????????
         */
        switch (productType) {
            case 1:
                productRange = "ED";
                structureType = "D";
                break;
            default:
                return "COMBINATION" + System.currentTimeMillis();
        }

        /**
         * ???????????????
         * 1.????????????(????????????)
         * 2.????????????
         * 3.????????????
         * 4.????????????(??????????????????)
         * 5.??????,???????????????(2021?????????21)
         * 6.??????
         * 7.???????????????(????????? + ????????? + ?????????-??????????????????)
         */
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append(productRange);
        sb.append(structureType);
        sb.append("0");
        sb.append(year.substring(2, 4));
        sb.append(MonthCodeEnum.getMonthCodeByMonth(month));
        // ?????????????????????,???????????????6????????????????????????
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sub = timeStamp.substring(timeStamp.length() - 6);
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", sub);
        sb.append(number);
        return sb.toString();
    }

    /**
     * ?????????????????????
     *
     * @param combinationId ?????????id
     * @return
     */
    private String getProductLot(Long combinationId) {
        Calendar cal = Calendar.getInstance();
        // ???????????????
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        /**
         * ??????????????????????????????????????????
         */
        int index = 0;
        List<Long> combinationIds = combinationOrderMapper.getToDayCombinationId();
        for (int i = 0; i < combinationIds.size(); i++) {
            if (combinationIds.get(i).equals(combinationId)) {
                index = i;
                break;
            }
        }

        /**
         * ???????????????
         * 1.LOT(?????????)
         * 2.??????
         * 3.??????
         * 4.??????
         * 5.?????????(001???002...)
         */
        StringBuilder sb = new StringBuilder();
        sb.append("LOT");
        sb.append(year);
        sb.append(month);
        sb.append(day);
        // index + 1,????????????0?????????
        sb.append(getNumber(index + 1));
        return sb.toString();
    }

    /**
     * ???????????????
     *
     * @param index
     * @return
     */
    private String getNumber(int index) {
        String number = null;
        if (index < 10) {
            number = "00" + index;
        } else if (index >= 10 && index < 100) {
            number = "0" + index;
        } else {
            number = String.valueOf(index);
        }
        return number;
    }

}
