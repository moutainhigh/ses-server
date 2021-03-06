package com.redescooter.ses.web.ros.controller.organization;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.vo.sys.dept.AddDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptListEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptTypeResult;
import com.redescooter.ses.web.ros.vo.sys.dept.EditDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.EmployeeListByDeptIdEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.EmployeeProfileResult;
import com.redescooter.ses.web.ros.vo.sys.dept.PrincipalResult;
import com.redescooter.ses.web.ros.vo.sys.dept.PrincipalsEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.SelectDeptResult;
import com.redescooter.ses.web.ros.vo.sys.dept.TypeListEnter;
import com.redescooter.ses.web.ros.vo.sys.dept.UpdateDeptEnter;
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
@Api(tags = {"????????????"})
@CrossOrigin
@RestController
@RequestMapping(value = "/organization/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private StaffService taffService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("????????????") SaveDeptEnter enter) {
        return new Response<>(deptService.save(enter));
    }

    @PostMapping(value = "/principal")
    @ApiOperation(value = "???????????????--reseat", response = StaffDataResult.class)
    public Response<List<StaffDataResult>> principal(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(taffService.principalData(enter.getTenantId()));
    }

    @PostMapping(value = "/saveDept")
    @ApiOperation(value = "????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    @AvoidDuplicateSubmit
    public Response<GeneralResult> saveDept(@ModelAttribute @ApiParam("????????????") AddDeptEnter enter) {
        return new Response<>(deptService.addSave(enter));
    }

    @PostMapping(value = "/saveDeptSelectParent")
    @ApiOperation(value = "???????????????????????????????????????--reseat", response = GeneralResult.class)
    public Response<List<DeptTreeListResult>> saveDeptSelectParent(@ModelAttribute @ApiParam("????????????") DeptListEnter enter) {
        return new Response<>(deptService.saveDeptSelectParent(enter));
    }

    @PostMapping(value = "/deptDetails")
    @ApiOperation(value = "????????????--reseat", response = DeptDetailsResult.class)
    @LogAnnotation
    public Response<DeptDetailsResult> deptDetails(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(deptService.deptDetails(enter));
    }

    @PostMapping(value = "/deleteDeptSelect")
    @ApiOperation(value = "??????????????????--reseat", response = BooleanResult.class)
    public Response<BooleanResult> deleteDeptSelect(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(deptService.deleteDeptSelect(enter));
    }

    @PostMapping(value = "/deleteDept")
    @ApiOperation(value = "????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> deleteDept(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(deptService.deleteDept(enter));
    }

    @PostMapping(value = "/selectDeptType")
    @ApiOperation(value = "??????????????????--reseat", response = DeptTypeResult.class)
    public Response<List<DeptTypeResult>> selectDept(@ModelAttribute @ApiParam("????????????") TypeListEnter enter) {
        return new Response<>(deptService.selectDeptType(enter));
    }

    @PostMapping(value = "/selectEditDept")
    @ApiOperation(value = "??????????????????--reseat", response = SelectDeptResult.class)
    public Response<SelectDeptResult> selectEditDept(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(deptService.selectEditDept(enter));
    }

    @PostMapping(value = "/editDept")
    @ApiOperation(value = "????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> editDept(@ModelAttribute @ApiParam("????????????") UpdateDeptEnter enter) {
        return new Response<>(deptService.editDept(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("????????????") EditDeptEnter enter) {
        return new Response<>(deptService.edit(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    public Response<DeptTreeReslt> details(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(deptService.details(enter));
    }

    @PostMapping(value = "/tree")
    @ApiOperation(value = "???????????????", response = GeneralResult.class)
    public Response<List<DeptTreeReslt>> tree(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(deptService.trees(enter));
    }

    @PostMapping(value = "/deptTree")
    @ApiOperation(value = "???????????????--reseat", response = DeptTreeListResult.class)
    @LogAnnotation
    public Response<List<DeptTreeListResult>> deptTree(@ModelAttribute @ApiParam("????????????") DeptListEnter enter) {
        return new Response<>(deptService.deptTrees(enter));
    }

    @PostMapping(value = "/deptList")
    @ApiOperation(value = "??????????????????????????????", response = GeneralResult.class)
    public Response<List<DeptTreeReslt>> deptList(@ModelAttribute @ApiParam("????????????") GeneralEnter enter) {
        return new Response<>(deptService.deptList(enter));
    }

    @PostMapping(value = "/employeeList")
    @ApiOperation(value = "????????????", response = EmployeeProfileResult.class)
    public Response<List<EmployeeProfileResult>> employeeList(@ModelAttribute @ApiParam("????????????") EmployeeListByDeptIdEnter enter) {
        return new Response<>(deptService.employeeListByDeptId(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(deptService.delete(enter));
    }

    @PostMapping(value = "/getDescendants")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    public Response<DeptTreeReslt> getDescendants(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(deptService.getDescendants(enter));
    }

    @PostMapping(value = "/topDepartment")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    public Response<DeptTreeReslt> TopDepartment(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(deptService.topDeptartment(enter, null));
    }

    @PostMapping(value = "/principals")
    @ApiOperation(value = "???????????????", response = GeneralResult.class)
    public Response<List<PrincipalResult>> principals(@ModelAttribute @ApiParam("????????????") PrincipalsEnter enter) {
        return new Response<>(deptService.principals(enter));
    }
}
