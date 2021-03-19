package com.redescooter.ses.service.scooter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnum;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.UpdateStatusEnter;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterLockReportedDTO;
import com.redescooter.ses.service.scooter.constant.ScooterDefaultData;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterEcuMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterStatus;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
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

        List<ScoScooter> saveScooterList= Lists.newArrayList();
        List<ScoScooterStatus> saveScoScooterStatusList = new ArrayList<> ();

        enter.forEach(item->{
            //保存车辆信息
            ScoScooter saveScooter = buildScooterSingle(item);
            //保存车辆状态
            ScoScooterStatus saveScooterStatus = buildScoScooterStatusSingle(item, saveScooter.getId());
            saveScooterList.add(saveScooter);
            saveScoScooterStatusList.add(saveScooterStatus);
        });
        if (CollectionUtils.isNotEmpty(saveScoScooterStatusList)){
            scoScooterStatusService.saveBatch(saveScoScooterStatusList);
        }
        if (CollectionUtils.isNotEmpty(saveScooterList)){
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
     * @Description
     * @Author: alex
     * @Date: 2020/11/16 6:01 下午
     * @Param: id, scooterNo
     * @Return: BaseScooterResult
     * @desc: 车辆基本信息
     * @param id
     * @param scooterNo
     */
    @Override
    public BaseScooterResult scooterInfoByScooterNo(Long id, String scooterNo) {
        return scooterServiceMapper.scooterInfoByScooterNo(id,scooterNo);
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
    public int syncScooterData(List<SyncScooterDataDTO> syncScooterDataList) {
        List<ScoScooter> scooterList = new ArrayList<>();

        syncScooterDataList.forEach(scooterData -> {
            ScoScooter scooter = new ScoScooter();
            BeanUtils.copyProperties(scooterData, scooter);
            scooter.setId(idAppService.getId(SequenceName.SCO_SCOOTER));
            scooter.setStatus(ScooterLockStatusEnums.LOCK.getValue());
            scooter.setTotalMileage(0L);
            scooter.setAvailableStatus(ScooterStatusEnums.AVAILABLE.getValue());
            scooter.setBoxStatus(ScooterLockStatusEnums.LOCK.getValue());
            scooter.setModel(String.valueOf(ScooterModelEnum.SCOOTER_E50.getType()));
            scooter.setCreatedBy(scooterData.getUserId());
            scooter.setCreatedTime(new Date());
            scooter.setUpdatedBy(scooterData.getUserId());
            scooter.setUpdatedTime(new Date());
            scooterList.add(scooter);
        });

        return scooterServiceMapper.batchInsertScooter(scooterList);
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
        // 先删除车辆、
        QueryWrapper<ScoScooter> sc = new QueryWrapper<>();
        sc.eq(ScoScooter.COL_TABLET_SN,sn);
        sc.last("limit 1");
        ScoScooter scooter = scoScooterService.getOne(sc);
        if (scooter != null) {
            scoScooterService.removeById(scooter.getId());
        }
        // 再删除车辆的ECU
        ScooterEcuDTO ecu = scooterEcuMapper.getScooterEcuBySerialNumber(sn);
        if (ecu != null) {
            scooterEcuMapper.deleteScooterEcuById(ecu.getId());
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

}
