package com.redescooter.ses.mobile.rps.service.combinorder.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.date.DayCodeEnum;
import com.redescooter.ses.api.common.enums.date.MonthCodeEnum;
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
import com.redescooter.ses.mobile.rps.dao.combinorder.*;
import com.redescooter.ses.mobile.rps.dao.production.ProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionPartsMapper;
import com.redescooter.ses.mobile.rps.dao.production.ProductionScooterBomMapper;
import com.redescooter.ses.mobile.rps.dao.qcorder.QcOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.*;
import com.redescooter.ses.mobile.rps.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionPartsService;
import com.redescooter.ses.mobile.rps.service.combinorder.CombinationOrderService;
import com.redescooter.ses.mobile.rps.vo.combinorder.*;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultDTO;
import com.redescooter.ses.mobile.rps.vo.common.SaveScanCodeResultParamDTO;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
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
        // 组装单没有部件类型
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult startCombination(IdEnter enter) {
        CombinationOrderDetailDTO combinationOrderDetail = combinationOrderMapper.getCombinationOrderDetailById(enter.getId());
        RpsAssert.isNull(combinationOrderDetail, ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getMessage());

        RpsAssert.isFalse(NewCombinOrderStatusEnums.PREPARATION_COMPLETED.getValue().equals(combinationOrderDetail.getStatus()),
                ExceptionCodeEnums.COMBINATION_ORDER_STATUS_ERROR.getCode(), ExceptionCodeEnums.COMBINATION_ORDER_STATUS_ERROR.getMessage());

        // 更新组装单状态为【组装中】
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
         * 查询组装单产品清单列表 1车辆 2组装件
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
         * 查询组装单产品详情 1车辆 2组装件
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveScanCodeResult(SaveScanCodeResultParamDTO paramDTO) {
        Integer qty = StringUtils.isNotBlank(paramDTO.getSerialNum()) ? 1 : paramDTO.getQty();
        // 避免重复扫码
        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            OpeCombinListPartsSerialBind opeCombinListPartsSerialBind = combinationListPartsSerialBindMapper
                    .getCombinListPartsSerialBySerialNum(paramDTO.getSerialNum());
            RpsAssert.isNull(opeCombinListPartsSerialBind, ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_SCAN_CODE.getMessage());
        }

        // 无码部件输入数量必须跟所需数量一致
        OpeCombinListRelationParts opeCombinListRelationParts = combinationListRelationPartsMapper
                .getCombinationListRelationPartsByRIdAndBId(paramDTO.getProductId(), paramDTO.getBomId());
        if (StringUtils.isBlank(paramDTO.getSerialNum())) {
            RpsAssert.isTrue(!qty.equals(opeCombinListRelationParts.getQty()),ExceptionCodeEnums.IN_WH_QTY_ERROR.getCode(),
                    ExceptionCodeEnums.IN_WH_QTY_ERROR.getMessage());
            RpsAssert.isTrue(opeCombinListRelationParts.getScanCodeQty() > 0,ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getCode(),
                    ExceptionCodeEnums.NO_NEED_TO_CHECK_AGAIN.getMessage());
        }

        /**
         * 更新组装单清单关联部件扫码数量
         */
        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            opeCombinListRelationParts.setScanCodeQty(opeCombinListRelationParts.getScanCodeQty() + qty);
        } else {
            opeCombinListRelationParts.setScanCodeQty(qty);
        }
        opeCombinListRelationParts.setUpdatedBy(paramDTO.getUserId());
        opeCombinListRelationParts.setUpdatedTime(new Date());
        combinationListRelationPartsMapper.updateCombinListRelationParts(opeCombinListRelationParts);

        /**
         * 保存组装单清单部件序列号信息
         */
        if (StringUtils.isNotBlank(paramDTO.getSerialNum())) {
            OpeQcOrderSerialBind opeQcOrderSerialBind = qcOrderSerialBindMapper.getQcOrderSerialBindBySerialNum(paramDTO.getSerialNum());

            OpeCombinListPartsSerialBind opeCombinListPartsSerialBind = new OpeCombinListPartsSerialBind();
            opeCombinListPartsSerialBind.setId(idAppService.getId(SequenceName.OPE_COMBIN_LIST_PARTS_SERIAL_BIND));
            opeCombinListPartsSerialBind.setDr(0);
            opeCombinListPartsSerialBind.setOrderBId(paramDTO.getProductId());
            opeCombinListPartsSerialBind.setOrderType(paramDTO.getProductType());
            opeCombinListPartsSerialBind.setSerialNum(paramDTO.getSerialNum());
            // 部件本身序列号(厂商序列号)
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SaveScanCodeResultDTO completeCombination(QueryCombinationPartsListParamDTO paramDTO) {
        SaveScanCodeResultDTO saveScanCodeResultDTO = new SaveScanCodeResultDTO();
        // 公共参数
        String name = null;
        Integer remainingQty = 0;
        String bluetoothMacAddress = null;
        String tabletSn = null;
        String serialNum = null;

        /**
         * 修改组装单产品信息 1车辆 2组装件
         */
        int scanCodeQty = combinationListRelationPartsMapper.getScanCodeQtyByRelationId(paramDTO.getProductId());
        switch (paramDTO.getProductType()) {
            case 1:
                OpeCombinListScooterB opeCombinListScooterB = combinationListScooterMapper.getCombinationListScooterById(paramDTO.getProductId());
                RpsAssert.isTrue(scanCodeQty < opeCombinListScooterB.getQty(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(),
                        ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());

                // 更新组装车辆状态为已组装
                opeCombinListScooterB.setCombinListStatus(CombinListStatusEnum.ASSEMBLED.getStatus());
                opeCombinListScooterB.setUpdatedBy(paramDTO.getUserId());
                opeCombinListScooterB.setUpdatedTime(new Date());
                combinationListScooterMapper.updateCombinationListScooter(opeCombinListScooterB);

                name = scooterBomMapper.getScooterModelById(opeCombinListScooterB.getScooterBomId());
                remainingQty = combinationListScooterMapper.getQuantityToBeAssembled();
                // 获取后台生成车辆序列号
                serialNum = getProductSerialNum(paramDTO.getProductType());
//                String serialNumByDb = null;
//                if (StringUtils.isNotBlank(serialNumByDb)) {
//                    // 这种方式还是可能会出现重复序列号,不过先暂时这样后面慢慢改
//                    serialNum = getProductSerialNum(paramDTO.getProductType());
//                }
                // 查询ECU仪表部件序列号绑定信息
                OpeCombinListPartsSerialBind opeCombinListPartsSerialBind = combinationListPartsSerialBindMapper
                        .getEcuPartsSerialBindByOrderBId(paramDTO.getProductId());
                bluetoothMacAddress = opeCombinListPartsSerialBind.getBluetoothMacAddress();
                tabletSn = opeCombinListPartsSerialBind.getDefaultSerialNum();

                break;
            default:
                OpeCombinListCombinB opeCombinListCombinB = combinationListCombinMapper.getCombinationListCombinationById(paramDTO.getProductId());
                RpsAssert.isTrue(scanCodeQty < opeCombinListCombinB.getQty(), ExceptionCodeEnums.PART_QTY_IS_WRONG.getCode(),
                        ExceptionCodeEnums.PART_QTY_IS_WRONG.getMessage());

                // 更新组装组装件状态为已组装
                opeCombinListCombinB.setCombinListStatus(CombinListStatusEnum.ASSEMBLED.getStatus());
                opeCombinListCombinB.setUpdatedBy(paramDTO.getUserId());
                opeCombinListCombinB.setUpdatedTime(new Date());
                combinationListCombinMapper.updateCombinationListCombination(opeCombinListCombinB);

                name = combinBomMapper.getCombinCnNameById(opeCombinListCombinB.getProductionCombinBomId());
                serialNum = getProductSerialNum(paramDTO.getProductType());
                remainingQty = combinationListCombinMapper.getQuantityToBeAssembled();

                break;
        }

        /**
         * 设置完成组装打印二维码参数
         */
        saveScanCodeResultDTO.setName(name);
        saveScanCodeResultDTO.setQty(remainingQty);
        saveScanCodeResultDTO.setBluetoothMacAddress(bluetoothMacAddress);
        saveScanCodeResultDTO.setTabletSn(tabletSn);
        saveScanCodeResultDTO.setSerialNum(serialNum);
        saveScanCodeResultDTO.setLot(System.currentTimeMillis() + "");
        // 生成日期指的就是车辆完成组装时间
        saveScanCodeResultDTO.setProductionDate(new Date());
        saveScanCodeResultDTO.setPrintFlag(true);
        return saveScanCodeResultDTO;
    }

    @Override
    public GeneralResult submitQc(IdEnter enter) {
        CombinationOrderDetailDTO combinationOrderDetail = combinationOrderMapper.getCombinationOrderDetailById(enter.getId());
        RpsAssert.isNull(combinationOrderDetail, ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getCode(),
                ExceptionCodeEnums.COMBINATION_ORDER_IS_NOT_EXISTS.getMessage());

        // 更新组装单状态为【组装完成】
        OpeCombinOrder opeCombinOrder = new OpeCombinOrder();
        opeCombinOrder.setId(combinationOrderDetail.getId());
        opeCombinOrder.setCombinStatus(NewCombinOrderStatusEnums.ASSEMBL_FINISH.getValue());
        opeCombinOrder.setCombinStartDate(new Date());
        opeCombinOrder.setUpdatedBy(enter.getUserId());
        opeCombinOrder.setUpdatedTime(new Date());
        combinationOrderMapper.updateCombinationOrder(opeCombinOrder);

        /**
         * 调用Chris生成组装质检单方法
         */
        rosCombinOrderService.generatorQcOrderByCombin(enter);

        return new GeneralResult(enter.getRequestId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult generateCombinationOrderList(IdEnter enter) {
        List<OpeCombinListRelationParts> opeCombinListRelationPartList = new ArrayList<>();
        OpeCombinOrder opeCombinOrder = combinationOrderMapper.getCombinationOrderById(enter.getId());

        /**
         * 生成组装单产品清单信息 1车辆 2组装件
         */
        switch (opeCombinOrder.getCombinType()) {
            case 1:
                QueryWrapper<OpeCombinOrderScooterB> scooterQueryWrapper = new QueryWrapper<>();
                scooterQueryWrapper.eq("dr", "0");
                scooterQueryWrapper.eq("combin_id", opeCombinOrder.getId());

                List<OpeCombinOrderScooterB> opeCombinOrderScooterList = opeCombinOrderScooterBMapper.selectList(scooterQueryWrapper);
                List<OpeCombinListScooterB> opeCombinListScooterList = new ArrayList<>();

                /**
                 * 组装车辆清单信息、关联部件序列号信息
                 */
                for (int i = 0; i < opeCombinOrderScooterList.size(); i++) {
                    QueryWrapper<OpeProductionPartsRelation> partsRelationQueryWrapper = new QueryWrapper<>();
                    partsRelationQueryWrapper.eq("dr", "0");
                    partsRelationQueryWrapper.eq("production_id", opeCombinOrderScooterList.get(i).getScooterBomId());
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
                        // 车辆组装所需部件信息
                        opeCombinListRelationPartList.addAll(buildCombinListRelationParts(opeProductionPartsRelationList,
                                enter.getUserId(), combinListScooterId, opeCombinOrder.getCombinType()));
                    }
                }

                // 保存车辆清单信息
                combinationListScooterMapper.batchInsertCombinationListScooter(opeCombinListScooterList);
                break;
            default:
                QueryWrapper<OpeCombinOrderCombinB> combinQueryWrapper = new QueryWrapper<>();
                combinQueryWrapper.eq("dr", "0");
                combinQueryWrapper.eq("combin_id", opeCombinOrder.getId());

                List<OpeCombinOrderCombinB> opeCombinOrderCombinList = opeCombinOrderCombinBMapper.selectList(combinQueryWrapper);
                List<OpeCombinListCombinB> opeCombinListCombinList = new ArrayList<>();

                /**
                 * 组装组装件清单信息、关联部件序列号信息
                 */
                for (int i = 0; i < opeCombinOrderCombinList.size(); i++) {
                    QueryWrapper<OpeProductionPartsRelation> partsRelationQueryWrapper = new QueryWrapper<>();
                    partsRelationQueryWrapper.eq("dr", "0");
                    partsRelationQueryWrapper.eq("production_id", opeCombinOrderCombinList.get(i).getProductionCombinBomId());
                    List<OpeProductionPartsRelation> opeProductionPartsRelationList = opeProductionPartsRelationMapper
                            .selectList(partsRelationQueryWrapper);

                    for (int j = 0; j < opeCombinOrderCombinList.get(i).getQty(); j ++) {
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
                        // 车辆组装所需部件信息
                        opeCombinListRelationPartList.addAll(buildCombinListRelationParts(opeProductionPartsRelationList,
                                enter.getUserId(), combinListCombinId, opeCombinOrder.getCombinType()));
                    }
                }

                // 保存组装件清单信息
                combinationListCombinMapper.batchInsertCombinationListCombination(opeCombinListCombinList);
                break;
        }

        /**
         * 保存清单关联部件信息
         */
        combinationListRelationPartsMapper.batchInsertCombinationListRelationParts(opeCombinListRelationPartList);

        return new GeneralResult(enter.getRequestId());
    }


    /**
     * 组装组装单清单关联部件信息
     * @param opeProductionPartsRelationList
     * @param userId 用户id
     * @param relationId 组装清单表id
     * @param relationType 组装清单表类型 1车辆 2组装件
     * @return
     */
    private List<OpeCombinListRelationParts> buildCombinListRelationParts(List<OpeProductionPartsRelation> opeProductionPartsRelationList,
                                                                          Long userId, Long relationId, Integer relationType) {
        OpeProductionParts opeProductionParts = productionPartsMapper
                .getProductionPartsByBomId(opeProductionPartsRelationList.get(0).getPartsId());

        List<OpeCombinListRelationParts> opeCombinListRelationPartsList = new ArrayList<>();
        for (int i = 0; i < opeProductionPartsRelationList.size(); i++) {
            OpeCombinListRelationParts opeCombinListRelationParts = new OpeCombinListRelationParts();
            BeanUtils.copyProperties(opeProductionPartsRelationList.get(i), opeCombinListRelationParts);

            opeCombinListRelationParts.setId(idAppService.getId(SequenceName.OPE_COMBIN_LIST_RELATION_PARTS));
            opeCombinListRelationParts.setDr(0);
            opeCombinListRelationParts.setRelationId(relationId);
            opeCombinListRelationParts.setRelationType(relationType);
            opeCombinListRelationParts.setQty(opeProductionPartsRelationList.get(i).getPartsQty());
            // 部件类型、是否有序列号(最好就是在部件关系表里面加两个字段)
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
     * 获取车辆、组装件组装所需部件总数
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
     * 获取产品序列号
     * @param productType 产品类型 1车辆 2组装件
     * @return
     */
    private String getProductSerialNum(Integer productType) {
        Calendar cal = Calendar.getInstance();
        // 年、月、日
        String year = String.valueOf(cal.get(Calendar.YEAR));
        int month = cal.get(Calendar.MONTH ) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String productRange = null;
        String structureType = null;
        /**
         * 获取产品范围和结构类型, 现在组装件和车辆暂时随机生成
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
         * 编号规则：
         * 1.目标市场(默认法国)
         * 2.产品范围
         * 3.结构类型
         * 4.生产地点(默认中国南通)
         * 5.年份
         * 6.月份
         * 7.生产流水号(生产日 + 工单号 + 流水号)
         */
        StringBuilder sb = new StringBuilder();
        sb.append("FR");
        sb.append(productRange);
        sb.append(structureType);
        sb.append("0");
        sb.append(year.substring(2, 4));
        sb.append(MonthCodeEnum.getMonthCodeByMonth(month));
        // 生产流水号在质检单这边暂时只有 “生产日” + “工单号”组成, 流水号在确认入库时生成
        String number = String.format("%s%s%s", DayCodeEnum.getDayCodeByDay(day), "1", RandomUtils.nextInt(1000, 9999));
        sb.append(number);

        return sb.toString();
    }

}
