package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.web.ros.service.sys.MenuService;
import com.redescooter.ses.web.ros.vo.sys.menu.EditMenuEnter;
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
    private MenuService menuService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "菜单创建", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveMenuEnter enter) {
        return new Response<>(menuService.save(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "菜单详情", response = MenuTreeResult.class)
    public Response<MenuTreeResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(menuService.details(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "菜单编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") EditMenuEnter enter) {
        return new Response<>(menuService.edit(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "菜单删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(menuService.delete(enter));
    }

    @PostMapping(value = "/tree")
    @ApiOperation(value = "菜单树", response = MenuTreeResult.class)
    public Response<List<MenuTreeResult>> trees(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(menuService.trees(enter));
    }

    @PostMapping(value = "/parallel")
    @ApiOperation(value = "平行菜单", response = MenuTreeResult.class)
    public Response<List<MenuTreeResult>> parallel(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(menuService.parallel(enter));
    }

    @PostMapping(value = "/vueRouters")
    @ApiOperation(value = "VUE路由创建", response = VueRouter.class)
    public Response<List<VueRouter<MenuTreeResult>>> vueRouters(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(menuService.vueRouters(enter));
    }

    @PostMapping(value = "/findMenuByRoleId")
    @ApiOperation(value = "获取权限", response = MenuTreeResult.class)
    public Response<List<MenuTreeResult>> findMenuByRoleId(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
  return new Response<>(menuService.findMenuByRoleId(enter));
    }
}
