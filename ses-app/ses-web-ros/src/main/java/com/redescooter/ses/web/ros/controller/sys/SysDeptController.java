package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private SysDeptService deptService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "部门创建", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveDeptEnter enter) {
        return new Response<>(deptService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "部门编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SaveDeptEnter enter) {
        return new Response<>(deptService.edit(enter));
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
    @ApiOperation(value = "部门树列表", response = GeneralResult.class)
    public Response<List<DeptTreeReslt>> tree(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(deptService.trees(enter));
    }

    @PostMapping(value = "/getDescendants")
    @ApiOperation(value = "子级列表", response = GeneralResult.class)
    public Response<GeneralResult> getDescendants(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}
