package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.web.ros.service.customer.TransferScooterService;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterIdEnter;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterResult;
import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.service.customer.TransferScooterService;
import com.redescooter.ses.web.ros.vo.customer.DetailsCustomerResult;
import com.redescooter.ses.web.ros.vo.customer.ListCustomerEnter;
import com.redescooter.ses.web.ros.vo.customer.ScooterCustomerResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName:TransferScooterController
 * @description: TransferScooterController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:30
 */
@Api(tags = {"ROS-TransferScooter"})
@CrossOrigin
@RestController
@RequestMapping(value = "/transferscooter")
public class TransferScooterController{

    @Autowired
    private TransferScooterService transferScooterService;


    @IgnoreLoginCheck
    @ApiOperation(value = "分配整车列表", response = ChooseScooterResult.class)
    @PostMapping(value = "/chooseScooterList")
    public Response<PageResult<ChooseScooterResult>> chooseScooterList(@ModelAttribute @ApiParam("请求参数") ChooseScooterIdEnter enter) {
        return new Response<>(transferScooterService.chooseScooterList(enter));
    }


    @PostMapping(value = "/list")
    @ApiOperation(value = "车辆分配用户信息", response = DetailsCustomerResult.class)
    @IgnoreLoginCheck
    public Response<PageResult<ScooterCustomerResult>> list(PageEnter enter) {
        return new Response<>(transferScooterService.scooterCustomerResult(enter));
    }

}
