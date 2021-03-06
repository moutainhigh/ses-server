package com.redescooter.ses.service.scooter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.enums.scooter.NavigationStatus;
import com.redescooter.ses.api.common.enums.scooter.ScooterActionTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.api.scooter.service.ScooterRecordService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.SaveScooterRecordEnter;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterIotServiceMapper;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterNavigationMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.scooter.service.base.ScoScooterService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.tool.utils.map.MapUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*import com.redescooter.ses.api.scooter.service.IotAdminService;*/

/**
 * @ClassName:IotScooterService
 * @description: IotScooterService
 * @author: Alex
 * @Version???1.3
 * @create: 2019/12/27 10:15
 */
@Slf4j
@DubboService
public class ScooterIotServiceImpl implements ScooterIotService {

    @Autowired
    private ScoScooterService scoScooterService;
    @Autowired
    private ScoScooterNavigationMapper scoScooterNavigationMapper;
    @Autowired
    private IdAppService idAppService;
    @Autowired
    private ScooterRecordService scooterRecordService;
    @Autowired
    private ScooterService scooterService;
    /*    @Autowired
        private IotAdminService iotAdminService;*/
    @Autowired
    private ScooterIotServiceMapper scooterIotServiceMapper;


    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult navigation(IotScooterEnter enter) {
        ScoScooter scoScooter = checkIotScooterEnterParameter(enter);

        List<Long> idEnterList = new ArrayList<>();
        idEnterList.add(enter.getId());
        /**
         * -scooterService.scooterInfor(idEnterList)????????????????????????,??????????????????????????????????????????????????????????????????????????????
         * -????????????scooter??? -- sco_scooter_status???
         */
//        List<BaseScooterResult> scooterList = scooterService.scooterInfor(idEnterList);
        BaseScooterResult scooterResult = scooterService.getScooterInfoById(enter.getId());
        if (null == scooterResult) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }
        if (scooterResult.getLatitude() == null) {
            scooterResult.setLatitude(enter.getLatitude());
        }
        if (scooterResult.getLongitule() == null) {
            scooterResult.setLongitule(enter.getLongitude());
        }
        List<SaveScooterRecordEnter<BaseScooterEnter>> saveScooterRecordEnterList = new ArrayList<>();
        SaveScooterRecordEnter<BaseScooterEnter> saveScooterRecordEnter = buildBaseScooterEnterSaveScooterRecordEnterSingle(enter, scooterResult);
        // ????????????????????????????????????
        QueryWrapper<ScoScooterNavigation> scoScooterNavigationQueryWrapper = new QueryWrapper<>();
        scoScooterNavigationQueryWrapper.eq(ScoScooterNavigation.COL_SCOOTER_ID, enter.getId());
        scoScooterNavigationQueryWrapper.eq(ScoScooterNavigation.COL_STATUS, NavigationStatus.START.getValue());
        ScoScooterNavigation scoScooterNavigation = scoScooterNavigationMapper.selectOne(scoScooterNavigationQueryWrapper);

        //???????????????????????????
        if (scoScooterNavigation != null) {
            scoScooterNavigation.setStatus(NavigationStatus.END.getValue());
            scoScooterNavigation.setUpdatedBy(enter.getUserId());
            scoScooterNavigation.setUpdatedTime(new Date());
            scoScooterNavigationMapper.updateById(scoScooterNavigation);

            saveScooterRecordEnter.setActionType(ScooterActionTypeEnums.END_NAVIGATION.getValue());
            saveScooterRecordEnterList.add(saveScooterRecordEnter);
            //??????IOT ?????? ????????????
            //iotAdminService.finishNavigation(scoScooter.getScooterNo());
        }

        if (enter.getBluetoothCommunication()) {
            switch (enter.getEvent()) {
                case "1":
                    ScoScooterNavigation savaNaviation = buildScoScooterNavigation(enter, scooterResult, NavigationStatus.START.getValue());
                    scoScooterNavigationMapper.insert(savaNaviation);
                    saveScooterRecordEnter.setActionType(ScooterActionTypeEnums.START_NAVIGATION.getValue());
                    //??????IOT ??????????????????
                    //iotAdminService.navigation(scoScooter.getScooterNo(),enter.getLongitude().toString(),enter.getLanguage().toString());
                    break;
                case "2":
                    // ???????????? ?????????????????????????????? ????????? ?????????????????????????????????
                    break;
                default:
            }
            saveScooterRecordEnterList.add(saveScooterRecordEnter);
        } else {
            // ????????????
            if (StringUtils.equals(CommonEvent.START.getValue(), enter.getEvent())) {
                ScoScooterNavigation savaNaviation = buildScoScooterNavigation(enter, scooterResult, NavigationStatus.START.getValue());
                scoScooterNavigationMapper.insert(savaNaviation);

                saveScooterRecordEnter.setActionType(ScooterActionTypeEnums.START_NAVIGATION.getValue());
            } else {
                // ???????????? ?????????????????????????????? ????????? ?????????????????????????????????
            }
            saveScooterRecordEnterList.add(saveScooterRecordEnter);
        }
        // ????????????
        scooterRecordService.saveScooterRecords(saveScooterRecordEnterList);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * ??????
     *
     * @param enter
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult lock(IotScooterEnter enter) {
        ScoScooter scoScooter = checkIotScooterEnterParameter(enter);

        List<Long> idEnterList = new ArrayList<>();
        idEnterList.add(enter.getId());
        /**
         * -scooterService.scooterInfor(idEnterList)????????????????????????,??????????????????????????????????????????????????????????????????????????????
         * -????????????scooter??? -- sco_scooter_status???
         */
//        List<BaseScooterResult> scooterList = scooterService.scooterInfor(idEnterList);
        BaseScooterResult scooterResult = scooterService.getScooterInfoById(enter.getId());
        if (null == scooterResult) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        List<SaveScooterRecordEnter<BaseScooterEnter>> saveScooterRecordEnterList = new ArrayList<>();
        SaveScooterRecordEnter<BaseScooterEnter> saveScooterRecordEnter = buildBaseScooterEnterSaveScooterRecordEnterSingle(enter, scooterResult);

        if (StringUtils.equals(enter.getEvent(), CommonEvent.START.getValue())) {
            if (StringUtils.equals(scooterResult.getStatus(), ScooterLockStatusEnums.LOCK.getValue())) {
                return new GeneralResult(enter.getRequestId());
            } else {
                // ??????????????????
                scooterIotServiceMapper.updateScooterLock(ScooterLockStatusEnums.LOCK.getValue(), enter.getUserId(), enter.getId());

                saveScooterRecordEnter.setActionType(ScooterActionTypeEnums.LOCK.getValue());
                saveScooterRecordEnterList.add(saveScooterRecordEnter);

                //??????IOT ?????? ????????????
                //iotAdminService.masterLock(scoScooter.getScooterNo(), Lock.MASTER);
            }
        } else {
            if (StringUtils.equals(scooterResult.getStatus(), ScooterLockStatusEnums.UNLOCK.getValue())) {
                return new GeneralResult(enter.getRequestId());
            } else {
                // ??????????????????
                scooterIotServiceMapper.updateScooterLock(ScooterLockStatusEnums.UNLOCK.getValue(), enter.getUserId(), enter.getId());

                saveScooterRecordEnter.setActionType(ScooterActionTypeEnums.UNLOCK.getValue());
                saveScooterRecordEnterList.add(saveScooterRecordEnter);
                //IOT??????????????????
                //iotAdminService.unMasterLocklock(scoScooter.getScooterNo(), Lock.MASTER);
            }
        }

        // ????????????
        scooterRecordService.saveScooterRecords(saveScooterRecordEnterList);
        return new GeneralResult(enter.getRequestId());
    }

    @Override
    public GeneralResult obd(IotScooterEnter enter) {
        return null;
    }

    @Override
    public GeneralResult box(IotScooterEnter enter) {
        return null;
    }


    /**
     * IotScooterEnter ????????????
     *
     * @param enter
     */
    private ScoScooter checkIotScooterEnterParameter(IotScooterEnter enter) {
        ScoScooter scoScooter = scoScooterService.getById(enter.getId());
        if (scoScooter == null) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }
        if (StringUtils.isEmpty(enter.getEvent())) {
            throw new ScooterException(ExceptionCodeEnums.EVENT_IS_EMPTY.getCode(), ExceptionCodeEnums.EVENT_IS_EMPTY.getMessage());
        }
        if (enter.getLatitude() == null || enter.getLongitude() == null) {
            throw new ScooterException(ExceptionCodeEnums.POSITIONING_DATA_IS_EMPTY.getCode(), ExceptionCodeEnums.POSITIONING_DATA_IS_EMPTY.getMessage());
        }
        return scoScooter;
    }

    private ScoScooterNavigation buildScoScooterNavigation(IotScooterEnter enter, BaseScooterResult scoScooter, String status) {

        Double distance = MapUtil.getDistance(enter.getLatitude().toString(), enter.getLongitude().toString(), scoScooter.getLatitude().toString(), enter.getLongitude().toString());

        ScoScooterNavigation savaNaviation = new ScoScooterNavigation();
        savaNaviation.setId(idAppService.getId(SequenceName.SCO_SCOOTER_NAVIGATION));
        savaNaviation.setDr(0);
        savaNaviation.setScooterId(scoScooter.getId());
        savaNaviation.setScooterNo(scoScooter.getScooterNo());
        savaNaviation.setDestination(Double.toString(distance));
        savaNaviation.setDestinationLatitude(enter.getLongitude());
        savaNaviation.setDestinationLongitude(enter.getLatitude());
        savaNaviation.setCreatedBy(enter.getUserId());
        savaNaviation.setCreatedTime(new Date());
        savaNaviation.setUpdatedBy(enter.getUserId());
        savaNaviation.setUpdatedTime(new Date());
        savaNaviation.setStatus(status);
        return savaNaviation;
    }

    /**
     * buildBaseScooterEnterSaveScooterRecordEnter
     *
     * @param enter
     * @param scooterResult
     * @return
     */
    private SaveScooterRecordEnter<BaseScooterEnter> buildBaseScooterEnterSaveScooterRecordEnterSingle(IotScooterEnter enter, BaseScooterResult scooterResult) {
        BaseScooterEnter baseScooterEnter = new BaseScooterEnter();
        BeanUtils.copyProperties(scooterResult, baseScooterEnter);
        baseScooterEnter.setUserId(enter.getUserId());
        baseScooterEnter.setTenantId(enter.getTenantId());
        SaveScooterRecordEnter<BaseScooterEnter> saveScooterRecordEnter = new SaveScooterRecordEnter();
        saveScooterRecordEnter.setLatitude(enter.getLatitude());
        saveScooterRecordEnter.setLongitude(enter.getLongitude());
        saveScooterRecordEnter.setT(baseScooterEnter);
        saveScooterRecordEnter.setUserId(enter.getUserId());
        saveScooterRecordEnter.setTenantId(enter.getTenantId());
        return saveScooterRecordEnter;
    }
}
