package com.redescooter.ses.service.scooter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.enums.base.BizType;
import com.redescooter.ses.api.common.enums.scooter.*;
import com.redescooter.ses.api.common.enums.user.UserServiceTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.InsertRideStatDataDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.api.mobile.b.service.RideStatBService;
import com.redescooter.ses.api.mobile.c.exception.MobileCException;
import com.redescooter.ses.api.mobile.c.service.RideStatCService;
import com.redescooter.ses.api.scooter.exception.ScooterException;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterLockPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterNavigationPublishDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterTabletUpdatePublishDTO;
import com.redescooter.ses.service.scooter.config.emqx.MqttClientUtil;
import com.redescooter.ses.service.scooter.constant.SequenceName;
import com.redescooter.ses.service.scooter.dao.ScooterNavigationMapper;
import com.redescooter.ses.service.scooter.dao.ScooterServiceMapper;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterActionTraceMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterActionTrace;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;
import com.redescooter.ses.service.scooter.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import com.redescooter.ses.tool.utils.MapUtil;
import com.redescooter.ses.tool.utils.SesStringUtils;
import com.redescooter.ses.tool.utils.thread.ThreadPoolExecutorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author assert
 * @date 2020/11/18 18:36
 */
@Slf4j
@Service
public class ScooterEmqXServiceImpl implements ScooterEmqXService {

    @Resource
    private ScooterServiceMapper scooterMapper;
    @Resource
    private ScoScooterActionTraceMapper scooterActionTraceMapper;
    @Resource
    private ScooterNavigationMapper scooterNavigationMapper;
    @Reference
    private RideStatBService rideStatBService;
    @Reference
    private RideStatCService rideStatCService;
    @Resource
    private MqttClientUtil mqttClientUtil;
    @Resource
    private IdAppService idAppService;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public GeneralResult lock(ScooterLockDTO scooterLockDTO, Long scooterId) {
        /**
         * 检查车辆是否存在
         */
        BaseScooterResult scooterResult = scooterMapper.getScooterInfoById(scooterId);
        if (null == scooterResult) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        ScooterLockPublishDTO publishDTO = new ScooterLockPublishDTO();
        publishDTO.setTabletSn(scooterResult.getTabletSn());

        /**
         * 车辆开关锁操作,后台只记录操作日志和发送EMQ X通知平板端进行锁操作
         */
        transactionTemplate.execute(lockPublishStatus -> {
            try {
                String actionType = null;

                if (CommonEvent.START.getValue().equals(scooterLockDTO.getEvent())) {
                    if (!ScooterLockStatusEnums.LOCK.getValue().equals(scooterResult.getStatus())) {
                        scooterMapper.updateScooterStatusById(ScooterLockStatusEnums.LOCK.getValue(), scooterId, scooterLockDTO.getUserId());
                        actionType = ScooterActionTypeEnums.LOCK.getValue();
                        // set scooter lock type
                        publishDTO.setType(Integer.valueOf(ScooterLockStatusEnums.LOCK.getValue()));
                    }
                } else {
                    if (!ScooterLockStatusEnums.UNLOCK.getValue().equals(scooterResult.getStatus())) {
                        scooterMapper.updateScooterStatusById(ScooterLockStatusEnums.UNLOCK.getValue(), scooterId, scooterLockDTO.getUserId());
                        actionType = ScooterActionTypeEnums.UNLOCK.getValue();
                        // set scooter lock type
                        publishDTO.setType(Integer.valueOf(ScooterLockStatusEnums.UNLOCK.getValue()));
                    }
                }

                /**
                 * 添加车辆操作记录
                 */
                if (null != actionType) {
                    ScoScooterActionTrace scooterActionTrace = buildScooterActionTraceData(scooterLockDTO, scooterResult, actionType);
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
                lockPublishStatus.setRollbackOnly();
            }
            return 1;
        });

        // 下发平板升级指令
//        sendTabletUpdateEMQ();

        return new GeneralResult(scooterLockDTO.getRequestId());
    }

    @Override
    public GeneralResult scooterNavigation(ScooterNavigationDTO scooterNavigation, Long scooterId, Integer userServiceType) {
        BaseScooterResult scooterResult = scooterMapper.getScooterInfoById(scooterId);
        if (null == scooterResult) {
            throw new ScooterException(ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getCode(),
                    ExceptionCodeEnums.SCOOTER_IS_NOT_EXIST.getMessage());
        }

        String actionType = null;
        // 导航下发数据
        ScooterNavigationPublishDTO navigationPublish = new ScooterNavigationPublishDTO();
        navigationPublish.setTabletSn(scooterResult.getTabletSn());
        navigationPublish.setLatitude(new BigDecimal(scooterNavigation.getLat()));
        navigationPublish.setLongitude(new BigDecimal(scooterNavigation.getLng()));

        /**
         * 查看当前车辆是否正在导航(每次导航都会产生一条导航数据)
         */
        ScoScooterNavigation scoScooterNavigation = scooterNavigationMapper
                .getScooterNavigationByScooterIdAndStatus(scooterId, NavigationStatus.START.getValue());

        /**
         * 如果开启导航的时候有正在导航的则不做处理,没有正在导航的则开启导航
         */
        if (CommonEvent.START.getValue().equals(scooterNavigation.getEvent())) {
            if (null == scoScooterNavigation) {
                // 开启导航
                ScoScooterNavigation scooterNavigationNew = buildScoScooterNavigationData(scooterNavigation, scooterResult);
                scooterNavigationMapper.insertScooterNavigation(scooterNavigationNew);

                actionType = ScooterActionTypeEnums.START_NAVIGATION.getValue();
                navigationPublish.setType(1);
            }
        } else {
            /**
             * 结束导航,结束导航的时候保存骑行数据(司机骑行数据、车辆骑行数据)
             */
            if (null != scoScooterNavigation) {
                if (SesStringUtils.isBlank(scooterNavigation.getMileage())){
                    throw new MobileCException(ExceptionCodeEnums.MILEAGE_IS_EMPTY.getCode(),ExceptionCodeEnums.MILEAGE_IS_EMPTY.getMessage());
                }
                if (null == scooterNavigation.getDuration() || 0 == scooterNavigation.getDuration()){
                    throw new MobileCException(ExceptionCodeEnums.DURATION_IS_EMPTY.getCode(),ExceptionCodeEnums.DURATION_IS_EMPTY.getMessage());
                }

                /**
                 * 根据用户类型(b/c)进行不同业务处理
                 */
                InsertRideStatDataDTO rideStatData = new InsertRideStatDataDTO();
                BeanUtils.copyProperties(scooterNavigation, rideStatData);
                rideStatData.setMileage(new BigDecimal(scooterNavigation.getDuration()));
                rideStatData.setBizId(scooterId);
                rideStatData.setBizType(BizType.SCOOTER.getValue());
                if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
                    // 保存司机、车辆骑行数据
                    rideStatBService.insertDriverAndScooterRideStat(rideStatData);
                } else {
                    rideStatCService.insertDriverAndScooterRideStat(rideStatData);
                }

                scoScooterNavigation.setStatus(NavigationStatus.END.getValue());
                scoScooterNavigation.setUpdatedBy(scooterNavigation.getUserId());
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
            scooterLock.setUserId(scooterNavigation.getUserId());
            scooterLock.setTenantId(scooterNavigation.getTenantId());
            scooterLock.setLat(scooterNavigation.getLat());
            scooterLock.setLng(scooterNavigation.getLng());

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

        return new GeneralResult(scooterNavigation.getRequestId());
    }

    /**
     * 发送平板更新EMQ X消息
     * TODO 待开发....
     */
    private void sendTabletUpdateEMQ() {
        ScooterTabletUpdatePublishDTO tabletUpdatePublish = new ScooterTabletUpdatePublishDTO();
        tabletUpdatePublish.setTabletSn("OF894HSG4T9LHECY");
        tabletUpdatePublish.setDownloadLink("http://test.redescooter.net/download/scs_code4.apk");
        tabletUpdatePublish.setVersionCode("4");

        mqttClientUtil.publish(String.format(EmqXTopicConstant.SCOOTER_TABLET_UPDATE_TOPIC, "OF894HSG4T9LHECY"),
                    JSONObject.toJSONString(tabletUpdatePublish));
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
