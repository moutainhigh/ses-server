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
import com.redescooter.ses.api.scooter.service.ScooterAllReportedService;
import com.redescooter.ses.api.scooter.service.ScooterBbiService;
import com.redescooter.ses.api.scooter.service.ScooterEcuService;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterMcuService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterAllReportedDTO;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterEcuDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SyncOrderQuantityPublishDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

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

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public int insertScooterAllInfo(ScooterAllReportedDTO scooterAll) {
        try {
            try {
                ScooterEcuDTO scooterEcu = scooterAll.getEcu();

                // ECU????????????
                scooterEcuService.insertScooterEcuByEmqX(scooterEcu);
                // BBI???BMS???BatteryWare ??????????????????
                scooterBbiService.insertScooterBbiByEmqX(scooterAll.getBbi());
                // MCU???????????????
                scooterMcuService.insertScooterMcuByEmqX(scooterAll.getMcu());

                /**
                 * ????????????????????????????????? -- ????????????
                 */
                if (!scooterEcu.getScooterLock()) {
                    MeterOrderEnter meterOrderEnter = new MeterOrderEnter();
                    meterOrderEnter.setSn(scooterEcu.getTabletSn());

                    MeterDeliveryOrderReuslt result = meterService.meterOrder(meterOrderEnter);

                    /**
                     * ???????????????????????????????????????????????????????????????
                     */
                    SyncOrderQuantityPublishDTO publishDTO = new SyncOrderQuantityPublishDTO();
                    publishDTO.setTabletSn(scooterEcu.getTabletSn());
                    publishDTO.setQuantity(result.getRemainingOrderNum());

                    scooterEmqXService.syncOrderQuantity(publishDTO);

                    /**
                     * ??????????????????????????????,????????????????????????
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
                log.error("????????????????????????????????????----{}", ExceptionUtils.getStackTrace(e));
            }
        } catch (Exception e) {
            log.error("????????????????????????????????????----{}", ExceptionUtils.getStackTrace(e));
        }
        return 1;
    }


    /**
     * ???????????????????????? 1-2B 2-2C
     * @param enter
     * @return
     */
    private int getUserServiceType(GeneralEnter enter) {
        int userServiceType = 0;

        /**
         * 2B?????? ---- Type=2,3,4,5,7
         * 2C?????? ---- Type=6
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
