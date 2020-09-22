package com.redescooter.ses.web.ros.controller.setting;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.web.ros.service.setting.ParameterService;
import com.redescooter.ses.web.ros.vo.setting.RosParameterListEnter;
import com.redescooter.ses.web.ros.vo.setting.RosSaveParamentEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameUserProfileController
 * @Description
 * @Author Joan
 * @Date2020/4/27 18:44
 * @Version V1.0
 **/

@Api(tags = {"ROS-Setting参数"})
@CrossOrigin
@RestController
@RequestMapping(value = "/setting/parameter")
public class ParameterSettingController {

    @Autowired
    private ParameterService parameterService;

    @ApiOperation(value = "参数列表", response = ParameterResult.class)
    @PostMapping(value = "/list")
    public Response<PageResult<ParameterResult>> list(@ModelAttribute @ApiParam("请求参数") RosParameterListEnter enter) {
        return new Response<>(parameterService.list(enter));
    }

    @ApiOperation(value = "详情", response = ParameterResult.class)
    @PostMapping(value = "/detail")
    public Response<ParameterResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(parameterService.detail(enter));
    }

    @ApiOperation(value = "删除", response = GeneralResult.class)
    @PostMapping(value = "/delete")
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(parameterService.delete(enter));
    }

    @ApiOperation(value = "导出", response = GeneralResult.class)
    @PostMapping(value = "/export")
    public Response<GeneralResult> export(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(parameterService.export(enter));
    }

    @ApiOperation(value = "导入", response = GeneralResult.class)
    @PostMapping(value = "/importParament")
    public Response<GeneralResult> importParament(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(parameterService.importParament(enter));
    }

    @ApiOperation(value = "保存", response = GeneralResult.class)
    @PostMapping(value = "/save")
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") RosSaveParamentEnter enter) {
        return new Response<>(parameterService.save(enter));
    }

    @ApiOperation(value = "模版下载", response = StringResult.class)
    @PostMapping(value = "/downloadExcel")
    public Response<StringResult> downloadExcel(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(parameterService.downloadExcel(enter));
    }

    @ApiOperation(value = "分组列表", response = ParameterGroupResultList.class)
    @PostMapping(value = "/groupList")
    public Response<List<ParameterGroupResultList>> groupList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(parameterService.groupList(enter));
    }
}
