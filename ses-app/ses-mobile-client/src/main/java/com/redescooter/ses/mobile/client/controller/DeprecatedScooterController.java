package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.mobile.b.service.ScooterMobileService;
import com.redescooter.ses.api.mobile.b.vo.LockEnter;
import com.redescooter.ses.api.mobile.c.service.IdScooterService;
import com.redescooter.ses.api.mobile.c.vo.ScooterNavigationEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * 该接口已废弃,改用 {@link com.redescooter.ses.mobile.client.controller.ScooterController}
 * @ClassName:ScooterController
 * @description: ScooterController
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 13:38
 */
@Slf4j
@Api(tags = {"车辆模块-deprecated"})
@CrossOrigin
@RestController
@RequestMapping(value = "/deprecated/scooter", method = RequestMethod.POST)
public class DeprecatedScooterController {

    @Reference
    private ScooterMobileService scooterMobileService;
    @Reference
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

    @ApiOperation(value = "车辆导航")
    @RequestMapping(value = "/scooterNavigation")
    public Response<GeneralResult> scooterNavigation(@ModelAttribute ScooterNavigationEnter enter) {
        return new Response<>(idScooterService.scooterNavigation(enter));
    }
}
