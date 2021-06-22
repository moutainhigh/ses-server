package com.redescooter.ses.service.scooter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.api.scooter.vo.UpdateStatusEnter;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterLockReportedDTO;
import com.redescooter.ses.service.scooter.constant.ScooterDefaultData;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterEcuMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterBbi;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterBbiBatteryWare;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterBms;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterEcu;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterMcu;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterMcuControllerInfo;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterStatus;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.scooter.service.base.ScoScooterBbiBatteryWareService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterBbiService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterBmsService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterEcuService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterMcuControllerInfoService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterMcuService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterNavigationService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterStatusService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.map.MapUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName:ScooterServiceImpl
 * @description: ScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 15:04
 */
@DubboService
@Log4j
public class ScooterServiceImpl implements ScooterService {

    @Autowired
    private ScooterServiceMapper scooterServiceMapper;

    @Autowired
    private ScoScooterService scoScooterService;

    @Autowired
    private ScoScooterStatusService scoScooterStatusService;

    @Autowired
    private ScoScooterBbiService scoScooterBbiService;

    @Autowired
    private ScoScooterBbiBatteryWareService scoScooterBbiBatteryWareService;

    @Autowired
    private ScoScooterBmsService scoScooterBmsService;

    @Autowired
    private ScoScooterEcuService scoScooterEcuService;

    @Autowired
    private ScoScooterMcuService scoScooterMcuService;

    @Autowired
    private ScoScooterMcuControllerInfoService scoScooterMcuControllerInfoService;

    @Autowired
    private ScoScooterNavigationService scoScooterNavigationService;

    @Autowired
    private ScooterEcuMapper scooterEcuMapper;

    @DubboReference
    private IdAppService idAppService;


    @Override
    public List<BaseScooterResult> scooterInfor(List<Long> enter) {
        List<BaseScooterResult> scooterResultList = scooterServiceMapper.scooterInfor(enter);
        scooterResultList.forEach(item -> {
            Optional.ofNullable(item).ifPresent(it -> {
                it.setNextMaintenanceKm(ScooterDefaultData.MAINTENANCE_KM.subtract(item.getTotalmileage()));
            });
        });
        return scooterResultList;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult saveScooter(List<BaseScooterEnter> enter) {
        //目前只有车辆新建业务
        List<ScoScooter> scoScooterList = scoScooterService.list(new LambdaQueryWrapper<ScoScooter>().in(ScoScooter::getScooterNo,
                enter.stream().map(BaseScooterEnter::getScooterNo).collect(Collectors.toList())));
        if (CollectionUtils.isNotEmpty(scoScooterList) && scoScooterList.size() != enter.size()) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_ALREADY_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_ALREADY_EXIST.getMessage());
        }

        List<ScoScooter> saveScooterList = Lists.newArrayList();
        List<ScoScooterStatus> saveScoScooterStatusList = new ArrayList<>();

        enter.forEach(item -> {
            //保存车辆信息
            ScoScooter saveScooter = buildScooterSingle(item);
            //保存车辆状态
            ScoScooterStatus saveScooterStatus = buildScoScooterStatusSingle(item, saveScooter.getId());
            saveScooterList.add(saveScooter);
            saveScoScooterStatusList.add(saveScooterStatus);
        });
        if (CollectionUtils.isNotEmpty(saveScoScooterStatusList)) {
            scoScooterStatusService.saveBatch(saveScoScooterStatusList);
        }
        if (CollectionUtils.isNotEmpty(saveScooterList)) {
            //保存车辆信息
            scoScooterService.saveBatch(saveScooterList);
        }
        return null;
    }

    private ScoScooterStatus buildScoScooterStatusSingle(BaseScooterEnter enter, Long scooterId) {
        ScoScooterStatus saveScooterStatus = new ScoScooterStatus();
        saveScooterStatus.setId(idAppService.getId(SequenceName.CON_USER_SCOOTER));
        saveScooterStatus.setDr(0);
        saveScooterStatus.setScooterId(scooterId);
        saveScooterStatus.setScooterEcuId(0L);
        saveScooterStatus.setLockStatus(ScooterLockStatusEnums.LOCK.getValue());
        saveScooterStatus.setTopBoxStatus(ScooterLockStatusEnums.LOCK.getValue());
        saveScooterStatus.setLongitule(enter.getLongitule());
        saveScooterStatus.setLatitude(enter.getLatitude());
        saveScooterStatus.setGeohash(MapUtil.geoHash(enter.getLongitule().toString(), enter.getLatitude().toString()));
        saveScooterStatus.setBattery(enter.getBattery());
        saveScooterStatus.setCumulativeMileage("0");
        saveScooterStatus.setRevision(0);
        saveScooterStatus.setCreatedBy(enter.getUserId());
        saveScooterStatus.setCreatedTime(new Date());
        saveScooterStatus.setUpdatedBy(enter.getUserId());
        saveScooterStatus.setUpdatedTime(new Date());
        return saveScooterStatus;
    }

    private ScoScooter buildScooterSingle(BaseScooterEnter enter) {
        ScoScooter saveScooter = new ScoScooter();
        saveScooter.setId(enter.getId());
        saveScooter.setDr(0);
        saveScooter.setScooterNo(enter.getScooterNo());
        saveScooter.setPicture(null);
        saveScooter.setStatus(ScooterStatusEnums.AVAILABLE.getValue());
        saveScooter.setTotalMileage(BigDecimal.ZERO.longValue());
        saveScooter.setAvailableStatus(ScooterStatusEnums.AVAILABLE.getValue());
        saveScooter.setBoxStatus(ScooterLockStatusEnums.LOCK.getValue());
        saveScooter.setModel(ScooterModelEnums.SCOOTER_125_CC.getValue());
        saveScooter.setLicensePlate(enter.getLicensePlate());
        saveScooter.setLicensePlatePicture(enter.getLicensePlatePicture());
        saveScooter.setLicensePlateTime(new Date());
        saveScooter.setScooterIdPicture(enter.getScooterIdPicture());
        saveScooter.setInsureTime(new Date());
        saveScooter.setInsurance(enter.getInsurance());
        saveScooter.setRevision(0);
        saveScooter.setCreatedBy(enter.getUserId());
        saveScooter.setCreatedTime(new Date());
        saveScooter.setUpdatedBy(enter.getUserId());
        saveScooter.setUpdatedTime(new Date());
        return saveScooter;
    }

    /**
     * 修改 车辆状态
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updateStatus(UpdateStatusEnter enter) {
        ScoScooter scoScooter = scoScooterService.query().eq(ScoScooter.COL_ID, enter.getId()).one();
        if (scoScooter == null) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.equals(enter.getAvailableStatus(), scoScooter.getAvailableStatus())) {
            return new GeneralResult(enter.getRequestId());
        }
        scoScooter.setAvailableStatus(enter.getAvailableStatus());
        scoScooter.setUpdatedBy(enter.getUserId());
        scoScooter.setUpdatedTime(new Date());
        scoScooterService.updateById(scoScooter);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public List<BaseScooterResult> scooterInforByPlates(List<String> enter) {
        List<BaseScooterResult> scooterResultList = scooterServiceMapper.scooterInforByplates(enter);
        scooterResultList.forEach(item -> {
            Optional.ofNullable(item).ifPresent(it -> {
                it.setNextMaintenanceKm(ScooterDefaultData.MAINTENANCE_KM.subtract(item.getTotalmileage()));
            });
        });
        return scooterResultList;
    }

    /**
     * @param id
     * @param scooterNo
     * @Description
     * @Author: alex
     * @Date: 2020/11/16 6:01 下午
     * @Param: id, scooterNo
     * @Return: BaseScooterResult
     * @desc: 车辆基本信息
     */
    @Override
    public BaseScooterResult scooterInfoByScooterNo(Long id, String scooterNo) {
        return scooterServiceMapper.scooterInfoByScooterNo(id, scooterNo);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int updateScooterStatusByJson(ScooterLockReportedDTO scooterLock) {
        String lockStatus = scooterServiceMapper.getScooterStatusByTabletSn(scooterLock.getTabletSn());
        if (StringUtils.isBlank(lockStatus)) {
            // 这里别抛出异常,这里抛出异常可能会导致emq x连接中断
            log.error("【车辆开关状态上报失败】----车辆不存在");
            return 0;
        }

        if (!scooterLock.getStatus().equals(lockStatus)) {
//            scooterServiceMapper.updateScooterStatusByTabletSn(scooterLock.getTabletSn(),
//                    scooterLock.getStatus().toString(), new Date());
        }

        return 1;
    }

    @Override
    public BaseScooterResult getScooterInfoById(Long scooterId) {
        return scooterServiceMapper.getScooterInfoById(scooterId);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int syncScooterData(List<SyncScooterDataDTO> list) {
        List<ScoScooter> scooterList = new ArrayList<>();
        for (SyncScooterDataDTO item : list) {
            ScoScooter scooter = new ScoScooter();
            scooter.setId(idAppService.getId(SequenceName.SCO_SCOOTER));
            scooter.setDr(Constant.DR_FALSE);
            scooter.setScooterNo(item.getScooterNo());
            scooter.setColorId(item.getColorId());
            scooter.setStatus(ScooterLockStatusEnums.LOCK.getValue());
            scooter.setTotalMileage(0L);
            scooter.setAvailableStatus(ScooterStatusEnums.AVAILABLE.getValue());
            scooter.setBoxStatus(ScooterLockStatusEnums.LOCK.getValue());
            scooter.setModel(String.valueOf(ScooterModelEnum.SCOOTER_E50.getType()));
            scooter.setCreatedBy(item.getUserId());
            scooter.setCreatedTime(new Date());
            scooter.setUpdatedBy(item.getUserId());
            scooter.setUpdatedTime(new Date());
            scooterList.add(scooter);
        }
        scoScooterService.saveBatch(scooterList);
        return 1;
        //return scooterServiceMapper.batchInsertScooter(scooterList);
    }

    @Override
    public int countByScooter() {
        return scooterServiceMapper.countByScooter();
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int syncScooterModel(String tabletSn, Integer scooterModel) {
        return scooterServiceMapper.updateScooterModelByTabletSn(tabletSn, scooterModel, new Date());
    }

    @Override
    public BaseScooterResult getScooterByTabletSn(String tabletSn) {
        return scooterServiceMapper.getScooterByTabletSn(tabletSn);
    }

    @Override
    public Long getScooterIdByTabletSn(String tabletSn) {
        return scooterServiceMapper.getScooterIdByTabletSn(tabletSn);
    }

    @Override
    public String getScooterStatusByTabletSn(String tabletSn) {
        return scooterServiceMapper.getScooterStatusByTabletSn(tabletSn);
    }


    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deleteScooterData(String sn) {
        // 先删除车辆
        QueryWrapper<ScoScooter> sc = new QueryWrapper<>();
        sc.eq(ScoScooter.COL_TABLET_SN, sn);
        sc.last("limit 1");
        ScoScooter scooter = scoScooterService.getOne(sc);
        if (scooter != null) {
            scooterServiceMapper.deleteScooter(scooter.getId());
        }
        // 删除车辆的ECU
        ScooterEcuDTO ecu = scooterEcuMapper.getScooterEcuBySerialNumber(sn);
        if (ecu != null) {
            scooterServiceMapper.deleteEcu(ecu.getId());
        }
    }

    @Override
    public List<String> getToDayScooterNos() {
        return scooterServiceMapper.getToDayScooterNos();
    }

    /**
     * 根据scooterId找到最后一次的经纬度
     */
    @Override
    public Map<String, BigDecimal> getPositionByScooterId(Long scooterId) {
        Map<String, BigDecimal> result = Maps.newHashMap();
        LambdaQueryWrapper<ScoScooterStatus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoScooterStatus::getDr, 0);
        wrapper.eq(ScoScooterStatus::getScooterId, scooterId);
        wrapper.orderByDesc(ScoScooterStatus::getCreatedTime);
        wrapper.last("limit 1");
        ScoScooterStatus model = scoScooterStatusService.getOne(wrapper);
        if (null != model) {
            result.put("longitude", model.getLongitule());
            result.put("latitude", model.getLatitude());
        }
        return result;
    }

    /**
     * 根据tabletSn查询sco_scooter
     */
    @Override
    public ScoScooterResult getScoScooterByTableSn(String rsn) {
        ScoScooterResult result = new ScoScooterResult();
        LambdaQueryWrapper<ScoScooter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoScooter::getDr, Constant.DR_FALSE);
        wrapper.eq(ScoScooter::getScooterNo, rsn);
        wrapper.orderByDesc(ScoScooter::getCreatedTime);
        wrapper.last("limit 1");
        ScoScooter scooter = scoScooterService.getOne(wrapper);
        if (null != scooter) {
            BeanUtils.copyProperties(scooter, result);
        }
        return result;
    }

    /**
     * 修改sco_scooter的牌照
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult updateScooterNo(Long id, String licensePlate) {
        scooterServiceMapper.updateScooterNo(id, licensePlate);
        return new GeneralResult();
    }

    /**
     * 根据平板序列号(sn)查询在sco_scooter表是否存在
     */
    @Override
    public Boolean getSnIsExist(String sn) {
        LambdaQueryWrapper<ScoScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooter::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooter::getTabletSn, sn);
        int count = scoScooterService.count(qw);
        if (count > 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 删除车辆
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public String deleteScooter(String tabletSn) {
        String rsn = null;
        ScoScooter scooter = scooterServiceMapper.getScooterInfo(tabletSn);
        if (null == scooter) {
            log.info("删除的车辆为空,请关注这里的代码");
        } else {
            log.info("删除的车辆不为空");
            Long scooterId = scooter.getId();
            rsn = scooter.getScooterNo();

            // 删除sco_scooter表
            scooterServiceMapper.deleteScooter(scooterId);

            // 删除sco_scooter_bbi表
            LambdaQueryWrapper<ScoScooterBbi> bbiWrapper = new LambdaQueryWrapper<>();
            bbiWrapper.eq(ScoScooterBbi::getDr, Constant.DR_FALSE);
            bbiWrapper.eq(ScoScooterBbi::getScooterNo, rsn);
            List<ScoScooterBbi> bbiList = scoScooterBbiService.list(bbiWrapper);
            if (CollectionUtils.isNotEmpty(bbiList)) {
                for (ScoScooterBbi bbi : bbiList) {
                    scooterServiceMapper.deleteBbi(bbi.getId());
                }
            }

            // 删除sco_scooter_bbi_battery_ware表
            LambdaQueryWrapper<ScoScooterBbiBatteryWare> lqw = new LambdaQueryWrapper<>();
            lqw.eq(ScoScooterBbiBatteryWare::getDr, Constant.DR_FALSE);
            lqw.eq(ScoScooterBbiBatteryWare::getScooterNo, rsn);
            List<ScoScooterBbiBatteryWare> list = scoScooterBbiBatteryWareService.list(lqw);
            if (CollectionUtils.isNotEmpty(list)) {
                for (ScoScooterBbiBatteryWare ware : list) {
                    scooterServiceMapper.deleteBbiBatteryWare(ware.getId());
                }
            }

            // 删除sco_scooter_bms表
            LambdaQueryWrapper<ScoScooterBms> bmsWrapper = new LambdaQueryWrapper<>();
            bmsWrapper.eq(ScoScooterBms::getDr, Constant.DR_FALSE);
            bmsWrapper.eq(ScoScooterBms::getScooterNo, rsn);
            List<ScoScooterBms> bmsList = scoScooterBmsService.list(bmsWrapper);
            if (CollectionUtils.isNotEmpty(bmsList)) {
                for (ScoScooterBms bms : bmsList) {
                    scooterServiceMapper.deleteBms(bms.getId());
                }
            }

            // 删除sco_scooter_ecu表
            LambdaQueryWrapper<ScoScooterEcu> ecuWrapper = new LambdaQueryWrapper<>();
            ecuWrapper.eq(ScoScooterEcu::getDr, Constant.DR_FALSE);
            ecuWrapper.eq(ScoScooterEcu::getSn, tabletSn);
            List<ScoScooterEcu> ecuList = scoScooterEcuService.list(ecuWrapper);
            if (CollectionUtils.isNotEmpty(ecuList)) {
                for (ScoScooterEcu ecu : ecuList) {
                    scooterServiceMapper.deleteEcu(ecu.getId());
                }
            }

            // 删除sco_scooter_mcu表
            LambdaQueryWrapper<ScoScooterMcu> mcuWrapper = new LambdaQueryWrapper<>();
            mcuWrapper.eq(ScoScooterMcu::getDr, Constant.DR_FALSE);
            mcuWrapper.eq(ScoScooterMcu::getScooterNo, rsn);
            mcuWrapper.last("limit 1");
            ScoScooterMcu mcu = scoScooterMcuService.getOne(mcuWrapper);
            if (null != mcu) {
                Long mcuId = mcu.getId();
                scooterServiceMapper.deleteMcu(mcuId);

                // 删除sco_scooter_mcu_controller_info表
                LambdaQueryWrapper<ScoScooterMcuControllerInfo> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ScoScooterMcuControllerInfo::getDr, Constant.DR_FALSE);
                wrapper.eq(ScoScooterMcuControllerInfo::getMcuId, mcuId);
                List<ScoScooterMcuControllerInfo> controllerList = scoScooterMcuControllerInfoService.list(wrapper);
                if (CollectionUtils.isNotEmpty(controllerList)) {
                    for (ScoScooterMcuControllerInfo controller : controllerList) {
                        scooterServiceMapper.deleteMcuController(controller.getId());
                    }
                }
            }

            // 删除sco_scooter_navigation表
            LambdaQueryWrapper<ScoScooterNavigation> navigationWrapper = new LambdaQueryWrapper<>();
            navigationWrapper.eq(ScoScooterNavigation::getDr, Constant.DR_FALSE);
            navigationWrapper.eq(ScoScooterNavigation::getScooterId, scooterId);
            List<ScoScooterNavigation> navigationList = scoScooterNavigationService.list(navigationWrapper);
            if (CollectionUtils.isNotEmpty(navigationList)) {
                for (ScoScooterNavigation navigation : navigationList) {
                    scooterServiceMapper.deleteNavigation(navigation.getScooterId());
                }
            }
        }
        return rsn;
    }

    /**
     * 根据rsn获取scooterId
     */
    @Override
    public Long getScooterIdByRsn(StringEnter enter) {
        Long result = null;
        LambdaQueryWrapper<ScoScooter> qw = new LambdaQueryWrapper<>();
        qw.eq(ScoScooter::getDr, Constant.DR_FALSE);
        qw.eq(ScoScooter::getScooterNo, enter.getKeyword().trim());
        qw.last("limit 1");
        ScoScooter scooter = scoScooterService.getOne(qw);
        if (null != scooter) {
            result = scooter.getId();
        }
        return result;
    }

}
