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
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * 车辆相关业务接口实现类
 * @author assert
 * @date 2020/11/18 15:29
 */
@Service
public class ScooterServiceImpl implements ScooterService {

    @Reference
    private ScooterMobileBService scooterMobileBService;
    @Reference
    private ScooterMobileCService scooterMobileCService;
    @Resource
    private UserComponent userComponent;


    @Override
    public BaseScooterResult getScooterInfo(GeneralEnter enter) {
        BaseScooterResult scooterResult = null;
        /**
         * 判断用户类型,根据用户类型进行业务分发
         */
        Integer userServiceType = userComponent.getUserServiceTypeById(enter);
        if (UserServiceTypeEnum.B.getType().equals(userServiceType)) {
            scooterResult = scooterMobileBService.getScooterInfo(enter);
        } else {
            scooterResult = scooterMobileCService.getScooterInfo(enter);
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
        } else {
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
        } else {
            result = scooterMobileCService.scooterNavigation(scooterNavigation);
        }

        return result;
    }

}
