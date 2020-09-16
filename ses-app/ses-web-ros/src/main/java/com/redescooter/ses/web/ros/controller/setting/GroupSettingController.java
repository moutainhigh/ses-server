package com.redescooter.ses.web.ros.controller.setting;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.setting.GroupSettingService;
import com.redescooter.ses.web.ros.vo.setting.GroupListEnter;
import com.redescooter.ses.web.ros.vo.setting.GroupResult;
import com.redescooter.ses.web.ros.vo.setting.SaveGroupEnter;
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
@RequestMapping(value = "/setting/group")
public class GroupSettingController {

    @Autowired
    private GroupSettingService groupSettingService;

    @ApiOperation(value = "参数列表", response = GroupResult.class)
    @PostMapping(value = "/list")
    public Response<PageResult<GroupResult>> list(@ModelAttribute @ApiParam("请求参数") GroupListEnter enter) {
        return new Response<>(groupSettingService.list(enter));
    }

    @ApiOperation(value = "详情", response = GroupResult.class)
    @PostMapping(value = "/detail")
    public Response<GroupResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(groupSettingService.detail(enter));
    }

    @ApiOperation(value = "删除", response = GeneralResult.class)
    @PostMapping(value = "/delete")
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(groupSettingService.delete(enter));
    }

    @ApiOperation(value = "导出", response = GeneralResult.class)
    @PostMapping(value = "/export")
    public Response<GeneralResult> export(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(groupSettingService.export(enter));
    }

    @ApiOperation(value = "导入", response = GeneralResult.class)
    @PostMapping(value = "/importParament")
    public Response<GeneralResult> importParament(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(groupSettingService.importGroup(enter));
    }

    @ApiOperation(value = "保存", response = GeneralResult.class)
    @PostMapping(value = "/save")
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveGroupEnter enter) {
        return new Response<>(groupSettingService.save(enter));
    }
}
