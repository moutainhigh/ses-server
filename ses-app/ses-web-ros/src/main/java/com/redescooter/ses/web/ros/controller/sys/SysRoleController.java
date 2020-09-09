package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.salearea.SaleAreaService;
import com.redescooter.ses.web.ros.service.sys.RoleService;
import com.redescooter.ses.web.ros.vo.roledata.RoleDataSaveEnter;
import com.redescooter.ses.web.ros.vo.salearea.RoleAreaEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleCityTreeResult;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionIdEnter;
import com.redescooter.ses.web.ros.vo.sys.role.*;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
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
@Api(tags = {"角色管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/role")
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private SaleAreaService saleAreaService;

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
    @ApiOperation(value = "角色菜单" +
            "权限展示--reseat", response = DeptAuthorityDetailsResult.class)
    public Response<DeptAuthorityDetailsResult> authDetailsById(@PathVariable(required = false) String type, @ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(roleService.roleAuthDetails(type, enter));
    }


    /**
     * @Author Aleks
     * @Description  以下为ROS组织架构重构后的代码
     * @Date  2020/9/1 10:26
     * @Param
     * @return
     **/


    @PostMapping(value = "/roleSave")
    @ApiOperation(value = "新增角色--reseat", response = GeneralResult.class)
    public Response<GeneralResult> roleSave(@ModelAttribute @ApiParam("请求参数") RoleSaveOrEditEnter enter) {
        return new Response(roleService.roleSave(enter));
    }


    @PostMapping(value = "/roleEdit")
    @ApiOperation(value = "修改角色--reseat", response = GeneralResult.class)
    public Response<GeneralResult> roleEdit(@ModelAttribute @ApiParam("请求参数") RoleSaveOrEditEnter enter) {
        return new Response(roleService.roleEdit(enter));
    }


    @PostMapping(value = "/roleDelete")
    @ApiOperation(value = "删除角色--reseat", response = GeneralResult.class)
    public Response<GeneralResult> roleDelete(@ModelAttribute @ApiParam("请求参数") RoleOpEnter enter) {
        return new Response(roleService.roleDelete(enter));
    }


    @PostMapping(value = "/roleDetail")
    @ApiOperation(value = "角色详情--reseat", response = GeneralResult.class)
    public Response<RoleDetailResult> roleDetail(@ModelAttribute @ApiParam("请求参数") RoleOpEnter enter) {
        return new Response(roleService.roleDetail(enter));
    }


    @PostMapping(value = "/roleList")
    @ApiOperation(value = "角色列表--reseat", response = GeneralResult.class)
    public Response<PageResult<RoleListResult>> roleList(@ModelAttribute @ApiParam("请求参数") RoleQueryListEnter enter) {
        return new Response(roleService.roleList(enter));
    }


    @PostMapping(value = "/roleMenuEdit")
    @ApiOperation(value = "角色菜单权限修改--reseat", response = GeneralResult.class)
    public Response<GeneralResult> roleMenu(@ModelAttribute @ApiParam("请求参数") RoleMenuEditEnter enter) {
        return new Response(roleService.roleMenuEdit(enter));
    }


//    @PostMapping(value = "/roleCity/{type}")
//    @ApiOperation(value = "角色区域展示--reseat", response = GeneralResult.class)
//    public Response<List<SalesAreaTressResult>> roleCity(@PathVariable(required = false) String type,@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
//        return new Response(roleService.roleSalesAreaById(type,enter));
//    }
//
//
//    @PostMapping(value = "/roleCityEdit")
//    @ApiOperation(value = "角色区域展示--reseat", response = GeneralResult.class)
//    public Response<GeneralResult> roleCityEdit(@ModelAttribute @ApiParam("请求参数") RoleCityEditEnter enter) {
//        return new Response(roleService.roleCityEdit(enter));
//    }


    @PostMapping(value = "/roleData")
    @ApiOperation(value = "角色的下拉数据接口--reseat", response = GeneralResult.class)
    public Response<List<RoleDataResult>> roleData(@ModelAttribute @ApiParam("请求参数") PositionIdEnter enter) {
        return new Response(roleService.roleData(enter));
    }


    @PostMapping(value = "/roleAreaAuthShow")
    @ApiOperation(value = "查看角色的销售区域--reseat", response = GeneralResult.class)
    public Response<List<SaleCityTreeResult>> roleAreaAuthShow(@ModelAttribute @ApiParam("请求参数") RoleOpEnter enter) {
        return new Response(saleAreaService.roleAreaAuthShow(enter));
    }


    @PostMapping(value = "/roleAreaAuth")
    @ApiOperation(value = "保存或角色的销售区域--reseat", response = GeneralResult.class)
    public Response<GeneralResult> roleAreaAuth(@ModelAttribute @ApiParam("请求参数") RoleAreaEnter enter) {
        return new Response(saleAreaService.roleAreaAuth(enter));
    }


    @PostMapping(value = "/roleDataShow")
    @ApiOperation(value = "查看角色的数据权限--reseat", response = GeneralResult.class)
    public Response<GeneralResult> roleDataShow(@ModelAttribute @ApiParam("请求参数") RoleAreaEnter enter) {
        return new Response(saleAreaService.roleAreaAuth(enter));
    }


//    @PostMapping(value = "/saveRoleData")
//    @ApiOperation(value = "保存角色的数据权限--reseat", response = GeneralResult.class)
//    public Response<GeneralResult> saveRoleData(@ModelAttribute @ApiParam("请求参数") RoleDataSaveEnter enter) {
//        return new Response(saleAreaService.roleAreaAuth(enter));
//    }

}
