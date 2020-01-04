package com.redescooter.ses.service.mobile.b.service.impl;

import com.redescooter.ses.api.common.enums.base.AccountTypeEnums;
import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
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
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverScooterMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class ScooterMobileServiceImpl implements ScooterMobileService {

    @Autowired
    private CorDriverScooterMapper corDriverScooterMapper;

    @Autowired
    private ScooterMobileServiceMapper scooterMobileServiceMapper;

    @Reference
    private ScooterService scooterService;

    @Reference
    private ScooterIotService scooterIotService;

    @Reference
    private AccountBaseService accountBaseService;

    @Reference
    private CusotmerScooterService cusotmerScooterService;

    @Reference
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
        if (queryUserResult.getUserType() == AccountTypeEnums.APP_RESTAURANT.getAccountType() || queryUserResult.getUserType() == AccountTypeEnums.APP_RESTAURANT.getAccountType()) {
            // 获取车辆分配信息
            corDriverScooter = scooterMobileServiceMapper.driverScooterByUserId(enter.getUserId(), DriverScooterStatusEnums.USED.getValue());
        } else {
            // 查询TOC 车辆分配信息
            IdEnter idEnter = new IdEnter();
            BeanUtils.copyProperties(enter, idEnter);
            QueryDriverScooterResult queryDriverScooterResult = cusotmerScooterService.queryDriverScooter(idEnter);
            corDriverScooter = new CorDriverScooter();
            BeanUtils.copyProperties(queryDriverScooterResult, corDriverScooter);
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
    @Transactional
    @Override
    public GeneralResult lock(LockEnter enter) {
        CorDriverScooter corDriverScooter = null;

        // 查询账户类型
        QueryUserResult queryUserResult = userBaseService.queryUserById(enter);
        if (queryUserResult.getUserType() == AccountTypeEnums.APP_RESTAURANT.getAccountType() || queryUserResult.getUserType() == AccountTypeEnums.APP_RESTAURANT.getAccountType()) {
            // 获取车辆分配信息
            corDriverScooter = scooterMobileServiceMapper.driverScooterByUserId(enter.getUserId(), DriverScooterStatusEnums.USED.getValue());
        } else {
            // 查询TOC 车辆分配信息
            IdEnter idEnter = new IdEnter();
            BeanUtils.copyProperties(enter, idEnter);
            QueryDriverScooterResult queryDriverScooterResult = cusotmerScooterService.queryDriverScooter(idEnter);
            corDriverScooter = new CorDriverScooter();
            BeanUtils.copyProperties(queryDriverScooterResult, corDriverScooter);
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
