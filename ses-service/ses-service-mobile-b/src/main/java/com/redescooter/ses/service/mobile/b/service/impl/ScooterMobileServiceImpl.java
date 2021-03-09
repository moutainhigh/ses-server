package com.redescooter.ses.service.mobile.b.service.impl;

import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;
import com.redescooter.ses.api.foundation.service.base.AccountBaseService;
import com.redescooter.ses.api.foundation.service.base.UserBaseService;
import com.redescooter.ses.api.foundation.vo.user.QueryUserResult;
import com.redescooter.ses.api.hub.service.customer.CusotmerScooterService;
import com.redescooter.ses.api.hub.vo.QueryDriverScooterResult;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.ScooterMobileService;
import com.redescooter.ses.api.mobile.b.vo.LockEnter;
import com.redescooter.ses.api.scooter.service.ScooterIotService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.mobile.b.dao.ScooterMobileServiceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import com.redescooter.ses.service.mobile.b.service.base.CorDriverService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:ScooterServiceImpl
 * @description: ScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 13:23
 */
@DubboService
public class ScooterMobileServiceImpl implements ScooterMobileService {

    @Autowired
    private CorDriverService driverService;

    @Autowired
    private ScooterMobileServiceMapper scooterMobileServiceMapper;

    @DubboReference
    private ScooterService scooterService;

    @DubboReference
    private ScooterIotService scooterIotService;

    @DubboReference
    private AccountBaseService accountBaseService;

    @DubboReference
    private CusotmerScooterService cusotmerScooterService;

    @DubboReference
    private UserBaseService userBaseService;

    /**
     * scooter 信息
     *
     * @param enter
     * @return
     */
    @Override
    public BaseScooterResult scooterInfor(GeneralEnter enter) {

        CorDriverScooter corDriverScooter = null;

        // 查询账户类型
        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);
        if (queryUserResult.getUserType().equals(AccountTypeEnums.APP_RESTAURANT.getAccountType()) || queryUserResult.getUserType().equals(AccountTypeEnums.APP_EXPRESS.getAccountType())) {
            // 获取车辆分配信息
            corDriverScooter = scooterMobileServiceMapper.driverScooterByUserId(enter.getUserId(), DriverScooterStatusEnums.USED.getValue());
        } else {
            // 查询TOC 车辆分配信息
            QueryDriverScooterResult queryDriverScooterResult = cusotmerScooterService.queryDriverScooter(enter);

            if (queryDriverScooterResult == null) {
                return null;
            } else {
                corDriverScooter = new CorDriverScooter();
                BeanUtils.copyProperties(queryDriverScooterResult, corDriverScooter);
            }
        }

        List<BaseScooterResult> scooterResultList = null;
        if (corDriverScooter != null) {
            List<Long> scooterIdList = new ArrayList<>();
            scooterIdList.add(corDriverScooter.getScooterId());
            scooterResultList = scooterService.scooterInfor(scooterIdList);
        }

        return CollectionUtils.isEmpty(scooterResultList) ? null : scooterResultList.get(0);
    }

    /**
     * @param enter
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public GeneralResult lock(LockEnter enter) {
        CorDriverScooter corDriverScooter = null;

        // 查询账户类型
        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);
        if (queryUserResult.getUserType().equals(AccountTypeEnums.APP_RESTAURANT.getAccountType()) || queryUserResult.getUserType().equals(AccountTypeEnums.APP_EXPRESS.getAccountType())) {
            // 获取车辆分配信息
            corDriverScooter = scooterMobileServiceMapper.driverScooterByUserId(enter.getUserId(), DriverScooterStatusEnums.USED.getValue());
        } else {
            // 查询TOC 车辆分配信息
            QueryDriverScooterResult queryDriverScooterResult = cusotmerScooterService.queryDriverScooter(enter);
            if (queryDriverScooterResult != null) {
                corDriverScooter = new CorDriverScooter();
                BeanUtils.copyProperties(queryDriverScooterResult, corDriverScooter);
            }
        }
        if (corDriverScooter == null) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(), ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }
        IotScooterEnter iotScooterEnter = new IotScooterEnter();
        BeanUtils.copyProperties(enter, iotScooterEnter);
        iotScooterEnter.setLongitude(new BigDecimal(enter.getLng()));
        iotScooterEnter.setLatitude(new BigDecimal(enter.getLat()));
        iotScooterEnter.setId(corDriverScooter.getScooterId());
        iotScooterEnter.setBluetoothCommunication(enter.getBluetoothCommunication());
        iotScooterEnter.setEvent(enter.getEvent());
        return scooterIotService.lock(iotScooterEnter);
    }
}
