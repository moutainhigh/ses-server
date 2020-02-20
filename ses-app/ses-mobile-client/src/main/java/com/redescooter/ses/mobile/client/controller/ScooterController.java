package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.mobile.b.service.ScooterMobileService;
import com.redescooter.ses.api.mobile.b.vo.LockEnter;
import com.redescooter.ses.api.mobile.c.service.IdScooterService;
import com.redescooter.ses.api.mobile.c.vo.ScooterNgvEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IdScooterService idScooterService;

    @ApiOperation(value = "车辆信息")
    @RequestMapping(value = "/scooterInfor")
    public Response<BaseScooterResult> scooterInfor(@ModelAttribute GeneralEnter enter) {
        return new Response<>(scooterMobileService.scooterInfor(enter));
    }

    @ApiOperation(value = "车辆开关锁")
    @RequestMapping(value = "/lock")
    public Response<GeneralResult> lock(@ModelAttribute LockEnter enter) {
        return new Response<>(scooterMobileService.lock(enter));
    }

    // 为C端 在开放两个 开始导航 、结束导航接口

    @ApiOperation(value = "车辆开始导航")
    @RequestMapping(value = "/startNgv")
    public Response<GeneralResult> lock(@ModelAttribute ScooterNgvEnter enter) {
        return new Response<>(idScooterService.scooterNgv(enter));
    }
}
