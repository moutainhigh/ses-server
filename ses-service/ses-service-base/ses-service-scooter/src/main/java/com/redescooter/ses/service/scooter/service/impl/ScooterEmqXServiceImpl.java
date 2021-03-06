package com.redescooter.ses.service.scooter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.redescooter.ses.api.common.constant.Constant;
import com.redescooter.ses.api.common.enums.base.AppVersionTypeEnum;
import com.redescooter.ses.api.common.enums.base.BizType;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.enums.scooter.NavigationStatus;
import com.redescooter.ses.api.common.enums.scooter.ScooterActionResult;
import com.redescooter.ses.api.common.enums.scooter.ScooterActionTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.ScooterLockStatusEnums;
import com.redescooter.ses.api.common.enums.user.UserServiceTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.InsertRideStatDataDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.api.common.vo.version.ReleaseAppVersionParamDTO;
import com.redescooter.ses.api.foundation.service.AppVersionService;
import com.redescooter.ses.api.foundation.service.AppVersionUpdateLogService;
import com.redescooter.ses.api.foundation.vo.app.AppVersionUpdateLogDTO;
import com.redescooter.ses.api.foundation.vo.app.QueryAppVersionResultDTO;
import com.redescooter.ses.api.mobile.b.service.RideStatBService;
import com.redescooter.ses.api.mobile.c.service.RideStatCService;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterLockPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterNavigationPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterTabletUpdatePublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SyncOrderQuantityPublishDTO;
import com.redescooter.ses.service.scooter.config.emqx.MqttClientUtil;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterEcuMapper;
import com.redescooter.ses.service.scooter.dao.ScooterNavigationMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterActionTraceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterActionTrace;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterEcu;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterUpdateRecord;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.scooter.service.base.ScoScooterEcuService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterStatusService;
import com.redescooter.ses.service.scooter.service.base.ScoScooterUpdateRecordService;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import com.redescooter.ses.tool.utils.map.MapUtil;
import com.redescooter.ses.tool.utils.thread.ThreadPoolExecutorUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author assert
 * @date 2020/11/18 18:36
 */
@Slf4j
@DubboService
public class ScooterEmqXServiceImpl implements ScooterEmqXService {

    @DubboReference
    private RideStatBService rideStatBService;

    @DubboReference
    private RideStatCService rideStatCService;

    @DubboReference
    private AppVersionService appVersionService;

    @DubboReference
    private AppVersionUpdateLogService appVersionUpdateLogService;

    @Resource
    private ScooterServiceMapper scooterMapper;

    @Resource
    private ScoScooterActionTraceMapper scooterActionTraceMapper;

    @Resource
    private ScooterNavigationMapper scooterNavigationMapper;

    @Resource
    private ScooterEcuMapper scooterEcuMapper;

    @Autowired
    private ScoScooterService scoScooterService;

    @Autowired
    private ScoScooterEcuService scoScooterEcuService;

    @Resource
    private MqttClientUtil mqttClientUtil;

    @DubboReference
    private IdAppService idAppService;

    @Autowired
    private ScoScooterUpdateRecordService scoScooterUpdateRecordService;

    @Autowired
    private ScoScooterStatusService scoScooterStatusService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult lock(ScooterLockDTO enter, Long scooterId, Integer type) {
        /**
         * ????????????????????????
         */
        BaseScooterResult scooterResult = scooterMapper.getScooterInfoById(scooterId);
        if (null == scooterResult) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        ScooterLockPublishDTO publishDTO = new ScooterLockPublishDTO();
        publishDTO.setTabletSn(scooterResult.getTabletSn());

        /**
         * ?????????????????????,????????????????????????????????????EMQ X??????????????????????????????
         */
        try {
            String actionType = null;

            if (CommonEvent.START.getValue().equals(enter.getEvent())) {
                if (!ScooterLockStatusEnums.LOCK.getValue().equals(scooterResult.getStatus())) {
                    scooterMapper.updateScooterStatusById(ScooterLockStatusEnums.LOCK.getValue(), scooterId, enter.getUserId());
                    actionType = ScooterActionTypeEnums.LOCK.getValue();
                    // set scooter lock type
                    publishDTO.setType(Integer.valueOf(ScooterLockStatusEnums.LOCK.getValue()));

                    // ?????????????????????????????????(???????????????????????????????????????)
                    /*LambdaQueryWrapper<ScoScooterStatus> qw = new LambdaQueryWrapper<>();
                    qw.eq(ScoScooterStatus::getDr, Constant.DR_FALSE);
                    qw.eq(ScoScooterStatus::getScooterId, scooterId);
                    qw.eq(ScoScooterStatus::getLockStatus, "1");
                    qw.orderByAsc(ScoScooterStatus::getCreatedTime);
                    qw.last("limit 1");
                    ScoScooterStatus scooterStatus = scoScooterStatusService.getOne(qw);*/

                    ScoScooter scooter = scoScooterService.getById(scooterId);
                    log.info("scooter????????????:[{}]", scooter);

                    LambdaQueryWrapper<ScoScooterEcu> qw = new LambdaQueryWrapper<>();
                    qw.eq(ScoScooterEcu::getDr, Constant.DR_FALSE);
                    qw.eq(ScoScooterEcu::getScooterNo, scooter.getScooterNo());
                    qw.last("limit 1");
                    ScoScooterEcu scooterStatus = scoScooterEcuService.getOne(qw);
                    log.info("scooterStatus????????????:[{}]", scooterStatus);

                    if (null != scooterStatus) {
                        log.info("?????????,?????????????????????,??????????????????,??????id???:[{}]", scooterId);

                        double mileage = 0.0;
                        if (null != scooterResult.getLatitude() && null != scooterResult.getLongitule()) {
                            // ???????????????????????????
                            mileage = MapUtil.getDistance(enter.getLat(), enter.getLng(), scooterResult.getLatitude().toString(), scooterResult.getLongitule().toString());
                            log.info("?????????,?????????????????????:[{}]", mileage);
                        }

                        // ????????????????????????,??????/s
                        Long duration = (scooterStatus.getCreatedTime().getTime() - System.currentTimeMillis()) / 1000;
                        log.info("?????????,???????????????????????????:[{}]", duration);

                        InsertRideStatDataDTO param = new InsertRideStatDataDTO();
                        param.setMileage(new BigDecimal(mileage));
                        param.setDuration(duration);
                        param.setBizType(BizType.SCOOTER.getValue());
                        param.setBizId(scooterId);
                        if (UserServiceTypeEnum.B.getType().equals(type)) {
                            // ?????????????????????????????????
                            rideStatBService.insertDriverAndScooterRideStat(param);
                        } else {
                            log.info("?????????,??????toc??????????????????,?????????:[{}]", param);
                            rideStatCService.insertDriverAndScooterRideStat(param);
                        }
                    } else {
                        log.info("?????????,??????????????????,????????????????????????");
                    }
                }
            } else {
                if (!ScooterLockStatusEnums.UNLOCK.getValue().equals(scooterResult.getStatus())) {
                    scooterMapper.updateScooterStatusById(ScooterLockStatusEnums.UNLOCK.getValue(), scooterId, enter.getUserId());
                    actionType = ScooterActionTypeEnums.UNLOCK.getValue();
                    // set scooter lock type
                    publishDTO.setType(Integer.valueOf(ScooterLockStatusEnums.UNLOCK.getValue()));
                }
            }

            /**
             * ????????????????????????
             */
            if (null != actionType) {
                ScoScooterActionTrace scooterActionTrace = buildScooterActionTraceData(enter, scooterResult, actionType);
                scooterActionTraceMapper.insert(scooterActionTrace);

                /**
                 * ??????????????????,????????????????????????????????????????????????(tabletSn)??????????????????????????????????????????
                 * Topic???scooter-[?????????????????????]/device-lock
                 */
                ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
                    mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_LOCK_TOPIC, scooterResult.getTabletSn()),
                            JSONObject.toJSONString(publishDTO));
                });
            }
        } catch (Exception e) {
            log.error("???????????????????????????????????????----{}", ExceptionUtils.getStackTrace(e));
        }

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult scooterNavigation(ScooterNavigationDTO enter, Long scooterId, Integer userServiceType) {
        BaseScooterResult scooterResult = scooterMapper.getScooterInfoById(scooterId);
        if (null == scooterResult) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        String actionType = null;
        // ??????????????????
        ScooterNavigationPublishDTO navigationPublish = new ScooterNavigationPublishDTO();
        navigationPublish.setTabletSn(scooterResult.getTabletSn());
        navigationPublish.setLatitude(new BigDecimal(enter.getLat()));
        navigationPublish.setLongitude(new BigDecimal(enter.getLng()));

        /**
         * ????????????????????????????????????(??????????????????????????????????????????)
         */
        ScoScooterNavigation scoScooterNavigation = scooterNavigationMapper
                .getScooterNavigationByScooterIdAndStatus(scooterId, NavigationStatus.START.getValue());

        /**
         * ????????????????????????????????????????????????????????????,????????????????????????????????????
         */
        if (CommonEvent.START.getValue().equals(enter.getEvent())) {
            // ????????????????????????????????????????????????????????????????????????,?????????????????????????????????
            if (null != scoScooterNavigation) {
                scoScooterNavigation.setStatus(NavigationStatus.END.getValue());
                scoScooterNavigation.setUpdatedBy(enter.getUserId());
                scoScooterNavigation.setUpdatedTime(new Date());
                scooterNavigationMapper.updateScooterNavigation(scoScooterNavigation);
            }
            // ????????????
            ScoScooterNavigation scooterNavigationNew = buildScoScooterNavigationData(enter, scooterResult);
            scooterNavigationMapper.insertScooterNavigation(scooterNavigationNew);

            actionType = ScooterActionTypeEnums.START_NAVIGATION.getValue();
            navigationPublish.setType(1);
        } else {
            /**
             * ????????????,???????????????????????????????????????(???????????????????????????????????????)
             */
            if (null != scoScooterNavigation) {
                // ??????
                Double distance = 0.0;
                if (null != scooterResult.getLatitude() && null != scooterResult.getLongitule()) {
                    // ?????????????????????????????????
                    distance = MapUtil.getDistance(enter.getLat(), enter.getLng(),
                            scooterResult.getLatitude().toString(), scooterResult.getLongitule().toString());
                    log.info("???????????????????????????:[{}]", distance);
                }

                // ????????????????????????,??????/s
                Long duration = (scoScooterNavigation.getCreatedTime().getTime() - new Date().getTime()) / 1000;
                log.info("???????????????????????????:[{}]", duration);

                enter.setMileage(String.valueOf(distance));
                enter.setDuration(duration);

                /**
                 * ??????????????????(b/c)????????????????????????
                 */
                InsertRideStatDataDTO param = new InsertRideStatDataDTO();
                param.setMileage(new BigDecimal(enter.getMileage()));
                param.setDuration(enter.getDuration());
                param.setBizType(BizType.SCOOTER.getValue());
                param.setBizId(scooterId);

                if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
                    // ?????????????????????????????????
                    rideStatBService.insertDriverAndScooterRideStat(param);
                } else {
                    log.info("??????toc??????????????????,?????????:[{}]", param);
                    rideStatCService.insertDriverAndScooterRideStat(param);
                }

                scoScooterNavigation.setStatus(NavigationStatus.END.getValue());
                scoScooterNavigation.setUpdatedBy(enter.getUserId());
                scoScooterNavigation.setUpdatedTime(new Date());
                scooterNavigationMapper.updateScooterNavigation(scoScooterNavigation);

                actionType = ScooterActionTypeEnums.END_NAVIGATION.getValue();
                navigationPublish.setType(2);
            }
        }

        /**
         * ????????????????????????
         */
        if (null != actionType) {
            ScooterLockDTO scooterLock = new ScooterLockDTO();
            scooterLock.setUserId(enter.getUserId());
            scooterLock.setTenantId(enter.getTenantId());
            scooterLock.setLat(enter.getLat());
            scooterLock.setLng(enter.getLng());

            ScoScooterActionTrace scooterActionTrace = buildScooterActionTraceData(scooterLock, scooterResult, actionType);
            scooterActionTraceMapper.insert(scooterActionTrace);

            /**
             * ??????????????????,?????????????????????????????????
             */
            ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
                mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_NAVIGATION_TOPIC, scooterResult.getTabletSn()),
                        JSONObject.toJSONString(navigationPublish));
            });
        }

        return new GeneralResult(enter.getRequestId());
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void updateScooterTablet(ReleaseAppVersionParamDTO paramDTO) {
        // ??????????????????????????????
        QueryAppVersionResultDTO appVersion = appVersionService.getAppVersionById(paramDTO.getId());
        List<String> tabletSnList = Arrays.asList(paramDTO.getTabletSnList().split(","));

        // ????????????
        if (4 == paramDTO.getReleaseType()) {
            tabletSnList = scooterMapper.getAllScooterTabletSn();
        }

        /**
         * ????????????????????????????????????,???????????????????????????????????????
         */
        List<AppVersionUpdateLogDTO> appVersionUpdateLogList = new ArrayList<>();

        tabletSnList.forEach(tabletSn -> {
            AppVersionUpdateLogDTO appVersionUpdateLog = new AppVersionUpdateLogDTO();
            appVersionUpdateLog.setTabletSn(tabletSn);
            appVersionUpdateLog.setAppVersionId(appVersion.getId());
            appVersionUpdateLog.setAppVersionType(AppVersionTypeEnum.SCS.getType());
            appVersionUpdateLog.setIsUpdateSuccess(false);
            appVersionUpdateLog.setCreatedBy(paramDTO.getUserId());
            appVersionUpdateLog.setUpdatedBy(paramDTO.getUserId());

            appVersionUpdateLogList.add(appVersionUpdateLog);
        });

        // ??????????????????????????????????????????????????????????????? ???????????????,????????????????????????????????????????????????,??????????????????????????????????????????????????????????????????????????????
        appVersionUpdateLogService.batchUpdateAppVersionUpdateLogStatus(tabletSnList);

        appVersionUpdateLogService.batchInsertAppVersionUpdateLog(appVersionUpdateLogList);

        /**
         * ??????????????????,?????????????????????????????????
         */
        ScooterTabletUpdatePublishDTO tabletUpdatePublish = new ScooterTabletUpdatePublishDTO();
        tabletUpdatePublish.setDownloadLink(appVersion.getUpdateLink());
        tabletUpdatePublish.setVersionCode(appVersion.getCode());

        List<String> newTabletSns = tabletSnList;
        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            newTabletSns.forEach(sn -> {
                tabletUpdatePublish.setTabletSn(sn);
                tabletUpdatePublish.setUpdateCode(UUID.randomUUID().toString().replaceAll("-", ""));
                log.info("??????????????????,????????????????????????????????? sn: " + sn);
                log.info("??????????????????:[{}]", tabletUpdatePublish);
                mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_TABLET_UPDATE_TOPIC, sn),
                        JSONObject.toJSONString(tabletUpdatePublish));

                // ?????????????????????????????????
                ScoScooterUpdateRecord record = new ScoScooterUpdateRecord();
                record.setId(idAppService.getId(SequenceName.SCO_SCOOTER));
                record.setDr(Constant.DR_FALSE);
                record.setTabletSn(tabletUpdatePublish.getTabletSn());
                record.setDownloadLink(tabletUpdatePublish.getDownloadLink());
                record.setVersionCode(tabletUpdatePublish.getVersionCode());
                record.setUpdateCode(tabletUpdatePublish.getUpdateCode());
                record.setFlag(1);
                record.setCreatedBy(paramDTO.getUserId());
                record.setCreatedTime(new Date());
                log.info("?????????????????????????????????????????????:[{}]", record);
                scoScooterUpdateRecordService.save(record);
            });
        });

    }

    @Override
    public GeneralResult setScooterModel(SetScooterModelPublishDTO publishDTO) {
        /**
         * ??????????????????,???????????????????????????????????????
         */
        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            mqttClientUtil.publish(String.format(EmqXTopicConstant.SET_SCOOTER_MODEL_TOPIC, publishDTO.getTabletSn()),
                    JSONObject.toJSONString(publishDTO));
        });

        return new GeneralResult();
    }

    @Override
    public GeneralResult syncOrderQuantity(SyncOrderQuantityPublishDTO publishDTO) {
        /**
         * ??????????????????,??????????????????????????????????????????????????????????????????
         */
        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            mqttClientUtil.publish(String.format(EmqXTopicConstant.SYNC_ORDER_QUANTITY_TOPIC, publishDTO.getTabletSn()),
                    JSONObject.toJSONString(publishDTO));
        });

        return new GeneralResult();
    }


    /**
     * ??????????????????????????????
     * @param scooterLockDTO
     * @param scooterResult
     * @param actionType
     * @return
     */
    private ScoScooterActionTrace buildScooterActionTraceData(ScooterLockDTO scooterLockDTO,
                                                              BaseScooterResult scooterResult, String actionType) {
        ScoScooterActionTrace scooterActionTrace = new ScoScooterActionTrace();
        scooterActionTrace.setId(idAppService.getId(SequenceName.SCO_SCOOTER_ACTION_TRACE));
        scooterActionTrace.setTenantId(scooterLockDTO.getTenantId());
        scooterActionTrace.setUserId(scooterLockDTO.getUserId());
        scooterActionTrace.setScooterId(scooterResult.getId());
        scooterActionTrace.setUserLongitule(new BigDecimal(scooterLockDTO.getLng()));
        scooterActionTrace.setUserLatitude(new BigDecimal(scooterLockDTO.getLat()));
        scooterActionTrace.setUserLocationGeohash(MapUtil.geoHash(scooterLockDTO.getLng(),scooterLockDTO.getLat()));
        scooterActionTrace.setActionType(actionType);
        scooterActionTrace.setActionResult(ScooterActionResult.SUCCESS.getValue());
        scooterActionTrace.setActionTime(new Date());
        scooterActionTrace.setBattery(scooterResult.getBattery());
        scooterActionTrace.setLongitule(scooterResult.getLongitule());
        scooterActionTrace.setLatitude(scooterResult.getLatitude());
        scooterActionTrace.setGeohash(MapUtil.geoHash(scooterResult.getLongitule().toString(),scooterResult.getLatitude().toString()));
        scooterActionTrace.setCreatedBy(scooterLockDTO.getUserId());
        scooterActionTrace.setCreatedTime(new Date());
        scooterActionTrace.setUpdatedBy(scooterLockDTO.getUserId());
        scooterActionTrace.setUpdatedTime(new Date());

        return scooterActionTrace;
    }

    /**
     * ????????????????????????
     * @param scooterNavigation
     * @param scooterResult
     * @return
     */
    private ScoScooterNavigation buildScoScooterNavigationData(ScooterNavigationDTO scooterNavigation, BaseScooterResult scooterResult) {
        // ????????????????????????????????????
        Double distance = MapUtil.getDistance(scooterNavigation.getLat(), scooterNavigation.getLng(),
                scooterResult.getLatitude().toString(), scooterResult.getLongitule().toString());

        ScoScooterNavigation saveNavigation = new ScoScooterNavigation();
        saveNavigation.setId(idAppService.getId(SequenceName.SCO_SCOOTER_NAVIGATION));
        saveNavigation.setScooterId(scooterResult.getId());
        saveNavigation.setScooterNo(scooterResult.getScooterNo());
        saveNavigation.setDestination(Double.toString(distance));
        saveNavigation.setDestinationLatitude(new BigDecimal(scooterNavigation.getLat()));
        saveNavigation.setDestinationLongitude(new BigDecimal(scooterNavigation.getLng()));
        saveNavigation.setCreatedBy(scooterNavigation.getUserId());
        saveNavigation.setCreatedTime(new Date());
        saveNavigation.setUpdatedBy(scooterNavigation.getUserId());
        saveNavigation.setUpdatedTime(new Date());
        saveNavigation.setStatus(NavigationStatus.START.getValue());

        return saveNavigation;
    }


}
