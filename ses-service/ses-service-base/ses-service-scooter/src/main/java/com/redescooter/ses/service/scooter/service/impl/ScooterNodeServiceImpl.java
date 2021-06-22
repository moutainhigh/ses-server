package com.redescooter.ses.service.scooter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.node.BindVinEnter;
import com.redescooter.ses.api.common.vo.node.InputBatteryEnter;
import com.redescooter.ses.api.common.vo.node.InputScooterEnter;
import com.redescooter.ses.api.common.vo.node.InquiryDetailResult;
import com.redescooter.ses.api.common.vo.node.InquiryListAppEnter;
import com.redescooter.ses.api.common.vo.node.InquiryListResult;
import com.redescooter.ses.api.common.vo.node.SetModelEnter;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterNodeService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @DubboReference
    private IdAppService idAppService;

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
        if (CollectionUtils.isNotEmpty(list)) {
            for (InquiryListResult item : list) {

                // 车辆型号
                if ("2".equals(item.getModel())) {
                    item.setScooterName("E50");
                } else if ("3".equals(item.getModel())) {
                    item.setScooterName("E100");
                }

                // 电池数量
                String battery = item.getBattery();
                if (StringUtils.isBlank(battery)) {
                    item.setBatteryNum(0);
                } else {
                    String[] split = battery.split(",");
                    List<String> batteryList = new ArrayList<>(Arrays.asList(split));
                    batteryList.removeAll(Collections.singleton(null));
                    item.setBatteryNum(batteryList.size());
                }
            }
        }
        return PageResult.create(enter, count, list);
    }

    /**
     * 详情
     */
    @Override
    public InquiryDetailResult getDetail(StringEnter enter) {
        Long scooterId = getScooterId(enter.getKeyword().trim());
        ScoScooter scooter = scoScooterService.getById(scooterId);

        Integer appNode = 0;
        LambdaQueryWrapper<ScoScooterNode> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooterNode::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooterNode::getScooterId, scooter.getId());
        qw.last("limit 1");
        ScoScooterNode model = scoScooterNodeService.getOne(qw);
        if (null != model) {
            appNode = model.getAppNode();
        }

        InquiryDetailResult result = new InquiryDetailResult();
        result.setScooterName(ScooterModelEnum.getScooterModelByType(Integer.valueOf(scooter.getModel())));
        result.setColorId(scooter.getColorId());
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
     * 根据rsn获取车辆id
     */
    private Long getScooterId(String rsn) {
        LambdaQueryWrapper<ScoScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooter::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooter::getScooterNo, rsn);
        qw.last("limit 1");
        ScoScooter scooter = scoScooterService.getOne(qw);
        if (null == scooter) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }
        return scooter.getId();
    }

    /**
     * 录入车辆
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult inputScooter(InputScooterEnter enter) {
        String rsn = enter.getRsn().trim();
        String tabletSn = enter.getTabletSn().trim();
        String bluetoothAddress = enter.getBluetoothMacAddress().trim();
        String bbi = enter.getBbi().trim();
        String controller = enter.getController().trim();
        String motor = enter.getMotor().trim();
        String imei = enter.getImei().trim();

        Long scooterId = getScooterId(rsn);

        // 校验此车辆是否已分配给其他仓库账号
        ScoScooter scooter = scoScooterService.getById(scooterId);
        if (null != scooter) {
            Long warehouseAccountId = scooter.getWarehouseAccountId();
            if (null != warehouseAccountId && !enter.getUserId().equals(warehouseAccountId)) {
                throw new ScooterException(ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getCode(), ExceptionCodeEnums.ORDER_HAS_DISTRIBUTED.getMessage());
            }
        }

        // 校验重复
        LambdaQueryWrapper<ScoScooter> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ScoScooter::getDr, Constant.DR_FALSE);
        lqw.eq(ScoScooter::getScooterNo, rsn);
        List<ScoScooter> list = scoScooterService.list(lqw);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ScooterException(ExceptionCodeEnums.PARTS_HAS_INPUT.getCode(), ExceptionCodeEnums.PARTS_HAS_INPUT.getMessage());
        }

        // 先查看车辆在node表是否存在数据,不存在就新建
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
        }

        // 修改主表
        ScoScooter param = new ScoScooter();
        param.setId(scooterId);
        param.setWarehouseAccountId(enter.getUserId());
        param.setScooterNo(rsn);
        param.setTabletSn(tabletSn);
        param.setBbi(bbi);
        param.setController(controller);
        param.setMotor(motor);
        param.setImei(imei);
        param.setBluetoothMacAddress(bluetoothAddress);
        param.setUpdatedBy(enter.getUserId());
        param.setUpdatedTime(new Date());
        scoScooterService.updateById(param);

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
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 录入电池
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public List<String> inputBattery(InputBatteryEnter enter) {
        Long scooterId = getScooterId(enter.getRsn());

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
        model.setId(scooterId);
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
        wrapper.eq(ScoScooterNode::getScooterId, scooterId);
        scoScooterNodeService.update(node, wrapper);
        return result;
    }

    /**
     * 设置软体
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public ScoScooterResult setScooterModel(SetModelEnter enter) {
        Long scooterId = getScooterId(enter.getRsn());
        ScoScooter scoScooter = scoScooterService.getById(scooterId);
        if (null == scoScooter) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }
        Integer revision = scoScooter.getRevision();
        if (null != revision) {
            throw new ScooterException(ExceptionCodeEnums.CANNOT_SET_MODEL.getCode(), ExceptionCodeEnums.CANNOT_SET_MODEL.getMessage());
        }

        ScoScooterResult result = new ScoScooterResult();
        BeanUtils.copyProperties(scoScooter, result);
        return result;
    }

    /**
     * 设置软体完成后节点流转
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updateNode(Long userId, Long scooterId) {
        // node表appNode字段
        ScoScooterNode node = new ScoScooterNode();
        node.setAppNode(4);
        node.setUpdatedBy(userId);
        node.setUpdatedTime(new Date());
        // 条件
        LambdaQueryWrapper<ScoScooterNode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoScooterNode::getDr, Constant.DR_FALSE);
        wrapper.eq(ScoScooterNode::getScooterId, scooterId);
        scoScooterNodeService.update(node, wrapper);

        // 标识为已同步
        ScoScooter syncModel = new ScoScooter();
        syncModel.setId(scooterId);
        syncModel.setRevision(1);
        scoScooterService.updateById(syncModel);
        log.info("已标识为已同步");
        return new GeneralResult();
    }

    /**
     * 绑定VIN
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public Map<String, String> bindVin(BindVinEnter enter) {
        Long scooterId = getScooterId(enter.getRsn());
        String vinCode = enter.getVinCode().trim();

        ScoScooter scoScooter = scoScooterService.getById(scooterId);
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

        // 修改主表
        ScoScooter model = new ScoScooter();
        model.setId(scooterId);
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
        wrapper.eq(ScoScooterNode::getScooterId, scooterId);
        scoScooterNodeService.update(node, wrapper);

        ScoScooter scooter = scoScooterService.getById(scooterId);
        if (null == scoScooter) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        Map<String, String> map = Maps.newHashMap();
        map.put("rsn", scooter.getScooterNo());
        map.put("tabletSn", scooter.getTabletSn());
        map.put("vin", scooter.getVin());
        map.put("bluetoothMacAddress", scooter.getBluetoothMacAddress());
        return map;
    }

}
