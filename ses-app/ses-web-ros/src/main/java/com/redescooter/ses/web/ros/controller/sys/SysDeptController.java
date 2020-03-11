package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SysDeptController
 * @Author Jerry
 * @date 2020/03/10 14:59
 * @Description:
 */
@Api(tags = {"部门管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/dept")
public class SysDeptController {

    @PostMapping(value = "/save")
    @ApiOperation(value = "部门创建", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "部门列表", response = GeneralResult.class)
    public Response<PageResult<GeneralResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "部门编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "部门详情", response = GeneralResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "部门删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/tree")
    @ApiOperation(value = "树形菜单", response = GeneralResult.class)
    public Response<GeneralResult> tree(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/getDescendants")
    @ApiOperation(value = "子级列表", response = GeneralResult.class)
    public Response<GeneralResult> getDescendants(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}