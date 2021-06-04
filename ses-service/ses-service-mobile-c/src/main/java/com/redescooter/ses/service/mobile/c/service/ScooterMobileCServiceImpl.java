package com.redescooter.ses.service.mobile.c.service;

import com.redescooter.ses.api.common.enums.scooter.DriverScooterStatusEnums;
import com.redescooter.ses.api.common.enums.user.UserServiceTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.api.mobile.c.exception.MobileCException;
import com.redescooter.ses.api.mobile.c.service.ScooterMobileCService;
import com.redescooter.ses.api.scooter.service.ScooterEmqXService;
import com.redescooter.ses.api.scooter.service.ScooterService;
import com.redescooter.ses.service.mobile.c.dao.UserScooterMapper;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import com.redescooter.ses.service.mobile.c.exception.ExceptionCodeEnums;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author assert
 * @date 2020/11/18 17:50
 */
@DubboService
public class ScooterMobileCServiceImpl implements ScooterMobileCService {

    @Resource
    private UserScooterMapper userScooterMapper;
    @DubboReference
    private ScooterService scooterService;
    @DubboReference
    private ScooterEmqXService scooterEmqXService;

    @Override
    public BaseScooterResult getScooterInfo(GeneralEnter enter) {
        ConUserScooter scooter = userScooterMapper.getUserScooterByUserIdAndStatus(enter.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
//            throw new MobileCException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
//                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
            // todo 分布式异常没有处理  抛出的异常 上个系统接不到 改为返回null
            return null;
        }

        /**
         * 查询车辆实时信息(ECU仪表盘的数据)
         */
        BaseScooterResult scooterResult = scooterService.getScooterInfoById(scooter.getScooterId());

        return scooterResult;
    }

    @Override
    public GeneralResult lock(ScooterLockDTO scooterLockDTO) {
        ConUserScooter scooter = userScooterMapper.getUserScooterByUserIdAndStatus(scooterLockDTO.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
            throw new MobileCException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
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
         * 开关车辆锁
         */
        return scooterEmqXService.lock(scooterLockDTO, scooter.getScooterId(), UserServiceTypeEnum.C.getType());
    }

    @Override
    public GeneralResult scooterNavigation(ScooterNavigationDTO enter) {
        ConUserScooter scooter = userScooterMapper.getUserScooterByUserIdAndStatus(enter.getUserId(),
                DriverScooterStatusEnums.USED.getValue());

        /**
         * 检查当前用户是否分配车辆
         */
        if (null == scooter) {
            throw new MobileCException(ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getCode(),
                    ExceptionCodeEnums.DRIVER_NOT_ASSIGNED_VEHICLE.getMessage());
        }

        /**
         * 开始/结束导航
         */
        return scooterEmqXService.scooterNavigation(enter, scooter.getScooterId(), UserServiceTypeEnum.C.getType());
    }

}
