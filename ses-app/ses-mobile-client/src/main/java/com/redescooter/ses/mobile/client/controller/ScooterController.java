package com.redescooter.ses.mobile.client.controller;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.mobile.client.service.ScooterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 车辆相关接口管理
 * @author assert
 * @date 2020/11/18 15:34
 */
@CrossOrigin
@Api(tags = {"车辆模块"})
@RestController
@RequestMapping(value = "/scooter", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScooterController {

    @Resource
    private ScooterService scooterService;


    /**
     * 查询车辆信息
     * @param: enter
     * @return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.scooter.BaseScooterResult>
     * @author: assert
     * @date: 2020/11/17
     */
    @ApiOperation(value = "车辆信息")
    @PostMapping(value = "/info")
    public Response<BaseScooterResult> scooterInfo(@ModelAttribute GeneralEnter enter) {
        return new Response<>(scooterService.getScooterInfo(enter));
    }

    /**
     * 车辆开关锁
     * @param: lock
     * @return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author: assert
     * @date: 2020/11/17
     */
    @ApiOperation(value = "车辆开关锁")
    @PostMapping(value = "/lock")
    public Response<GeneralResult> lock(@ModelAttribute ScooterLockDTO lock) {
        return new Response<>(scooterService.lock(lock));
    }

    /**
     * 车辆开关导航
     * @param: enter
     * @return: com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author: assert
     * @date: 2020/11/17
     */
    @ApiOperation(value = "车辆开关导航")
    @PostMapping(value = "/navigation")
    public Response<GeneralResult> scooterNavigation(@ModelAttribute ScooterNavigationDTO enter) {
        return new Response<>(scooterService.scooterNavigation(enter));
    }

}
