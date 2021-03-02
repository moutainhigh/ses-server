package com.redescooter.ses.service.scooter.service.impl;

import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.mobile.b.service.meter.MeterService;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterDeliveryOrderReuslt;
import com.redescooter.ses.api.mobile.b.vo.meter.MeterOrderEnter;
import com.redescooter.ses.api.scooter.service.*;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterAllReportedDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SyncOrderQuantityPublishDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/11/23 14:57
 */
@Slf4j
@DubboService
public class ScooterAllReportedServiceImpl implements ScooterAllReportedService {

    @DubboReference
    private ScooterEcuService scooterEcuService;
    @DubboReference
    private ScooterBbiService scooterBbiService;
    @DubboReference
    private ScooterMcuService scooterMcuService;
    @DubboReference
    private ScooterService scooterService;
    @DubboReference
    private MeterService meterService;
    @DubboReference
    private ScooterEmqXService scooterEmqXService;
    @DubboReference
    private UserBaseService userBaseService;
    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertScooterAllInfo(ScooterAllReportedDTO scooterAll) {
        try {
            transactionTemplate.execute(scooterAllStatus -> {
                try {
                    ScooterEcuDTO scooterEcu = scooterAll.getEcu();

                    // ECU仪表数据
                    scooterEcuService.insertScooterEcuByEmqX(scooterEcu);
                    // BBI、BMS、BatteryWare 电池相关数据
                    scooterBbiService.insertScooterBbiByEmqX(scooterAll.getBbi());
                    // MCU控制器数据
                    scooterMcuService.insertScooterMcuByEmqX(scooterAll.getMcu());

                    /**
                     * 车辆解锁时需要做的操作 -- 数据同步
                     */
                    if (!scooterEcu.getScooterLock()) {
                        MeterOrderEnter meterOrderEnter = new MeterOrderEnter();
                        meterOrderEnter.setSn(scooterEcu.getTabletSn());

                        MeterDeliveryOrderReuslt result = meterService.meterOrder(meterOrderEnter);

                        /**
                         * 同步当前配送中、待配送的订单数量到车载平板
                         */
                        SyncOrderQuantityPublishDTO publishDTO = new SyncOrderQuantityPublishDTO();
                        publishDTO.setTabletSn(scooterEcu.getTabletSn());
                        publishDTO.setQuantity(result.getRemainingOrderNum());

                        scooterEmqXService.syncOrderQuantity(publishDTO);

                        /**
                         * 如果有正在配送的订单,如果有则打开导航
                         */
                        if (null != result.getId()) {
                            ScooterNavigationDTO scooterNavigation = new ScooterNavigationDTO();
                            scooterNavigation.setEvent(CommonEvent.START.getValue());
                            scooterNavigation.setLat(String.valueOf(result.getLatitude()));
                            scooterNavigation.setLng(String.valueOf(result.getLongitude()));
//                            scooterNavigation.setLat(String.valueOf(scooterEcu.getLatitude()));
//                            scooterNavigation.setLng(String.valueOf(scooterEcu.getLongitude()));

                            Long scooterId = scooterService.getScooterIdByTabletSn(scooterEcu.getTabletSn());

                            GeneralEnter enter = new GeneralEnter();
                            enter.setUserId(result.getUserId());

                            scooterEmqXService.scooterNavigation(scooterNavigation, scooterId, getUserServiceType(enter));
                        }

                    }
                } catch (Exception e) {
                    log.error("【车辆整车数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
                    scooterAllStatus.setRollbackOnly();
                }
                return 1;
            });
        } catch (Exception e) {
            log.error("【车辆整车数据上报失败】----{}", ExceptionUtils.getStackTrace(e));
        }

        return 1;
    }


    /**
     * 获取用户业务类型 1-2B 2-2C
     * @param enter
     * @return
     */
    private int getUserServiceType(GeneralEnter enter) {
        int userServiceType = 0;

        /**
         * 2B用户 ---- Type=2,3,4,5,7
         * 2C用户 ---- Type=6
         */
        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);
        if (!AccountTypeEnums.WEB_REPAIR.getAccountType().equals(queryUserResult.getUserType())
                || !AccountTypeEnums.APP_PERSONAL.getAccountType().equals(queryUserResult.getUserType())) {
            userServiceType = 1;
        } else if (AccountTypeEnums.APP_PERSONAL.getAccountType().equals(queryUserResult.getUserType())) {
            userServiceType = 2;
        }

        return userServiceType;
    }

}
