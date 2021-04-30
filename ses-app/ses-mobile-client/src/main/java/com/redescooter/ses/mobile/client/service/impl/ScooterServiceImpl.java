package com.redescooter.ses.mobile.client.service.impl;

import com.redescooter.ses.api.common.enums.scooter.CommonEvent;
import com.redescooter.ses.api.common.enums.user.UserServiceTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.api.mobile.b.service.ScooterMobileBService;
import com.redescooter.ses.api.mobile.c.service.ScooterMobileCService;
import com.redescooter.ses.mobile.client.config.UserComponent;
import com.redescooter.ses.mobile.client.exception.ExceptionCodeEnums;
import com.redescooter.ses.mobile.client.exception.SesMobileClientException;
import com.redescooter.ses.mobile.client.service.ScooterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 车辆相关业务接口实现类
 *
 * @author assert
 * @date 2020/11/18 15:29
 */
@Service
public class ScooterServiceImpl implements ScooterService {

    @DubboReference
    private ScooterMobileBService scooterMobileBService;

    @DubboReference
    private ScooterMobileCService scooterMobileCService;

    @Resource
    private UserComponent userComponent;

    @Override
    public BaseScooterResult getScooterInfo(GeneralEnter enter) {
        BaseScooterResult scooterResult = null;
        /**
         * 判断用户类型,根据用户类型进行业务分发(维修端忽略不处理)
         */
        Integer userServiceType = userComponent.getUserServiceTypeById(enter);
        if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
            scooterResult = scooterMobileBService.getScooterInfo(enter);
        } else if (UserServiceTypeEnum.C.getType().equals(userServiceType)) {
            scooterResult = scooterMobileCService.getScooterInfo(enter);
        }

        if (null == scooterResult) {
            throw new SesMobileClientException(ExceptionCodeEnums.SCOOTER_INFO_NOT_FIND.getCode(), ExceptionCodeEnums.SCOOTER_INFO_NOT_FIND.getMessage());
        }
        if (StringUtils.isBlank(scooterResult.getBluetoothMacAddress())) {
            throw new SesMobileClientException(ExceptionCodeEnums.BLUE_TOOTH_MAC_ADDRESS_IS_EMPTY.getCode(), ExceptionCodeEnums.BLUE_TOOTH_MAC_ADDRESS_IS_EMPTY.getMessage());
        }
        return scooterResult;
    }

    @Override
    public GeneralResult lock(ScooterLockDTO scooterLockDTO) {
        GeneralResult result = null;

        if (!CommonEvent.START.getValue().equals(scooterLockDTO.getEvent())
                && !CommonEvent.END.getValue().equals(scooterLockDTO.getEvent())) {
            throw new SesMobileClientException(ExceptionCodeEnums.EVENT_ERROR.getCode(), ExceptionCodeEnums.EVENT_ERROR.getMessage());
        }

        /**
         * 业务分发
         */
        Integer userServiceType = userComponent.getUserServiceTypeById(scooterLockDTO);
        if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
            result = scooterMobileBService.lock(scooterLockDTO);
        } else if (UserServiceTypeEnum.C.getType().equals(userServiceType)) {
            result = scooterMobileCService.lock(scooterLockDTO);
        }
        return result;
    }

    @Override
    public GeneralResult scooterNavigation(ScooterNavigationDTO scooterNavigation) {
        GeneralResult result = null;

        if (!CommonEvent.START.getValue().equals(scooterNavigation.getEvent())
                && !CommonEvent.END.getValue().equals(scooterNavigation.getEvent())) {
            throw new SesMobileClientException(ExceptionCodeEnums.EVENT_ERROR.getCode(), ExceptionCodeEnums.EVENT_ERROR.getMessage());
        }

        /**
         * 业务分发
         */
        Integer userServiceType = userComponent.getUserServiceTypeById(scooterNavigation);
        if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
            result = scooterMobileBService.scooterNavigation(scooterNavigation);
        } else if (UserServiceTypeEnum.C.getType().equals(userServiceType)) {
            result = scooterMobileCService.scooterNavigation(scooterNavigation);
        }
        return result;
    }

}
