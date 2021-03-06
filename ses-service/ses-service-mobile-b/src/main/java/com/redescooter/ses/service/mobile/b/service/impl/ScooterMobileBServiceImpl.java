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
import java.math.BigDecimal;
import java.util.Map;

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
         * ????????????????????????????????????
         */
        if (null == scooter) {
//            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
//                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
            // todo ???????????????????????????  ??????????????? ????????????????????? ????????????null
            return null;
        }

        /**
         * ????????????????????????(ECU??????????????????)
         */
        BaseScooterResult scooterResult = scooterService.getScooterInfoById(scooter.getScooterId());

        return scooterResult;
    }

    @Override
    public GeneralResult lock(ScooterLockDTO scooterLockDTO) {
        CorDriverScooter scooter = scooterMobileServiceMapper.driverScooterByUserId(scooterLockDTO.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * ????????????????????????????????????
         */
        if (null == scooter) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }

        if ("0".equals(scooterLockDTO.getLat()) && "0".equals(scooterLockDTO.getLng())) {
            Map<String, BigDecimal> map = scooterService.getPositionByScooterId(scooter.getScooterId());
            if (null != map) {
                BigDecimal longitude = map.get("longitude");
                BigDecimal latitude = map.get("latitude");
                scooterLockDTO.setLng(null == longitude ? String.valueOf(BigDecimal.ZERO) : String.valueOf(longitude));
                scooterLockDTO.setLat(null == latitude ? String.valueOf(BigDecimal.ZERO) : String.valueOf(latitude));
            }
        }

        /**
         * ???????????????
         */
        return scooterEmqXService.lock(scooterLockDTO, scooter.getScooterId(), UserServiceTypeEnum.B.getType());
    }

    @Override
    public GeneralResult scooterNavigation(ScooterNavigationDTO scooterNavigation) {
        CorDriverScooter scooter = scooterMobileServiceMapper.driverScooterByUserId(scooterNavigation.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * ????????????????????????????????????
         */
        if (null == scooter) {
            throw new MobileBException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }

        /**
         * ??????/????????????
         */
        return scooterEmqXService.scooterNavigation(scooterNavigation, scooter.getScooterId(), UserServiceTypeEnum.B.getType());
    }

    /**
     * ??????cor_driver???
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
     * ??????cor_driver_scooter???
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
