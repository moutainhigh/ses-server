package com.redescooter.ses.web.ros.controller.organization;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.roledata.RoleDataService;
import com.redescooter.ses.web.ros.service.salearea.SaleAreaService;
import com.redescooter.ses.web.ros.service.sys.RoleService;
import com.redescooter.ses.web.ros.vo.roledata.RoleDataSaveEnter;
import com.redescooter.ses.web.ros.vo.roledata.RoleDataShowResult;
import com.redescooter.ses.web.ros.vo.salearea.RoleAreaEnter;
import com.redescooter.ses.web.ros.vo.salearea.SaleCityTreeResult;
import com.redescooter.ses.web.ros.vo.sys.dept.DeptAuthorityDetailsResult;
import com.redescooter.ses.web.ros.vo.sys.position.PositionIdEnter;
import com.redescooter.ses.web.ros.vo.sys.role.DeptRoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleDataResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleDetailResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleListResult;
import com.redescooter.ses.web.ros.vo.sys.role.RoleMenuEditEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleOpEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleQueryListEnter;
import com.redescooter.ses.web.ros.vo.sys.role.RoleSaveOrEditEnter;
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
 * @ClassName SysRoleController
 * @Author Jerry
 * @date 2020/03/10 15:29
 * @Description:
 */
@Api(tags = {"????????????"})
@CrossOrigin
@RestController
@RequestMapping(value = "/organization/sys/role")
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private SaleAreaService saleAreaService;

    @Autowired
    private RoleDataService roleDataService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    @AvoidDuplicateSubmit
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("????????????") RoleEnter enter) {
        return new Response<>(roleService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("????????????") RoleEnter enter) {
        return new Response<>(roleService.edit(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "????????????", response = DeptRoleListResult.class)
    public Response<List<DeptRoleListResult>> list(@ModelAttribute @ApiParam("????????????") RoleListEnter enter) {
        return new Response<>(roleService.list(enter));
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "????????????", response = GeneralResult.class)
    public Response<GeneralResult> delete(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(roleService.delete(enter));
    }

    @PostMapping(value = "/authDetailsById/tree")
    @ApiOperation(value = "????????????" + "????????????--reseat", response = DeptAuthorityDetailsResult.class)
    public Response<DeptAuthorityDetailsResult> authDetailsById(@ModelAttribute @ApiParam("????????????") IdEnter enter) {
        return new Response<>(roleService.roleAuthDetails("tree", enter));
    }

    /**
     * @return
     * @Author Aleks
     * @Description ?????????ROS??????????????????????????????
     * @Date 2020/9/1 10:26
     * @Param
     **/


    @PostMapping(value = "/roleSave")
    @ApiOperation(value = "????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    @AvoidDuplicateSubmit
    public Response<GeneralResult> roleSave(@ModelAttribute @ApiParam("????????????") RoleSaveOrEditEnter enter) {
        return new Response(roleService.roleSave(enter));
    }

    @PostMapping(value = "/roleEdit")
    @ApiOperation(value = "????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> roleEdit(@ModelAttribute @ApiParam("????????????") RoleSaveOrEditEnter enter) {
        return new Response(roleService.roleEdit(enter));
    }

    @PostMapping(value = "/roleDelete")
    @ApiOperation(value = "????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> roleDelete(@ModelAttribute @ApiParam("????????????") RoleOpEnter enter) {
        return new Response(roleService.roleDelete(enter));
    }

    @PostMapping(value = "/roleDetail")
    @ApiOperation(value = "????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    public Response<RoleDetailResult> roleDetail(@ModelAttribute @ApiParam("????????????") RoleOpEnter enter) {
        return new Response(roleService.roleDetail(enter));
    }

    @PostMapping(value = "/roleList")
    @ApiOperation(value = "????????????--reseat", response = RoleListResult.class)
    @LogAnnotation
    public Response<PageResult<RoleListResult>> roleList(@ModelAttribute @ApiParam("????????????") RoleQueryListEnter enter) {
        return new Response(roleService.roleList(enter));
    }

    @PostMapping(value = "/roleMenuEdit")
    @ApiOperation(value = "????????????????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> roleMenu(@ModelAttribute @ApiParam("????????????") RoleMenuEditEnter enter) {
        return new Response(roleService.roleMenuEdit(enter));
    }


//    @PostMapping(value = "/roleCity/{type}")
//    @ApiOperation(value = "??????????????????--reseat", response = GeneralResult.class)
//    public Response<List<SalesAreaTressResult>> roleCity(@PathVariable(required = false) String type,@ModelAttribute @ApiParam("????????????") IdEnter enter) {
//        return new Response(roleService.roleSalesAreaById(type,enter));
//    }
//
//
//    @PostMapping(value = "/roleCityEdit")
//    @ApiOperation(value = "??????????????????--reseat", response = GeneralResult.class)
//    public Response<GeneralResult> roleCityEdit(@ModelAttribute @ApiParam("????????????") RoleCityEditEnter enter) {
//        return new Response(roleService.roleCityEdit(enter));
//    }

    @PostMapping(value = "/roleData")
    @ApiOperation(value = "???????????????????????????--reseat", response = GeneralResult.class)
    public Response<List<RoleDataResult>> roleData(@ModelAttribute @ApiParam("????????????") PositionIdEnter enter) {
        return new Response(roleService.roleData(enter));
    }

    @PostMapping(value = "/roleAreaAuthShow")
    @ApiOperation(value = "???????????????????????????--reseat", response = GeneralResult.class)
    public Response<List<SaleCityTreeResult>> roleAreaAuthShow(@ModelAttribute @ApiParam("????????????") RoleOpEnter enter) {
        return new Response(saleAreaService.roleAreaAuthShow(enter));
    }

    @PostMapping(value = "/roleAreaAuth")
    @ApiOperation(value = "??????????????????????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> roleAreaAuth(@ModelAttribute @ApiParam("????????????") RoleAreaEnter enter) {
        return new Response(saleAreaService.roleAreaAuth(enter));
    }

    @PostMapping(value = "/roleDataShow")
    @ApiOperation(value = "???????????????????????????--reseat", response = GeneralResult.class)
    public Response<RoleDataShowResult> roleDataShow(@ModelAttribute @ApiParam("????????????") RoleOpEnter enter) {
        return new Response(roleDataService.roleDataShow(enter));
    }

    @PostMapping(value = "/saveRoleData")
    @ApiOperation(value = "???????????????????????????--reseat", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> saveRoleData(@ModelAttribute @ApiParam("????????????") RoleDataSaveEnter enter) {
        return new Response(roleDataService.saveRoleData(enter));
    }

}
