package com.redescooter.ses.service.scooter.service.impl;
import	java.util.ArrayList;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
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
import com.redescooter.ses.api.scooter.vo.emqx.ScooterLockReportedDTO;
import com.redescooter.ses.service.scooter.constant.ScooterDefaultData;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterStatus;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.scooter.service.base.ScoScooterService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterStatusService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.MapUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName:ScooterServiceImpl
 * @description: ScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 15:04
 */
@Service
@Log4j
public class ScooterServiceImpl implements ScooterService {

    @Autowired
    private ScooterServiceMapper scooterServiceMapper;

    @Autowired
    private ScoScooterService scoScooterService;

    @Autowired
    private ScoScooterStatusService scoScooterStatusService;

    @Reference
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



    @Transactional
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

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int syncScooterData(SyncScooterDataDTO syncScooterData) {
        ScoScooter scooter = new ScoScooter();
        BeanUtils.copyProperties(syncScooterData, scooter);

        scooter.setStatus(ScooterLockStatusEnums.LOCK.getValue());
        scooter.setTotalMileage(0L);
        scooter.setAvailableStatus(ScooterStatusEnums.AVAILABLE.getValue());
        scooter.setBoxStatus(ScooterLockStatusEnums.LOCK.getValue());
        scooter.setModel(String.valueOf(ScooterModelEnum.SCOOTER_E50.getType()));
        scooter.setCreatedBy(syncScooterData.getUserId());
        scooter.setCreatedTime(new Date());
        scooter.setUpdatedBy(syncScooterData.getUserId());
        scooter.setUpdatedTime(new Date());

        return scooterServiceMapper.insertScooter(scooter);
    }

    @Override
    public int countByScooter() {
        return scooterServiceMapper.countByScooter();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int syncScooterModel(Long id, Integer scooterModel) {
        return scooterServiceMapper.updateScooterModelById(id, scooterModel, new Date());
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

}
