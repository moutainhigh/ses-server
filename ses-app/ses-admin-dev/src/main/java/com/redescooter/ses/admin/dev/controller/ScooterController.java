package com.redescooter.ses.admin.dev.controller;

import com.redescooter.ses.admin.dev.service.scooter.AdminScooterService;
import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.InsertAdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.QueryAdminScooterParamDTO;
import com.redescooter.ses.admin.dev.vo.scooter.SetScooterModelParamDTO;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.hub.service.operation.ColorService;
import com.redescooter.ses.api.hub.service.operation.SpecificService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @Reference
    private ColorService colorService;
    @Reference
    private SpecificService specificService;


    /**
     * 查询车辆列表
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.PageResult<com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO>
     * @author assert
     * @date 2020/12/9
    */
    @ApiOperation(value = "查询车辆列表")
    @GetMapping(value = "/query")
    public Response<PageResult<AdminScooterDTO>> queryAdminScooter(@ModelAttribute QueryAdminScooterParamDTO paramDTO) {
        return new Response<>(adminScooterService.queryAdminScooter(paramDTO));
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

    /**
     * 查询颜色信息列表(下拉列表使用)
     * @param
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO>
     * @author assert
     * @date 2020/12/9
    */
    @ApiOperation(value = "查询颜色信息列表", notes = "查询颜色信息列表(下拉列表使用)")
    @GetMapping(value = "/colors")
    public Response<List<SelectBaseResultDTO>> getScooterColorList() {
        return new Response<>(colorService.getScooterColorList());
    }

    /**
     * 查询车辆型号分组列表(下拉列表使用)
     * @param
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO>
     * @author assert
     * @date 2020/12/9
    */
    @ApiOperation(value = "查询车辆型号分组列表", notes = "查询车辆型号分组列表(下拉列表使用)")
    @GetMapping(value = "/groups")
    public Response<List<SelectBaseResultDTO>> getScooterGroupList() {
        return new Response<>(specificService.getSpecificGroupList());
    }

    /**
     * 查询车辆型号列表(下拉框列表使用)
     * @param
     * @return com.redescooter.ses.api.common.vo.base.Response<java.util.List<com.redescooter.ses.api.common.vo.base.SelectBaseResultDTO>>
     * @author assert
     * @date 2020/12/15
    */
    @ApiOperation(value = "查询车辆型号列表", notes = "查询车辆型号列表(下拉框列表使用)")
    @GetMapping(value = "/models")
    public Response<List<SelectBaseResultDTO>> getScooterModelList() {
        return new Response<>(specificService.getScooterModelList());
    }

    /**
     * 根据id查询车辆详情
     * @param id
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO>
     * @author assert
     * @date 2020/12/10
    */
    @ApiOperation(value = "查询车辆详情", notes = "根据id查询车辆详情")
    @GetMapping(value = "/detail/{id}")
    public Response<AdminScooterDTO> getAdminScooterDetailById(@PathVariable("id") Long id) {
        return new Response<>(adminScooterService.getAdminScooterDetailById(id));
    }

    /**
     * 设置车辆软体模式(车辆型号)
     * @param paramDTO
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2020/12/14
    */
    @ApiOperation(value = "设置车辆软体模式", notes = "设置车辆软体模式(车辆型号E50、E100等..)")
    @PostMapping(value = "/setScooterModel")
    public Response<GeneralResult> setScooterModel(@ModelAttribute SetScooterModelParamDTO paramDTO) {
        return new Response<>(adminScooterService.setScooterModel(paramDTO));
    }

    /**
     * 重置车辆软体(将车辆重置成E50)
     * @param enter
     * @return com.redescooter.ses.api.common.vo.base.Response<com.redescooter.ses.api.common.vo.base.GeneralResult>
     * @author assert
     * @date 2020/12/15
    */
    @ApiOperation(value = "重置车辆软体", notes = "重置车辆软体(将车辆重置成E50)")
    @PostMapping(value = "/resetScooterModel")
    public Response<GeneralResult> resetScooterModel(@ModelAttribute IdEnter enter) {
        return new Response<>(adminScooterService.resetScooterModel(enter));
    }

}
