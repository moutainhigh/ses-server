package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.admin.dev.service.scooter.AdminScooterService;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.InsertAdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.QueryAdminScooterDTO;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 车辆相关接口管理
 * @author assert
 * @date 2020/12/4 11:34
 */
@Api(tags = {"车辆相关接口管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/scooter", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScooterController {

    @Resource
    private AdminScooterService adminScooterService;

    /**
     * 查询车辆列表
     * @param adminScooterDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO>
     * @author assert
     * @date 2020/12/9
    */
    @ApiOperation(value = "查询车辆列表")
    @GetMapping(value = "/query")
    public Response<PageResult<AdminScooterDTO>> queryAdminScooter(@ModelAttribute QueryAdminScooterDTO adminScooterDTO) {
        return new Response<>(adminScooterService.queryAdminScooter(adminScooterDTO));
    }

    /**
     * 创建车辆信息
     * @param adminScooterDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<java.lang.Integer>
     * @author assert
     * @date 2020/12/8
    */
    @ApiOperation(value = "创建车辆信息")
    @PostMapping(value = "/insert")
    public Response<Integer> insertAdminScooter(@ModelAttribute InsertAdminScooterDTO adminScooterDTO) {
        return new Response<>(adminScooterService.insertAdminScooter(adminScooterDTO));
    }



}
