package com.redescooter.ses.service.mobile.b.service.impl;

import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.enums.user.UserServiceTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.api.mobile.b.exception.MobileBException;
import com.redescooter.ses.api.mobile.b.service.ScooterMobileBService;
import com.redescooter.ses.api.mobile.b.vo.CorDriver;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.mobile.b.dao.ScooterMobileServiceMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverMapper;
import com.redescooter.ses.service.mobile.b.dao.base.CorDriverScooterMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.redescooter.ses.service.mobile.b.exception.ExceptionCodeEnums;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/11/18 17:49
 */
@DubboService
public class ScooterMobileBServiceImpl implements ScooterMobileBService {

    @Resource
    private ScooterMobileServiceMapper scooterMobileServiceMapper;
    @DubboReference
    private ScooterService scooterService;
    @DubboReference
    private ScooterEmqXService scooterEmqXService;

    @Autowired
    private CorDriverMapper corDriverMapper;

    @Autowired
    private CorDriverScooterMapper corDriverScooterMapper;

    @Override
    public BaseScooterResult getScooterInfo(GeneralEnter enter) {
        CorDriverScooter scooter = scooterMobileServiceMapper.driverScooterByUserId(enter.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }

        /**
         * 查询车辆实时信息(ECU仪表盘的数据)
         */
        BaseScooterResult scooterResult = scooterService.getScooterInfoById(scooter.getScooterId());

        return scooterResult;
    }

    @Override
    public GeneralResult lock(ScooterLockDTO scooterLockDTO) {
        CorDriverScooter scooter = scooterMobileServiceMapper.driverScooterByUserId(scooterLockDTO.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }

        /**
         * 开关车辆锁
         */
        return scooterEmqXService.lock(scooterLockDTO, scooter.getScooterId());
    }

    @Override
    public GeneralResult scooterNavigation(ScooterNavigationDTO scooterNavigation) {
        CorDriverScooter scooter = scooterMobileServiceMapper.driverScooterByUserId(scooterNavigation.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }

        /**
         * 开始/结束导航
         */
        return scooterEmqXService.scooterNavigation(scooterNavigation, scooter.getScooterId(), UserServiceTypeEnum.B.getType());
    }

    /**
     * 新增cor_driver表
     *
     * @param corDriver
     */
    @Override
    public GeneralResult addCorDriver(CorDriver corDriver) {
        com.redescooter.ses.service.mobile.b.dm.base.CorDriver model = new com.redescooter.ses.service.mobile.b.dm.base.CorDriver();
        BeanUtils.copyProperties(corDriver, model);
        corDriverMapper.insert(model);
        return new GeneralResult();
    }

    /**
     * 新增cor_driver_scooter表
     *
     * @param corDriverScooter
     */
    @Override
    public GeneralResult addCorDriverScooter(com.redescooter.ses.api.mobile.b.vo.CorDriverScooter corDriverScooter) {
        CorDriverScooter model = new CorDriverScooter();
        BeanUtils.copyProperties(corDriverScooter, model);
        corDriverScooterMapper.insert(model);
        return new GeneralResult();
    }

}
