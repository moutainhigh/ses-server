package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.mobile.b.service.ScooterMobileService;
import com.redescooter.ses.api.mobile.b.vo.LockEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:ScooterController
 * @description: ScooterController
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 13:38
 */
@Slf4j
@Api(tags = {"车辆模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/scooter", method = RequestMethod.POST)
public class ScooterController {

    @Reference
    private ScooterMobileService scooterMobileService;

    @IgnoreLoginCheck
    @ApiOperation(value = "车辆信息")
    @RequestMapping(value = "/scooterInfor")
    public Response<BaseScooterResult> scooterInfor(@ModelAttribute GeneralEnter enter) {
        enter.setUserId(1071493L);
        enter.setTenantId(0L);
        return new Response<>(scooterMobileService.scooterInfor(enter));
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "车辆开关锁")
    @RequestMapping(value = "/lock")
    public Response<GeneralResult> lock(@ModelAttribute LockEnter enter) {
        enter.setUserId(1071493L);
        enter.setTenantId(0L);
        return new Response<>(scooterMobileService.lock(enter));
    }
}
