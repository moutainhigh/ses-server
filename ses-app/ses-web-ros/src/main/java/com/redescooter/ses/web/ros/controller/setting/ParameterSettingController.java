package com.redescooter.ses.web.ros.controller.setting;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.setting.ParameterSettingService;
import com.redescooter.ses.web.ros.vo.setting.ParameterListEnter;
import com.redescooter.ses.web.ros.vo.setting.ParameterResult;
import com.redescooter.ses.web.ros.vo.setting.SaveParamentEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameUserProfileController
 * @Description
 * @Author Joan
 * @Date2020/4/27 18:44
 * @Version V1.0
 **/

@Api(tags = {"ROS-Setting"})
@CrossOrigin
@RestController
@RequestMapping(value = "/setting/parameter")
public class ParameterSettingController {

    @Autowired
    private ParameterSettingService parameterSettingService;

    @ApiOperation(value = "参数列表", response = ParameterResult.class)
    @PostMapping(value = "/list")
    public Response<PageResult<ParameterResult>> list(@ModelAttribute @ApiParam("请求参数") ParameterListEnter enter) {
        return new Response<>(parameterSettingService.list(enter));
    }

    @ApiOperation(value = "详情", response = ParameterResult.class)
    @PostMapping(value = "/detail")
    public Response<ParameterResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(parameterSettingService.detail(enter));
    }

    @ApiOperation(value = "删除", response = GeneralResult.class)
    @PostMapping(value = "/delete")
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(parameterSettingService.delete(enter));
    }

    @ApiOperation(value = "导出", response = GeneralResult.class)
    @PostMapping(value = "/export")
    public Response<GeneralResult> export(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(parameterSettingService.export(enter));
    }

    @ApiOperation(value = "导入", response = GeneralResult.class)
    @PostMapping(value = "/importParament")
    public Response<GeneralResult> importParament(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(parameterSettingService.importParament(enter));
    }

    @ApiOperation(value = "保存", response = GeneralResult.class)
    @PostMapping(value = "/save")
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveParamentEnter enter) {
        return new Response<>(parameterSettingService.save(enter));
    }
}
