package com.redescooter.ses.service.scooter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.constant.SpecificDefNameConstant;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.node.BindVinEnter;
import com.redescooter.ses.api.common.vo.node.InputBatteryEnter;
import com.redescooter.ses.api.common.vo.node.InputScooterEnter;
import com.redescooter.ses.api.common.vo.node.InquiryDetailResult;
import com.redescooter.ses.api.common.vo.node.InquiryListAppEnter;
import com.redescooter.ses.api.common.vo.node.InquiryListResult;
import com.redescooter.ses.api.common.vo.node.SetModelEnter;
import com.redescooter.ses.api.common.vo.scooter.ColorDTO;
import com.redescooter.ses.api.common.vo.scooter.SpecificGroupDTO;
import com.redescooter.ses.api.common.vo.specification.SpecificDefDTO;
import com.redescooter.ses.api.hub.service.admin.ScooterModelService;
import com.redescooter.ses.api.hub.service.operation.CodebaseService;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import com.redescooter.ses.api.hub.vo.admin.AdmScooter;
import com.redescooter.ses.api.hub.vo.admin.AdmScooterUpdateEnter;
import com.redescooter.ses.api.hub.vo.operation.OpeSimInformation;
import com.redescooter.ses.api.hub.vo.operation.SpecificTypeDTO;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterNodeService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SpecificDefGroupPublishDTO;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterNodeMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNode;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.scooter.service.base.ScoScooterNodeService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/21 17:12
 */
@DubboService
@Log4j
public class ScooterNodeServiceImpl implements ScooterNodeService {

    @Autowired
    private ScoScooterService scoScooterService;

    @Autowired
    private ScoScooterNodeService scoScooterNodeService;

    @Autowired
    private ScoScooterNodeMapper scoScooterNodeMapper;

    @Autowired
    private ScooterService scooterService;

    @Autowired
    private ScooterEmqXService scooterEmqXService;

    @Autowired
    private JedisCluster jedisCluster;

    @DubboReference
    private IdAppService idAppService;

    @DubboReference
    private ColorService colorService;

    @DubboReference
    private CodebaseService codebaseService;

    @DubboReference
    private ScooterModelService scooterModelService;

    @DubboReference
    private SpecificService specificService;

    /**
     * 列表
     */
    @Override
    public PageResult<InquiryListResult> getList(InquiryListAppEnter enter) {
        int count = scoScooterNodeMapper.getListCount(enter, enter.getUserId());
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        List<InquiryListResult> list = scoScooterNodeMapper.getList(enter, enter.getUserId());
        return PageResult.create(enter, count, list);
    }

    /**
     * 详情
     */
    @Override
    public InquiryDetailResult getDetail(IdEnter enter) {
        // 校验此车辆是否已分配给其他仓库账号
        LambdaQueryWrapper<ScoScooter> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScoScooter::getDr, Constant.DR_FALSE);
        lqw.eq(ScoScooter::getId, enter.getId());
        lqw.last("limit 1");
        ScoScooter scooter = scoScooterService.getOne(lqw);
        if (null != scooter) {
            Long warehouseAccountId = scooter.getWarehouseAccountId();
            if (null != warehouseAccountId && !enter.getUserId().equals(warehouseAccountId)) {
                throw new ScooterException(ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getCode(), ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getMessage());
            }
        }

        // 先查看车辆在node表是否存在数据,不存在就新建
        Integer appNode = 0;
        LambdaQueryWrapper<ScoScooterNode> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooterNode::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooterNode::getScooterId, scooter.getId());
        qw.last("limit 1");
        ScoScooterNode model = scoScooterNodeService.getOne(qw);
        if (null == model) {
            // 新增node表
            ScoScooterNode node = new ScoScooterNode();
            node.setId(idAppService.getId(SequenceName.SCO_SCOOTER));
            node.setDr(Constant.DR_FALSE);
            node.setScooterId(scooter.getId());
            node.setAppNode(1);
            node.setFlag(0);
            node.setCreatedBy(enter.getUserId() == null ? 0L : enter.getUserId());
            node.setCreatedTime(new Date());
            scoScooterNodeService.save(node);
        } else {
            appNode = model.getAppNode();
        }

        InquiryDetailResult result = new InquiryDetailResult();
        result.setScooterName(ScooterModelEnum.getScooterModelByType(Integer.valueOf(scooter.getModel())));
        ColorDTO color = colorService.getColorInfoById(scooter.getColorId());
        if (null == color) {
            throw new ScooterException(ExceptionCodeEnums.COLOR_NOT_EXISTS.getCode(), ExceptionCodeEnums.COLOR_NOT_EXISTS.getMessage());
        }
        result.setColorName(color.getColorName());
        result.setRsn(scooter.getScooterNo());
        result.setTabletSn(scooter.getTabletSn());
        result.setBluetoothAddress(scooter.getBluetoothMacAddress());
        result.setVinCode(scooter.getVin());
        result.setBbi(scooter.getBbi());
        result.setController(scooter.getController());
        result.setMotor(scooter.getMotor());
        result.setImei(scooter.getImei());
        String battery = scooter.getBattery();
        if (StringUtils.isBlank(battery)) {
            result.setBatteryNumber(0);
            result.setBatteryList(Collections.EMPTY_LIST);
        } else {
            String[] split = battery.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            list.removeAll(Collections.singleton(null));
            result.setBatteryNumber(list.size());
            result.setBatteryList(list);
        }
        result.setAppNode(appNode);
        result.setRequestId(enter.getRequestId());
        return result;
    }

    /**
     * 录入车辆
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputScooter(InputScooterEnter enter) {
        Long scooterId = enter.getScooterId();
        String rsn = enter.getRsn().trim();
        String tabletSn = enter.getTabletSn().trim();
        String bluetoothAddress = enter.getBluetoothMacAddress().trim();
        String bbi = enter.getBbi().trim();
        String controller = enter.getController().trim();
        String motor = enter.getMotor().trim();
        String imei = enter.getImei().trim();

        // 校验重复
        LambdaQueryWrapper<ScoScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooter::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooter::getScooterNo, rsn);
        List<ScoScooter> list = scoScooterService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ScooterException(ExceptionCodeEnums.PARTS_HAS_INPUT.getCode(), ExceptionCodeEnums.PARTS_HAS_INPUT.getMessage());
        }

        // 校验码库
        boolean flag = codebaseService.checkRsn(rsn);
        if (flag) {
            throw new ScooterException(ExceptionCodeEnums.CODEBASE_NOT_EXIST.getCode(), ExceptionCodeEnums.CODEBASE_NOT_EXIST.getMessage());
        }

        // 修改主表
        ScoScooter model = new ScoScooter();
        model.setId(scooterId);
        model.setWarehouseAccountId(enter.getUserId());
        model.setScooterNo(rsn);
        model.setTabletSn(tabletSn);
        model.setBbi(bbi);
        model.setController(controller);
        model.setMotor(motor);
        model.setImei(imei);
        model.setBluetoothMacAddress(bluetoothAddress);
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        scoScooterService.updateById(model);

        // node表appNode字段
        ScoScooterNode node = new ScoScooterNode();
        node.setAppNode(2);
        node.setFlag(1);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<ScoScooterNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoScooterNode::getDr, Constant.DR_FALSE);
        wrapper.eq(ScoScooterNode::getScooterId, scooterId);
        scoScooterNodeService.update(node, wrapper);

        // 修改码库此rsn为已用
        codebaseService.updateRsn(rsn, enter.getUserId());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 录入电池
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public List<String> inputBattery(InputBatteryEnter enter) {
        // 校验重复
        List<String> result = Lists.newArrayList();
        String[] split = enter.getBattery().split(",");
        List<String> list = new ArrayList<>(Arrays.asList(split));
        for (String battery : list) {
            LambdaQueryWrapper<ScoScooter> qw = new LambdaQueryWrapper<>();
            qw.eq(ScoScooter::getDr, Constant.DR_FALSE);
            qw.like(ScoScooter::getBattery, battery);
            qw.last("limit 1");
            ScoScooter scooter = scoScooterService.getOne(qw);
            if (null != scooter) {
                result.add(battery);
            }
        }
        if (CollectionUtils.isNotEmpty(result) && result.size() > 0) {
            return result;
        }

        // 修改主表
        ScoScooter model = new ScoScooter();
        model.setId(enter.getScooterId());
        model.setBattery(enter.getBattery());
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        scoScooterService.updateById(model);

        // node表appNode字段
        ScoScooterNode node = new ScoScooterNode();
        node.setAppNode(3);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<ScoScooterNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoScooterNode::getDr, Constant.DR_FALSE);
        wrapper.eq(ScoScooterNode::getScooterId, enter.getScooterId());
        scoScooterNodeService.update(node, wrapper);
        return result;
    }

    /**
     * 设置软体
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult setScooterModel(SetModelEnter enter) {
        ScoScooter scoScooter = scoScooterService.getById(enter.getScooterId());
        if (null == scoScooter) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        // 创建车辆
        AdmScooter admScooter = scooterModelService.getScooterBySn(scoScooter.getTabletSn());
        if (null != admScooter) {
            throw new ScooterException(ExceptionCodeEnums.SN_ALREADY_EXISTS.getCode(), ExceptionCodeEnums.SN_ALREADY_EXISTS.getMessage());
        }
        log.info("车辆不存在");

        ColorDTO color = colorService.getColorInfoById(scoScooter.getColorId());
        if (null == color) {
            throw new ScooterException(ExceptionCodeEnums.COLOR_NOT_EXISTS.getCode(), ExceptionCodeEnums.COLOR_NOT_EXISTS.getMessage());
        }

        // 获取低速
        SpecificGroupDTO group = colorService.getLowSpeed();

        // 新增adm_scooter表
        AdmScooter scooter = new AdmScooter();
        scooter.setId(idAppService.getId(SequenceName.SCO_SCOOTER));
        scooter.setDr(Constant.DR_FALSE);
        scooter.setSn(scoScooter.getTabletSn());
        scooter.setGroupId(group.getId());
        scooter.setColorId(scoScooter.getColorId());
        scooter.setMacAddress(scoScooter.getBluetoothMacAddress());
        scooter.setScooterController(enter.getModel());
        scooter.setCreatedBy(0L);
        scooter.setCreatedTime(new Date());
        scooter.setUpdatedBy(0L);
        scooter.setUpdatedTime(new Date());
        scooter.setColorName(color.getColorName());
        scooter.setColorValue(color.getColorValue());
        scooter.setGroupName(group.getGroupName());
        scooter.setMacName(scoScooter.getBluetoothMacAddress());
        scooterModelService.insertScooter(scooter);
        log.info("新增adm_scooter表成功");

        // 设置软体
        AdmScooter scooterModel = scooterModelService.getScooterById(scooter.getId());
        if (null == scooterModel) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }
        // 只允许车辆关闭状态时进行软体设置
        String status = scooterService.getScooterStatusByTabletSn(scooter.getSn());
        if (ScooterLockStatusEnums.UNLOCK.getValue().equals(status)) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getCode(), ExceptionCodeEnums.SCOOTER_NOT_CLOSED.getMessage());
        }

        // 更新车辆型号信息
        AdmScooterUpdateEnter param = new AdmScooterUpdateEnter();
        param.setId(scooterModel.getId());
        Integer type = 2;
        String specificName = "E50";
        if (enter.getModel() == 1) {
            type = 2;
            specificName = "E50";
        }
        if (enter.getModel() == 2) {
            type = 3;
            specificName = "E100";
        }
        param.setScooterController(type);
        param.setGroupId(group.getId());
        param.setGroupName(group.getGroupName());
        scooterModelService.updateAdmScooter(param);
        log.info("更新车辆型号信息");
        scooterService.syncScooterModel(scooterModel.getSn(), type);
        log.info("同步车辆型号");

        SpecificTypeDTO specificType = specificService.getSpecificTypeByName(specificName);
        List<SpecificDefGroupPublishDTO> list = buildSetScooterModelData(specificType.getId());
        if (CollectionUtils.isNotEmpty(list)) {
            // 发送EMQ消息,通知车辆那边进行升级处理
            SetScooterModelPublishDTO instance = new SetScooterModelPublishDTO();
            instance.setTabletSn(scooterModel.getSn());
            instance.setScooterModel(type);
            instance.setSpecificDefGroupList(list);
            scooterEmqXService.setScooterModel(instance);
        }
        log.info("设置软体完毕");

        // node表appNode字段
        ScoScooterNode node = new ScoScooterNode();
        node.setAppNode(4);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<ScoScooterNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoScooterNode::getDr, Constant.DR_FALSE);
        wrapper.eq(ScoScooterNode::getScooterId, enter.getScooterId());
        scoScooterNodeService.update(node, wrapper);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 绑定VIN
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult bindVin(BindVinEnter enter) {
        String vinCode = enter.getVinCode().trim();
        if (vinCode.length() != 17) {
            throw new ScooterException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }
        if (!vinCode.startsWith("VXSR2A")) {
            throw new ScooterException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        ScoScooter scoScooter = scoScooterService.getById(enter.getScooterId());
        if (null == scoScooter) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        // 截取第7位,车型编号
        String productTypeSub = vinCode.substring(6, 7);
        if (!StringUtils.equals(scoScooter.getModel(), productTypeSub)) {
            throw new ScooterException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        // 截取第8位,座位数量
        String seatNumberSub = vinCode.substring(7, 8);
        if (!StringUtils.equals("2", seatNumberSub)) {
            throw new ScooterException(ExceptionCodeEnums.VIN_NOT_MATCH.getCode(), ExceptionCodeEnums.VIN_NOT_MATCH.getMessage());
        }

        // 校验重复
        LambdaQueryWrapper<ScoScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooter::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooter::getVin, vinCode);
        List<ScoScooter> list = scoScooterService.list(qw);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ScooterException(ExceptionCodeEnums.PARTS_HAS_INPUT.getCode(), ExceptionCodeEnums.PARTS_HAS_INPUT.getMessage());
        }

        // 校验码库
        boolean flag = codebaseService.checkVin(vinCode);
        if (flag) {
            throw new ScooterException(ExceptionCodeEnums.CODEBASE_NOT_EXIST.getCode(), ExceptionCodeEnums.CODEBASE_NOT_EXIST.getMessage());
        }

        // 修改主表
        ScoScooter model = new ScoScooter();
        model.setId(enter.getScooterId());
        model.setVin(vinCode);
        model.setUpdatedBy(enter.getUserId());
        model.setUpdatedTime(new Date());
        scoScooterService.updateById(model);

        // node表appNode字段
        ScoScooterNode node = new ScoScooterNode();
        node.setAppNode(5);
        node.setFlag(2);
        node.setUpdatedBy(enter.getUserId());
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<ScoScooterNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoScooterNode::getDr, Constant.DR_FALSE);
        wrapper.eq(ScoScooterNode::getScooterId, enter.getScooterId());
        scoScooterNodeService.update(node, wrapper);

        // 修改码库此rsn为已用
        codebaseService.updateVin(vinCode, enter.getUserId());

        // sim卡信息录入
        ScoScooter scooter = scoScooterService.getById(enter.getScooterId());
        if (null == scoScooter) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        OpeSimInformation sim = new OpeSimInformation();
        String iccid = jedisCluster.get(scooter.getTabletSn());
        if (StringUtils.isNotBlank(iccid)) {
            sim.setSimIccid(iccid);
        }
        sim.setRsn(scooter.getScooterNo());
        sim.setBluetoothMacAddress(scooter.getBluetoothMacAddress());
        sim.setTabletSn(scooter.getTabletSn());
        sim.setVin(scooter.getVin());
        sim.setCreatedBy(enter.getUserId());
        sim.setCreatedTime(new Date());
        codebaseService.saveSim(sim);
        jedisCluster.del(scooter.getTabletSn());
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 组装设置车辆型号数据
     */
    private List<SpecificDefGroupPublishDTO> buildSetScooterModelData(Long specificTypeId) {
        List<SpecificDefGroupPublishDTO> list = new ArrayList<>();

        // 查询当前车辆电池信息,这里主要是为了拿车辆电池的出厂号/流水号信息(用于设置软体时使用) 先不给电池的出厂号/流水号(车辆那边现在不是强制性需要)
        // 查询车辆型号自定义项信息
        List<SpecificDefDTO> specificDefList = specificService.getSpecificDefBySpecificId(specificTypeId);
        if (CollectionUtils.isNotEmpty(specificDefList)) {
            // 旧数据ope_specificat_def表里面def_group_id字段值是空的,这里会导致stream分组的时候报错
            specificDefList.forEach(def -> {
                if (null == def.getSpecificDefGroupId()) {
                    throw new ScooterException(ExceptionCodeEnums.DATA_EXCEPTION.getCode(), ExceptionCodeEnums.DATA_EXCEPTION.getMessage());
                }
            });

            Map<Long, List<SpecificDefDTO>> specificDefGroupMap = specificDefList.stream().collect(Collectors.groupingBy(SpecificDefDTO::getSpecificDefGroupId));

            for (Map.Entry<Long, List<SpecificDefDTO>> map : specificDefGroupMap.entrySet()) {
                Map<String, String> specificDefMap = map.getValue().stream().collect(Collectors.toMap(SpecificDefDTO::getDefName, SpecificDefDTO::getDefValue));

                // 组装自定义项数据 -- 自定义项名称固定值
                SpecificDefGroupPublishDTO publish = SpecificDefGroupPublishDTO.builder()
                        .wheelDiameter(specificDefMap.get(SpecificDefNameConstant.WHEEL_DIAMETER))
                        .speedRatio(specificDefMap.get(SpecificDefNameConstant.SPEED_RATIO))
                        .limitSpeedBos(specificDefMap.get(SpecificDefNameConstant.LIMIT_SPEED_BOS))
                        .limiting(specificDefMap.get(SpecificDefNameConstant.LIMITING))
                        .speedLimit(specificDefMap.get(SpecificDefNameConstant.SPEED_LIMIT))
                        .socRedWarning(specificDefMap.get(SpecificDefNameConstant.SOC_RED_WARNING))
                        .orangeWarning(specificDefMap.get(SpecificDefNameConstant.ORANGE_WARNING))
                        .stallSOC(specificDefMap.get(SpecificDefNameConstant.STALL_SOC))
                        .setSOCTo0AtStallUndervoltage(specificDefMap.get(SpecificDefNameConstant.SET_SOC_TO_0_AT_STALL_UNDER_VOLTAGE))
                        .stallVoltageUndervoltage(specificDefMap.get(SpecificDefNameConstant.STALL_VOLTAGE_UNDER_VOLTAGE))
                        .voltageLegalRecognitionMin(specificDefMap.get(SpecificDefNameConstant.VOLTAGE_LEGAL_RECOGNITION_MAX))
                        .voltageLegalRecognitionMax(specificDefMap.get(SpecificDefNameConstant.VOLTAGE_LEGAL_RECOGNITION_MIN))
                        .controllerUndervoltage(specificDefMap.get(SpecificDefNameConstant.CONTROLLER_UNDER_VOLTAGE))
                        .controllerUndervoltageRecovery(specificDefMap.get(SpecificDefNameConstant.CONTROLLER_UNDER_VOLTAGE_RECOVERY))
                        .build();
                list.add(publish);
            }
        }
        return list;
    }




}