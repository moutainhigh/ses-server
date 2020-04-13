package com.redescooter.ses.web.delivery.controller.restaurant;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.delivery.service.EdScooterService;
import com.redescooter.ses.web.delivery.vo.edscooter.ChanageStatusEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterGreenDataResult;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterHistroyEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterHistroyResult;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterListEnter;
import com.redescooter.ses.web.delivery.vo.edscooter.EdScooterResult;
import com.redescooter.ses.web.delivery.vo.task.DriverListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 11:21 上午
 * @ClassName: RtScooterController
 * @Function: TODO
 */
@Api(tags = {"餐厅车辆"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rt/scooter", method = RequestMethod.POST)
public class RtScooterController {

    @Autowired
    private EdScooterService edScooterService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(edScooterService.countStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "车辆列表", response = EdScooterResult.class)
    public Response<PageResult<EdScooterResult>> list(@ModelAttribute @ApiParam("请求参数") EdScooterListEnter enter) {
        return new Response<>(edScooterService.list(enter));
    }

    @PostMapping(value = "/offDrivers")
    @ApiOperation(value = "车辆添加司机列表", response = DriverListResult.class)
    public Response<List<DriverListResult>> offDrivers(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(edScooterService.offDrivers(enter));
    }

    @PostMapping(value = "/detail")
    @ApiOperation(value = "车辆详情", response = EdScooterResult.class)
    public Response<EdScooterResult> detail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(edScooterService.detail(enter));
    }

    @PostMapping(value = "/scooterGreenData")
    @ApiOperation(value = "车辆环保数据", response = EdScooterGreenDataResult.class)
    public Response<EdScooterGreenDataResult> scooterGreenData(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(edScooterService.scooterGreenData(enter));
    }

    @PostMapping(value = "/assignMobileHistroy")
    @ApiOperation(value = "车辆分配记录", response = EdScooterHistroyResult.class)
    public Response<PageResult<EdScooterHistroyResult>> assignMobileHistroy(@ModelAttribute @ApiParam("请求参数") EdScooterHistroyEnter enter) {
        return new Response<>(edScooterService.assignMobileHistroy(enter));
    }

    @PostMapping(value = "/chanageScooterStatus")
    @ApiOperation(value = "更改车辆状态", response = GeneralResult.class)
    public Response<GeneralResult> chanageScooterStatus(@ModelAttribute @ApiParam("请求参数") ChanageStatusEnter enter) {
        return new Response<>(edScooterService.chanageScooterStatus(enter));
    }
}
