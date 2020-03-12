package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysMenuController
 * @Author Jerry
 * @date 2020/03/10 15:17
 * @Description:
 */
@Api(tags = {"菜单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "菜单创建", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveMenuEnter enter) {
        return new Response<>(sysMenuService.save(enter));
    }

    @PostMapping(value = "/tree")
    @ApiOperation(value = "树形菜单", response = GeneralResult.class)
    public Response<List<MenuTreeResult>> trees(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(sysMenuService.trees(enter));
    }

    @PostMapping(value = "/userRouters")
    @ApiOperation(value = "vue路由", response = GeneralResult.class)
    public Response<List<VueRouter<MenuTreeResult>>> userRouters(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(sysMenuService.userRouters(enter));
    }

    @PostMapping(value = "/userTrees")
    @ApiOperation(value = "用户菜单", response = GeneralResult.class)
    public Response<GeneralResult> userTrees(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/roleTree")
    @ApiOperation(value = "角色菜单", response = GeneralResult.class)
    public Response<GeneralResult> roleTree(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "菜单详情", response = GeneralResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "菜单编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "菜单删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }
}
