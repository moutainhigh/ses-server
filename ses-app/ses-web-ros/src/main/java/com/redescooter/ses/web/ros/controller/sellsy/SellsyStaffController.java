package com.redescooter.ses.web.ros.controller.sellsy;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.sellsy.SellsyStaffService;
import com.redescooter.ses.web.ros.vo.sellsy.result.staff.SellsyStaffGroupResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.staff.SellsyStaffResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"ROS-Sellsy-Staff"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sellsy/staff")
public class SellsyStaffController {

    @Autowired
    private SellsyStaffService sellsyStaffService;

    @IgnoreLoginCheck
    @ApiOperation(value = "员工列表", response = SellsyStaffResult.class)
    @PostMapping(value = "/queryStaffList")
    public Response<List<SellsyStaffResult>> queryStaffList() {
        return new Response<>(sellsyStaffService.queryStaffList());
    }

    @IgnoreLoginCheck
    @ApiOperation(value = "员工分组", response = SellsyStaffGroupResult.class)
    @PostMapping(value = "/queryStaffGroup")
    public Response<SellsyStaffGroupResult> queryStaffGroup() {
        return new Response<>(sellsyStaffService.queryStaffGroups());
    }
}
