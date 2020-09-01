package com.redescooter.ses.web.ros.controller.sys;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sys.StaffService;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffOpEnter;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffResult;
import com.redescooter.ses.web.ros.vo.sys.staff.StaffSaveOrEditEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameSysStaffController
 * @Description
 * @Author Aleks
 * @Date2020/8/31 19:49
 * @Version V1.0
 **/
@Api(tags = {"员工管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sys/staff")
public class SysStaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping(value = "/staffSave")
    @ApiOperation(value = "新增员工", response = GeneralResult.class)
    public Response<GeneralResult> staffSave(@ModelAttribute @ApiParam("请求参数") StaffSaveOrEditEnter enter) {
        return new Response(staffService.staffSave(enter));
    }


    @PostMapping(value = "/staffEdit")
    @ApiOperation(value = "修改员工", response = GeneralResult.class)
    public Response<GeneralResult> staffEdit(@ModelAttribute @ApiParam("请求参数") StaffSaveOrEditEnter enter) {
        return new Response(staffService.staffEdit(enter));
    }


    @PostMapping(value = "/staffDelete")
    @ApiOperation(value = "删除员工", response = GeneralResult.class)
    public Response<GeneralResult> staffDelete(@ModelAttribute @ApiParam("请求参数") StaffOpEnter enter) {
        return new Response(staffService.staffDelete(enter));
    }


    @PostMapping(value = "/staffDetail")
    @ApiOperation(value = "删除员工", response = GeneralResult.class)
    public Response<StaffResult> staffDetail(@ModelAttribute @ApiParam("请求参数") StaffOpEnter enter) {
        return new Response(staffService.staffDetail(enter));
    }
}
