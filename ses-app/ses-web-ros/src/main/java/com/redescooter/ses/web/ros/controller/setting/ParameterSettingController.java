package com.redescooter.ses.web.ros.controller.setting;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.setting.ParameterGroupResultList;
import com.redescooter.ses.api.foundation.vo.setting.ParameterResult;
import com.redescooter.ses.web.ros.service.setting.RosParameterService;
import com.redescooter.ses.web.ros.vo.setting.ImportParameterEnter;
import com.redescooter.ses.web.ros.vo.setting.RosParameterListEnter;
import com.redescooter.ses.web.ros.vo.setting.RosSaveParamentEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "/setup/setting/parameter")
public class ParameterSettingController {

    @Autowired
    private RosParameterService rosParameterService;

    @ApiOperation(value = "参数列表", response = ParameterResult.class)
    @PostMapping(value = "/list")
    public Response<PageResult<ParameterResult>> list(@ModelAttribute @ApiParam("请求参数") RosParameterListEnter enter) {
        return new Response<>(rosParameterService.list(enter));
    }

    @ApiOperation(value = "详情", response = ParameterResult.class)
    @PostMapping(value = "/detail")
    public Response<ParameterResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rosParameterService.detail(enter));
    }

    @ApiOperation(value = "删除", response = GeneralResult.class)
    @PostMapping(value = "/delete")
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rosParameterService.delete(enter));
    }

    @ApiOperation(value = "导出", response = GeneralResult.class)
    @GetMapping(value = "/export")
    public Response<GeneralResult> export(@ApiParam("请求参数") String ids, HttpServletResponse response) {
        return new Response<>(rosParameterService.export(ids, response));
    }

    @ApiOperation(value = "导入", response = GeneralResult.class)
    @PostMapping(value = "/importParament")
    public Response<GeneralResult> importParament(@ModelAttribute @ApiParam("请求参数") ImportParameterEnter enter) {
        return new Response<>(rosParameterService.importParament(enter));
    }

    @ApiOperation(value = "保存", response = GeneralResult.class)
    @PostMapping(value = "/save")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") RosSaveParamentEnter enter) {
        return new Response<>(rosParameterService.save(enter));
    }

    @ApiOperation(value = "模版下载", response = StringResult.class)
    @PostMapping(value = "/downloadExcel")
    public Response<StringResult> downloadExcel(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rosParameterService.downloadExcel(enter));
    }

    @ApiOperation(value = "分组列表", response = ParameterGroupResultList.class)
    @PostMapping(value = "/groupList")
    public Response<List<ParameterGroupResultList>> groupList(@ModelAttribute @ApiParam("请求参数") BooleanEnter enter) {
        return new Response<>(rosParameterService.groupList(enter));
    }
}
