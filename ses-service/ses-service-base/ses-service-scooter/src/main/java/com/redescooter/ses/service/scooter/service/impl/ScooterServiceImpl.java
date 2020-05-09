package com.redescooter.ses.service.scooter.service.impl;
import	java.util.ArrayList;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterModelEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.UpdateStatusEnter;
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
        if (CollectionUtils.isEmpty(scoScooterList) || scoScooterList.size() != enter.size()) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_ALREADY_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_ALREADY_EXIST.getMessage());
        }

        List<ScoScooter> saveScooterList= Lists.newArrayList();
        List<ScoScooterStatus> saveScoScooterStatusList = new ArrayList<> ();

        enter.forEach(item->{
            //保存车辆信息
            ScoScooter saveScooter = buildScooterSingle(item);
            //保存车辆状态
            ScoScooterStatus saveScooterStatus = buildScoScooterStatusSingle(item, saveScooter.getId());
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
}
