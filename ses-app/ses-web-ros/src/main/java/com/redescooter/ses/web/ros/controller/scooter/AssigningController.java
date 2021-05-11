package com.redescooter.ses.web.ros.controller.scooter;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.assign.AssigningService;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningDetailResult;
import com.redescooter.ses.web.ros.vo.assign.doing.result.AssigningListResult;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.CustomerIdEnter;
import com.redescooter.ses.web.ros.vo.assign.tobe.enter.ToBeAssignListEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 12:48
 */
@Api(value = "车辆处理中控制器", tags = "车辆处理中控制器")
@CrossOrigin
@RestController
@RequestMapping("/scooter/assigning")
public class AssigningController {

    @Autowired
    private AssigningService assigningService;

    /**
     * 处理中列表
     */
    @ApiOperation(value = "处理中列表", notes = "处理中列表")
    @PostMapping("/list")
    public Response<PageResult<AssigningListResult>> getList(@ModelAttribute ToBeAssignListEnter enter) {
        return new Response<>(assigningService.getList(enter));
    }

    /**
     * 处理中查看详情
     */
    @ApiOperation(value = "处理中查看详情", notes = "处理中查看详情")
    @PostMapping("/detail")
    public Response<AssigningDetailResult> getDetail(@ModelAttribute CustomerIdEnter enter) {
        return new Response<>(assigningService.getDetail(enter));
    }

}
