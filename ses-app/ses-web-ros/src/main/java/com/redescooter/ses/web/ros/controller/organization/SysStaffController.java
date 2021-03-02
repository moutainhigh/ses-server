package com.redescooter.ses.web.ros.controller.organization;

import com.redescooter.ses.api.common.annotation.AvoidDuplicateSubmit;
import com.redescooter.ses.api.common.annotation.LogAnnotation;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.sys.staff.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameSysStaffController
 * @Description
 * @Author Aleks
 * @Date2020/8/31 19:49
 * @Version V1.0
 **/
@Api(tags = {"员工管理模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/organization/sys/staff")
public class SysStaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping(value = "/staffSave")
    @ApiOperation(value = "新增员工", response = GeneralResult.class)
    @LogAnnotation
    @AvoidDuplicateSubmit
    public Response<GeneralResult> staffSave(@ModelAttribute @ApiParam("请求参数") StaffSaveOrEditEnter enter) {
        return new Response(staffService.staffSave(enter));
    }


    @PostMapping(value = "/staffEdit")
    @ApiOperation(value = "修改员工", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> staffEdit(@ModelAttribute @ApiParam("请求参数") StaffSaveOrEditEnter enter) {
        return new Response(staffService.staffEdit(enter));
    }


    @PostMapping(value = "/staffDelete")
    @ApiOperation(value = "删除员工", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> staffDelete(@ModelAttribute @ApiParam("请求参数") StaffDeleteEnter enter) {
        return new Response(staffService.staffDelete(enter));
    }


    @PostMapping(value = "/staffDetail")
    @ApiOperation(value = "员工详情", response = GeneralResult.class)
    @LogAnnotation
    public Response<StaffResult> staffDetail(@ModelAttribute @ApiParam("请求参数") StaffOpEnter enter) {
        return new Response(staffService.staffDetail(enter));
    }


    @PostMapping(value = "/staffList")
    @ApiOperation(value = "员工列表", response = GeneralResult.class)
    @LogAnnotation
    public Response<PageResult<StaffListResult>> staffList(@ModelAttribute @ApiParam("请求参数") StaffListEnter enter) {
        return new Response(staffService.staffList(enter));
    }

    @PostMapping(value = "/openAccount")
    @ApiOperation(value = "员工开通账号", response = GeneralResult.class)
    @LogAnnotation
    public Response<GeneralResult> openAccount(@ModelAttribute @ApiParam("请求参数") StaffOpEnter enter) {
        return new Response(staffService.openAccount(enter));
    }



    // 以下为ros-1.7.1的

    @PostMapping(value = "/checkLoginPsd")
    @ApiOperation(value = "校验登陆密码-1.7.1", response = GeneralResult.class)
    public Response<Boolean> checkLoginPsd(@ModelAttribute @ApiParam("请求参数") UserPsdEnter enter) {
        return new Response(staffService.checkLoginPsd(enter));
    }


    @PostMapping(value = "/editSafeCode")
    @ApiOperation(value = "修改安全码-1.7.1", response = GeneralResult.class)
    public Response<GeneralResult> editSafeCode(@ModelAttribute @ApiParam("请求参数") UserPsdEnter enter) {
        return new Response(staffService.editSafeCode(enter));
    }


    @PostMapping(value = "/staffSaleArea")
    @ApiOperation(value = "员工销售区域查看-1.7.1", response = GeneralResult.class)
    public Response<List<StaffSaleAreaResult>> staffSaleArea(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(staffService.staffSaleArea(enter));
    }


    @PostMapping(value = "/editUserPsd")
    @ApiOperation(value = "修改密码-1.7.1", response = GeneralResult.class)
    public Response<GeneralResult> editUserPsd(@ModelAttribute @ApiParam("请求参数") WebResetPasswordEnter enter) {
        return new Response(staffService.editUserPsd(enter));
    }


    @PostMapping(value = "/firstLoginEditPsd")
    @ApiOperation(value = "首次登陆修改密码-1.7.1", response = GeneralResult.class)
    public Response<GeneralResult> firstLoginEditPsd(@ModelAttribute @ApiParam("请求参数") UserPsdEnter enter) {
        return new Response(staffService.firstLoginEditPsd(enter));
    }

    @PostMapping(value = "/getSafeCode")
    @ApiOperation(value = "获取员工的安全码-1.7.1", response = GeneralResult.class)
    public Response<SafeCodeResult> getSafeCode(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(staffService.getSafeCode(enter));
    }


    @PostMapping(value = "/userMsgDetail")
    @ApiOperation(value = "个人信息详情-1.7.1", response = GeneralResult.class)
    public Response<StaffResult> userMsgDetail(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response(staffService.userMsgDetail(enter));
    }

    @PostMapping(value = "/userMsgEdit")
    @ApiOperation(value = "个人信息修改-1.7.1", response = GeneralResult.class)
    public Response<GeneralResult> userMsgEdit(@ModelAttribute @ApiParam("请求参数") UserMsgEditEnter enter) {
        return new Response(staffService.userMsgEdit(enter));
    }

}
