package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.service.sys.SysPositionService;
import com.redescooter.ses.web.ros.vo.sys.dept.AddDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptTypeResult;
import com.redescooter.ses.web.ros.vo.sys.dept.UpdateDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.position.*;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameSysPositionController
 * @Description
 * @Author Joan
 * @Date2020/9/2 17:20
 * @Version V1.0
 **/
@Api(tags = {"岗位模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/position")
public class SysPositionController{
    @Autowired
    private SysPositionService sysPositionService;

    @Autowired
    private SysDeptService deptService;
    @PostMapping(value = "/selectPositionType")
    @ApiOperation(value = "查询岗位类型", response = PositionTypeResult.class)
    public Response<List<PositionTypeResult>> selectPosition(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(sysPositionService.selectPositionType(enter));
    }
    @PostMapping(value = "/list")
    @ApiOperation(value = "岗位列表", response = PositionResult.class)
    public Response<PageResult<PositionResult>> list(@ModelAttribute @ApiParam("请求参数") PositionEnter enter) {
        return new Response<>(sysPositionService.list(enter));
    }

    @PostMapping(value = "/selectDeptType")
    @ApiOperation(value = "查询部门类型", response = DeptTypeResult.class)
    public Response<List<DeptTypeResult>> selectDept(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(deptService.selectDeptType(enter));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建岗位", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SavePositionEnter enter) {
        return new Response<>(sysPositionService.save(enter));
    }
    @PostMapping(value = "/editPosition")
    @ApiOperation(value = "岗位编辑", response = GeneralResult.class)
    public Response<GeneralResult> editDept(@ModelAttribute @ApiParam("请求参数") UpdateDeptEnter enter) {
        return new Response<>(sysPositionService.positionEdit(enter));
    }
    @PostMapping(value = "/positionDetails")
    @ApiOperation(value = "岗位详情", response = DeptDetailsResult.class)
    public Response<PositionDetailsResult> deptDetails(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(sysPositionService.positionDetails(enter));
    }

    @PostMapping(value = "/deletePositionSelect")
    @ApiOperation(value = "岗位删除校验", response = BooleanResult.class)
    public Response<BooleanResult> deletePositionSelect(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(sysPositionService.deletePositionSelect(enter));
    }

    @PostMapping(value = "/deletePosition")
    @ApiOperation(value = "岗位删除", response = GeneralResult.class)
    public Response<GeneralResult> deletePosition(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(sysPositionService.deletePosition(enter));
    }
}

