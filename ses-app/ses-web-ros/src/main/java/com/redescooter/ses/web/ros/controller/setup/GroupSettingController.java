package com.redescooter.ses.web.ros.controller.setup;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.api.foundation.vo.setting.GroupResult;
import com.redescooter.ses.web.ros.service.setting.RosGroupService;
import com.redescooter.ses.web.ros.vo.setting.RosGroupListEnter;
import com.redescooter.ses.web.ros.vo.setting.RosSaveGroupEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassNameUserProfileController
 * @Description
 * @Author Joan
 * @Date2020/4/27 18:44
 * @Version V1.0
 **/
@Api(tags = {"ROS-Setting分组"})
@CrossOrigin
@RestController
@RequestMapping(value = "/setup/setting/group")
public class GroupSettingController {

    @Autowired
    private RosGroupService rosGroupService;

    @ApiOperation(value = "分组列表", response = GroupResult.class)
    @PostMapping(value = "/list")
    public Response<PageResult<GroupResult>> list(@ModelAttribute @ApiParam("请求参数") RosGroupListEnter enter) {
        return new Response<>(rosGroupService.list(enter));
    }

    @ApiOperation(value = "详情", response = GroupResult.class)
    @PostMapping(value = "/detail")
    public Response<GroupResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rosGroupService.detail(enter));
    }

    @ApiOperation(value = "删除", response = GeneralResult.class)
    @PostMapping(value = "/delete")
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rosGroupService.delete(enter));
    }

    @ApiOperation(value = "导出", response = GeneralResult.class)
    @GetMapping(value = "/export")
    public Response<GeneralResult> export(@ApiParam("请求参数 id") String id, HttpServletResponse response) {
        return new Response<>(rosGroupService.export(id, response));
    }

    @ApiOperation(value = "保存", response = GeneralResult.class)
    @PostMapping(value = "/save")
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") RosSaveGroupEnter enter) {
        return new Response<>(rosGroupService.save(enter));
    }
}
