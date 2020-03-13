package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sys.SysRoleService;
import com.redescooter.ses.web.ros.vo.sys.position.RoleDeptListResult;
import com.redescooter.ses.web.ros.vo.sys.position.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private SysRoleService roleService;

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

    @PostMapping(value = "/delete")
    @ApiOperation(value = "岗位删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "岗位列表", response = RoleDeptListResult.class)
    public Response<RoleDeptListResult> list(@ModelAttribute @ApiParam("请求参数") RoleListEnter enter) {
        return new Response<>(roleService.list(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "岗位详情", response = GeneralResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/tree")
    @ApiOperation(value = "更新岗位菜单", response = GeneralResult.class)
    public Response<GeneralResult> saveRoleMenus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

}
