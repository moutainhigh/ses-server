package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sys.RoleService;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.role.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysRoleController
 * @Author Jerry
 * @date 2020/03/10 15:29
 * @Description:
 */
@Api(tags = {"岗位管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/role")
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "岗位创建", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") RoleEnter enter) {
        return new Response<>(roleService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "岗位编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") RoleEnter enter) {
        return new Response<>(roleService.edit(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "岗位列表", response = DeptRoleListResult.class)
    public Response<List<DeptRoleListResult>> list(@ModelAttribute @ApiParam("请求参数") RoleListEnter enter) {
        return new Response<>(roleService.list(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "岗位删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(roleService.delete(enter));
    }

    @PostMapping(value = "/authDetailsById/{type}")
    @ApiOperation(value = "权限展示", response = DeptAuthorityDetailsResult.class)
    public Response<DeptAuthorityDetailsResult> authDetailsById(@PathVariable(required = false) String type, @ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(roleService.roleAuthDetails(type, enter));
    }


    // 以下为ROS组织架构重构后的代码
    @PostMapping(value = "/roleSave")
    @ApiOperation(value = "新增角色", response = GeneralResult.class)
    public Response<GeneralResult> roleSave(@ModelAttribute @ApiParam("请求参数") RoleSaveOrEditEnter enter) {
        return new Response(roleService.roleSave(enter));
    }


    @PostMapping(value = "/roleEdit")
    @ApiOperation(value = "修改角色", response = GeneralResult.class)
    public Response<GeneralResult> roleEdit(@ModelAttribute @ApiParam("请求参数") RoleSaveOrEditEnter enter) {
        return new Response(roleService.roleEdit(enter));
    }


    @PostMapping(value = "/roleDelete")
    @ApiOperation(value = "删除角色", response = GeneralResult.class)
    public Response<GeneralResult> roleDelete(@ModelAttribute @ApiParam("请求参数") RoleOpEnter enter) {
        return new Response(roleService.roleDelete(enter));
    }


    @PostMapping(value = "/roleDetail")
    @ApiOperation(value = "角色详情", response = GeneralResult.class)
    public Response<RoleDetailResult> roleDetail(@ModelAttribute @ApiParam("请求参数") RoleOpEnter enter) {
        return new Response(roleService.roleDetail(enter));
    }


    @PostMapping(value = "/roleList")
    @ApiOperation(value = "角色列表", response = GeneralResult.class)
    public Response<PageResult<RoleListResult>> roleList(@ModelAttribute @ApiParam("请求参数") RoleQueryListEnter enter) {
        return new Response(roleService.roleList(enter));
    }


}
