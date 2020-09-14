package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.vo.sys.dept.*;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffDataResult;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeListResult;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private StaffService taffService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "部门创建", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveDeptEnter enter) {
        return new Response<>(deptService.save(enter));
    }

    @PostMapping(value = "/principal")
    @ApiOperation(value = "查询负责人--reseat", response = StaffDataResult.class)
    public Response<List<StaffDataResult>> principal(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(taffService.principalData(enter.getTenantId()));
    }

    @PostMapping(value = "/saveDept")
    @ApiOperation(value = "新建部门--reseat", response = GeneralResult.class)
    public Response<GeneralResult> saveDept(@ModelAttribute @ApiParam("请求参数") AddDeptEnter enter) {
        return new Response<>(deptService.addSave(enter));
    }

    @PostMapping(value = "/deptDetails")
    @ApiOperation(value = "部门详情--reseat", response = DeptDetailsResult.class)
    public Response<DeptDetailsResult> deptDetails(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(deptService.deptDetails(enter));
    }

    @PostMapping(value = "/deleteDeptSelect")
    @ApiOperation(value = "部门删除校验--reseat", response = BooleanResult.class)
    public Response<BooleanResult> deleteDeptSelect(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(deptService.deleteDeptSelect(enter));
    }

    @PostMapping(value = "/deleteDept")
    @ApiOperation(value = "部门删除--reseat", response = GeneralResult.class)
    public Response<GeneralResult> deleteDept(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(deptService.deleteDept(enter));
    }

    @PostMapping(value = "/selectDeptType")
    @ApiOperation(value = "查询部门类型--reseat", response = DeptTypeResult.class)
    public Response<List<DeptTypeResult>> selectDept(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(deptService.selectDeptType(enter));
    }

    @PostMapping(value = "/selectEditDept")
    @ApiOperation(value = "查询编辑部门--reseat", response = SelectDeptResult.class)
    public Response<SelectDeptResult> selectEditDept(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(deptService.selectEditDept(enter));
    }

    @PostMapping(value = "/editDept")
    @ApiOperation(value = "部门编辑--reseat", response = GeneralResult.class)
    public Response<GeneralResult> editDept(@ModelAttribute @ApiParam("请求参数") UpdateDeptEnter enter) {
        return new Response<>(deptService.editDept(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑部门", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") EditDeptEnter enter) {
        return new Response<>(deptService.edit(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "部门详情", response = GeneralResult.class)
    public Response<DeptTreeReslt> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(deptService.details(enter));
    }

    @PostMapping(value = "/tree")
    @ApiOperation(value = "部门树列表", response = GeneralResult.class)
    public Response<List<DeptTreeReslt>> tree(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(deptService.trees(enter));
    }

    @PostMapping(value = "/deptTree")
    @ApiOperation(value = "部门列表树--reseat", response = DeptTreeListResult.class)
    @IgnoreLoginCheck
    public Response<List<DeptTreeListResult>> deptTree(@ModelAttribute @ApiParam("请求参数") DeptListEnter enter) {
        return new Response<>(deptService.deptTrees(enter));
    }

    @PostMapping(value = "/deptList")
    @ApiOperation(value = "部门列表（平行结构）", response = GeneralResult.class)
    public Response<List<DeptTreeReslt>> deptList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(deptService.deptList(enter));
    }

    @PostMapping(value = "/employeeList")
    @ApiOperation(value = "员工列表", response = EmployeeProfileResult.class)
    public Response<List<EmployeeProfileResult>> employeeList(@ModelAttribute @ApiParam("请求参数") EmployeeListByDeptIdEnter enter) {
        return new Response<>(deptService.employeeListByDeptId(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "部门删除", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(deptService.delete(enter));
    }

    @PostMapping(value = "/getDescendants")
    @ApiOperation(value = "子级列表", response = GeneralResult.class)
    public Response<DeptTreeReslt> getDescendants(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(deptService.getDescendants(enter));
    }

    @PostMapping(value = "/topDepartment")
    @ApiOperation(value = "顶级部门", response = GeneralResult.class)
    public Response<DeptTreeReslt> TopDepartment(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(deptService.topDeptartment(enter, null));
    }

    @PostMapping(value = "/principals")
    @ApiOperation(value = "负责人列表", response = GeneralResult.class)
    public Response<List<PrincipalResult>> principals(@ModelAttribute @ApiParam("请求参数") PrincipalsEnter enter) {
        return new Response<>(deptService.principals(enter));
    }
}
