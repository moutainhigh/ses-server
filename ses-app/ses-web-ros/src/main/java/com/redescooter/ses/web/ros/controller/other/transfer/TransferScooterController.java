package com.redescooter.ses.web.ros.controller.other.transfer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.customer.TransferScooterService;
import com.redescooter.ses.web.ros.vo.customer.ScooterCustomerResult;
import com.redescooter.ses.web.ros.vo.customer.TransferScooterEnter;
import com.redescooter.ses.web.ros.vo.transferscooter.ChooseScooterListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:TransferScooterController
 * @description: TransferScooterController
 * @author: Alex @Version：1.3
 * @create: 2020/04/24 16:30
 */
@Api(tags = {"ROS-TransferScooter"})
@CrossOrigin
@RestController
@RequestMapping(value = "/transferscooter")
public class TransferScooterController {

    @Autowired
    private TransferScooterService transferScooterService;

    @ApiOperation(value = "分配整车列表", response = ChooseScooterListResult.class)
    @PostMapping(value = "/chooseScooterList")
    public Response<ChooseScooterListResult> chooseScooterList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(transferScooterService.chooseScooterList(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "车辆分配用户列表", response = ScooterCustomerResult.class)
    public Response<PageResult<ScooterCustomerResult>> list(PageEnter enter) {
        return new Response<>(transferScooterService.scooterCustomerResult(enter));
    }

    @PostMapping(value = "/transferScooter")
    @ApiOperation(value = "客户分配车辆", response = GeneralResult.class)
    public Response<GeneralResult> list(TransferScooterEnter enter) {
        return new Response<>(transferScooterService.transferScooter(enter));
    }
}
