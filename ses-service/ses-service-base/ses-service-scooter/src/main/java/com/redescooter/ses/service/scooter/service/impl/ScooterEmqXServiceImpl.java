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
import com.redescooter.ses.service.scooter.config.RequestKeyProperties;
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
import com.redescooter.ses.tool.crypt.AESUtil;
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

    @Autowired
    private RequestKeyProperties requestKeyProperties;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public GeneralResult lock(ScooterLockDTO enter, Long scooterId, Integer type) {
        /**
         * 检查车辆是否存在
         */
        BaseScooterResult scooterResult = scooterMapper.getScooterInfoById(scooterId);
        if (null == scooterResult) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        ScooterLockPublishDTO publishDTO = new ScooterLockPublishDTO();
        publishDTO.setTabletSn(scooterResult.getTabletSn());

        /**
         * 车辆开关锁操作,后台只记录操作日志和发送EMQ X通知平板端进行锁操作
         */
        try {
            String actionType = null;

            if (CommonEvent.START.getValue().equals(enter.getEvent())) {
                if (!ScooterLockStatusEnums.LOCK.getValue().equals(scooterResult.getStatus())) {
                    scooterMapper.updateScooterStatusById(ScooterLockStatusEnums.LOCK.getValue(), scooterId, enter.getUserId());
                    actionType = ScooterActionTypeEnums.LOCK.getValue();
                    // set scooter lock type
                    publishDTO.setType(Integer.valueOf(ScooterLockStatusEnums.LOCK.getValue()));

                    // 锁住的时候保存骑行数据(司机骑行数据、车辆骑行数据)
                    /*LambdaQueryWrapper<ScoScooterStatus> qw = new LambdaQueryWrapper<>();
                    qw.eq(ScoScooterStatus::getDr, Constant.DR_FALSE);
                    qw.eq(ScoScooterStatus::getScooterId, scooterId);
                    qw.eq(ScoScooterStatus::getLockStatus, "1");
                    qw.orderByAsc(ScoScooterStatus::getCreatedTime);
                    qw.last("limit 1");
                    ScoScooterStatus scooterStatus = scoScooterStatusService.getOne(qw);*/

                    ScoScooter scooter = scoScooterService.getById(scooterId);
                    log.info("scooter的信息为:[{}]", scooter);

                    LambdaQueryWrapper<ScoScooterEcu> qw = new LambdaQueryWrapper<>();
                    qw.eq(ScoScooterEcu::getDr, Constant.DR_FALSE);
                    qw.eq(ScoScooterEcu::getScooterNo, scooter.getScooterNo());
                    qw.last("limit 1");
                    ScoScooterEcu scooterStatus = scoScooterEcuService.getOne(qw);
                    log.info("scooterStatus的信息为:[{}]", scooterStatus);

                    if (null != scooterStatus) {
                        log.info("关锁时,车辆状态不为空,进入预想逻辑,车辆id为:[{}]", scooterId);

                        double mileage = 0.0;
                        if (null != scooterResult.getLatitude() && null != scooterResult.getLongitule()) {
                            // 计算本次行驶公里数
                            mileage = MapUtil.getDistance(enter.getLat(), enter.getLng(), scooterResult.getLatitude().toString(), scooterResult.getLongitule().toString());
                            log.info("关锁时,本次行驶公里为:[{}]", mileage);
                        }

                        // 计算行驶所花时间,单位/s
                        Long duration = (scooterStatus.getCreatedTime().getTime() - System.currentTimeMillis()) / 1000;
                        log.info("关锁时,计算行驶所花时间是:[{}]", duration);

                        InsertRideStatDataDTO param = new InsertRideStatDataDTO();
                        param.setMileage(new BigDecimal(mileage));
                        param.setDuration(duration);
                        param.setBizType(BizType.SCOOTER.getValue());
                        param.setBizId(scooterId);
                        if (UserServiceTypeEnum.B.getType().equals(type)) {
                            // 保存司机、车辆骑行数据
                            rideStatBService.insertDriverAndScooterRideStat(param);
                        } else {
                            log.info("关锁时,进入toc司机骑行数据,参数是:[{}]", param);
                            rideStatCService.insertDriverAndScooterRideStat(param);
                        }
                    } else {
                        log.info("关锁时,车辆状态为空,没有进入预想逻辑");
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
             * 添加车辆操作记录
             */
            if (null != actionType) {
                ScoScooterActionTrace scooterActionTrace = buildScooterActionTraceData(enter, scooterResult, actionType);
                scooterActionTraceMapper.insert(scooterActionTrace);

                /**
                 * 消息通知下发,消息下发时需要根据车辆平板序列号(tabletSn)准确下发到指定车辆平板上面去
                 * Topic：scooter-[车辆平板序列号]/device-lock
                 */
                ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
                    mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_LOCK_TOPIC, scooterResult.getTabletSn()),
                            JSONObject.toJSONString(publishDTO));
                });
            }
        } catch (Exception e) {
            log.error("【车辆锁开关指令下发失败】----{}", ExceptionUtils.getStackTrace(e));
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
        // 导航下发数据
        ScooterNavigationPublishDTO navigationPublish = new ScooterNavigationPublishDTO();
        navigationPublish.setTabletSn(scooterResult.getTabletSn());
        navigationPublish.setLatitude(new BigDecimal(enter.getLat()));
        navigationPublish.setLongitude(new BigDecimal(enter.getLng()));

        /**
         * 查看当前车辆是否正在导航(每次导航都会产生一条导航数据)
         */
        ScoScooterNavigation scoScooterNavigation = scooterNavigationMapper
                .getScooterNavigationByScooterIdAndStatus(scooterId, NavigationStatus.START.getValue());

        /**
         * 如果开启导航的时候有正在导航的则不做处理,没有正在导航的则开启导航
         */
        if (CommonEvent.START.getValue().equals(enter.getEvent())) {
            // 避免上次导航未结束或结束导航失败导致车辆还在导航,无法开启导航的情况出现
            if (null != scoScooterNavigation) {
                scoScooterNavigation.setStatus(NavigationStatus.END.getValue());
                scoScooterNavigation.setUpdatedBy(enter.getUserId());
                scoScooterNavigation.setUpdatedTime(new Date());
                scooterNavigationMapper.updateScooterNavigation(scoScooterNavigation);
            }
            // 开启导航
            ScoScooterNavigation scooterNavigationNew = buildScoScooterNavigationData(enter, scooterResult);
            scooterNavigationMapper.insertScooterNavigation(scooterNavigationNew);

            actionType = ScooterActionTypeEnums.START_NAVIGATION.getValue();
            navigationPublish.setType(1);
        } else {
            /**
             * 结束导航,结束导航的时候保存骑行数据(司机骑行数据、车辆骑行数据)
             */
            if (null != scoScooterNavigation) {
                // 距离
                Double distance = 0.0;
                if (null != scooterResult.getLatitude() && null != scooterResult.getLongitule()) {
                    // 计算本次导航行驶公里数
                    distance = MapUtil.getDistance(enter.getLat(), enter.getLng(),
                            scooterResult.getLatitude().toString(), scooterResult.getLongitule().toString());
                    log.info("本次导航行驶公里为:[{}]", distance);
                }

                // 计算导航所花时间,单位/s
                Long duration = (scoScooterNavigation.getCreatedTime().getTime() - new Date().getTime()) / 1000;
                log.info("计算导航所花时间是:[{}]", duration);

                enter.setMileage(String.valueOf(distance));
                enter.setDuration(duration);

                /**
                 * 根据用户类型(b/c)进行不同业务处理
                 */
                InsertRideStatDataDTO param = new InsertRideStatDataDTO();
                param.setMileage(new BigDecimal(enter.getMileage()));
                param.setDuration(enter.getDuration());
                param.setBizType(BizType.SCOOTER.getValue());
                param.setBizId(scooterId);

                if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
                    // 保存司机、车辆骑行数据
                    rideStatBService.insertDriverAndScooterRideStat(param);
                } else {
                    log.info("进入toc司机骑行数据,参数是:[{}]", param);
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
         * 添加车辆操作记录
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
             * 消息通知下发,通知平板端进行导航操作
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
        // 查询车辆平板版本信息
        QueryAppVersionResultDTO appVersion = appVersionService.getAppVersionById(paramDTO.getId());
        List<String> tabletSnList = Arrays.asList(paramDTO.getTabletSnList().split(","));

        // 全部升级
        if (4 == paramDTO.getReleaseType()) {
            tabletSnList = scooterMapper.getAllScooterTabletSn();
        }

        /**
         * 记录本次车载平板升级日志,主要用于升级失败重试时使用
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

        // 先将本次升级车辆存在于升级日志表的状态改为 “已成功”,然后再往升级日志表中添加升级日志,为了避免升级失败重试时同一辆车出现多个重试版本的情况
        appVersionUpdateLogService.batchUpdateAppVersionUpdateLogStatus(tabletSnList);

        appVersionUpdateLogService.batchInsertAppVersionUpdateLog(appVersionUpdateLogList);

        /**
         * 消息通知下发,通知平板端进行升级操作
         */
        ScooterTabletUpdatePublishDTO tabletUpdatePublish = new ScooterTabletUpdatePublishDTO();
        tabletUpdatePublish.setDownloadLink(appVersion.getUpdateLink());
        tabletUpdatePublish.setVersionCode(appVersion.getCode());

        List<String> newTabletSns = tabletSnList;
        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            newTabletSns.forEach(sn -> {
                tabletUpdatePublish.setTabletSn(sn);
                tabletUpdatePublish.setUpdateCode(UUID.randomUUID().toString().replaceAll("-", ""));
                log.info("消息通知下发,通知平板端进行升级操作 sn: " + sn);
                log.info("下发的消息为:[{}]", tabletUpdatePublish);

                mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_TABLET_UPDATE_TOPIC, sn),
                        JSONObject.toJSONString(tabletUpdatePublish));

                // 新增平板升级更新记录表
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
                log.info("新增平板升级更新记录表的入参是:[{}]", record);
                scoScooterUpdateRecordService.save(record);
            });
        });

    }

    @Override
    public GeneralResult setScooterModel(SetScooterModelPublishDTO publishDTO) {
        /**
         * 消息通知下发,通知平板端进行车型设置操作
         */
        // 数据下发 加密
        log.info("设置软体的原始入参是:[{}]", publishDTO);
        String encryptData;
        try {
            String json = JSONObject.toJSONString(publishDTO);
            log.info("对象转换成json格式字符串为:[{}]", json);
            encryptData = AESUtil.encrypt(json, requestKeyProperties.getAesKey());
        } catch (Exception e) {
            log.error("设置软体数据下发异常", e);
            throw new ScooterException(ExceptionCodeEnums.DATA_ENCRYPT_WRONG.getCode(), ExceptionCodeEnums.DATA_ENCRYPT_WRONG.getMessage());
        }
        log.info("设置软体加密后的信息是:[{}]", encryptData);

        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            mqttClientUtil.publish(String.format(EmqXTopicConstant.SET_SCOOTER_MODEL_TOPIC, publishDTO.getTabletSn()), encryptData);
        });

        return new GeneralResult();
    }

    @Override
    public GeneralResult syncOrderQuantity(SyncOrderQuantityPublishDTO publishDTO) {
        /**
         * 消息通知下发,将当前配送中、待配送的订单数量同步给车载平板
         */
        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            mqttClientUtil.publish(String.format(EmqXTopicConstant.SYNC_ORDER_QUANTITY_TOPIC, publishDTO.getTabletSn()),
                    JSONObject.toJSONString(publishDTO));
        });

        return new GeneralResult();
    }


    /**
     * 组装车辆操作记录数据
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
     * 组装车辆导航信息
     * @param scooterNavigation
     * @param scooterResult
     * @return
     */
    private ScoScooterNavigation buildScoScooterNavigationData(ScooterNavigationDTO scooterNavigation, BaseScooterResult scooterResult) {
        // 获取两个经纬度之间的距离
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
