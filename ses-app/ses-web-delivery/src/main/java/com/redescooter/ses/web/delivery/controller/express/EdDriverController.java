package com.redescooter.ses.web.delivery.controller.express;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.service.RtDriverService;
import com.redescooter.ses.web.delivery.service.express.EdDriverService;
import com.redescooter.ses.web.delivery.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/2/2 14:56
 *  @version：V 1.2
 *  @Description: EdDriverController
 */
@Api(tags = {"快递司机"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rd/driver", method = RequestMethod.POST)
public class EdDriverController {

    @Autowired
    private RtDriverService rtDriverService;
    @Autowired
    private EdDriverService edDriverService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rtDriverService.countStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "司机列表", response = ListDriverResult.class)
    public Response<PageResult<ListDriverResult>> list(@ModelAttribute @ApiParam("请求参数") ListDriverPage page) {
        return new Response<>(rtDriverService.list(page));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建司机", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveDriverEnter enter) {
        return new Response<>(rtDriverService.save(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "司机编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SaveDriverEnter enter) {
        return new Response<>(rtDriverService.save(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "司机详情", response = DriverDetailsResult.class)
    public Response<DriverDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDriverService.details(enter));
    }

    @PostMapping(value = "/leave")
    @ApiOperation(value = "司机离职", response = GeneralResult.class)
    public Response<GeneralResult> leave(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDriverService.leave(enter));
    }


    @PostMapping(value = "/assignScooter")
    @ApiOperation(value = "分配车辆", response = GeneralResult.class)
    public Response<GeneralResult> assignScooter(@ModelAttribute @ApiParam("请求参数") AssignScooterEnter enter) {
        return new Response<>(rtDriverService.assignScooter(enter));
    }

    @PostMapping(value = "/removeScooter")
    @ApiOperation(value = "移除车辆", response = GeneralResult.class)
    public Response<GeneralResult> removeScooter(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDriverService.removeScooter(enter));
    }

    @PostMapping(value = "/againSendEmail")
    @ApiOperation(value = "重发邮件", response = GeneralResult.class)
    public Response<GeneralResult> againSendEmail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDriverService.againSendEmail(enter));
    }

    @PostMapping(value = "/scooterList")
    @ApiOperation(value = "车辆列表", response = ListScooterResult.class)
    public Response<List<ListScooterResult>> scooterList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rtDriverService.scooterList(enter));
    }


    @PostMapping(value = "/scooterInfor")
    @ApiOperation(value = "司机详情车辆信息", response = DriverScooterInforResult.class)
    public Response<DriverScooterInforResult> driverScooterInfor(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDriverService.driverScooterInfor(enter));
    }

    @PostMapping(value = "/deliveryHistroy")
    @ApiOperation(value = "司机详情历史订单", response = DeliveryHistroyResult.class)
    public Response<PageResult<DeliveryHistroyResult>> deliveryHistroy(@ModelAttribute @ApiParam("请求参数") DeliveryHistroyEnter enter) {
        return new Response<>(edDriverService.expressOrderHistroy(enter));
    }

    @PostMapping(value = "/driverScooterHistroy")
    @ApiOperation(value = "车辆分配历史列表", response = DriverScooterHistoryResult.class)
    public Response<PageResult<DriverScooterHistoryResult>> driverscooterHistroy(@ModelAttribute @ApiParam("请求参数") DriverScooterHistroyEnter enter) {
        return new Response<>(rtDriverService.driverscooterHistroy(enter));
    }

    //todo 需要重写

    //    @PostMapping(value = "/driverDeliveryChartList")
//    @ApiOperation(value = "司机订单柱状图", response = DeliveryChartResult.class)
//    public Response<DeliveryChartListResult> driverDeliveryChartList(@ModelAttribute @ApiParam("请求参数") DeliveryChartEnter enter) {
//        return new Response<>(rtDriverService.driverDeliveryChartList(enter));
//    }

}
