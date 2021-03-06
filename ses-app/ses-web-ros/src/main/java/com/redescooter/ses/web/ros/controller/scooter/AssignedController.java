package com.redescooter.ses.web.ros.controller.scooter;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.assign.AssignedService;
import com.redescooter.ses.web.ros.vo.assign.done.enter.AssignedListEnter;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedDetailResult;
import com.redescooter.ses.web.ros.vo.assign.done.result.AssignedListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 车辆已分配控制器
 * @Author Chris
 * @Date 2020/12/27 16:35
 */
@Api(value = "车辆已分配控制器", tags = "车辆已分配控制器")
@CrossOrigin
@RestController
@RequestMapping("/scooter/assigned")
public class AssignedController {

    @Autowired
    private AssignedService assignedService;

    /**
     * 已分配列表
     */
    @ApiOperation(value = "已分配列表", notes = "已分配列表")
    @PostMapping("/list")
    public Response<PageResult<AssignedListResult>> getAssignedList(@ModelAttribute AssignedListEnter enter) {
        return new Response<>(assignedService.getAssignedList(enter));
    }

    /**
     * 已分配列表查看详情
     */
    @ApiOperation(value = "已分配列表查看详情", notes = "已分配列表查看详情")
    @PostMapping("/detail")
    public Response<AssignedDetailResult> getAssignedDetail(@ModelAttribute CustomerIdEnter enter) {
        return new Response<>(assignedService.getAssignedDetail(enter));
    }

    /**
     * 已分配列表生成PDF
     */
    @ApiOperation(value = "已分配列表生成PDF", notes = "已分配列表生成PDF")
    @PostMapping("/pdf")
    public Response<GeneralResult> generatePDF(@ModelAttribute IdEnter enter) {
        return new Response<>(assignedService.generatePDF(enter));
    }

}
