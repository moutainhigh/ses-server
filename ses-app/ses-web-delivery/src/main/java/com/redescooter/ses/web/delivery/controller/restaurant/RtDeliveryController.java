package com.redescooter.ses.web.delivery.controller.restaurant;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.service.RtDeliveryService;
import com.redescooter.ses.web.delivery.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 31/12/2019 11:30 上午
 * @ClassName: RtDeliveryController
 * @Function: TODO
 */
@Api(tags = {"配送管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/rt/delivery", method = RequestMethod.POST)
public class RtDeliveryController {

    @Autowired
    private RtDeliveryService rtDeliveryService;

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(rtDeliveryService.countStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "配送单列表", response = ListDeliveryResult.class)
    public Response<PageResult<ListDeliveryResult>> list(@ModelAttribute @ApiParam("请求参数") ListDeliveryPage page) {
        return new Response<>(rtDeliveryService.list(page));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新建配送单", response = GeneralResult.class)
    public Response<GeneralResult> save(@ModelAttribute @ApiParam("请求参数") SaveOrderDeliveryEnter enter) {
        return new Response<>(rtDeliveryService.save(enter));
    }

    @PostMapping(value = "/closed")
    @ApiOperation(value = "订单关闭", response = SelectDriverResult.class)
    public Response<GeneralResult> closed(@ModelAttribute @ApiParam("请求参数") ClosedEnter enter) {
        return new Response<>(rtDeliveryService.closed(enter));
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "配送单编辑", response = GeneralResult.class)
    public Response<GeneralResult> edit(@ModelAttribute @ApiParam("请求参数") SaveOrderDeliveryEnter enter) {
        return new Response<>(rtDeliveryService.save(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "配送单详情", response = DeliveryDetailsResult.class)
    public Response<DeliveryDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDeliveryService.details(enter));
    }



    @PostMapping(value = "/selectDriver")
    @ApiOperation(value = "司机列表选择", response = SelectDriverResult.class)
    public Response<List<SelectDriverResult>> selectDriver(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        List<SelectDriverResult> selectDriverResults = rtDeliveryService.selectDriverList(enter);
        return new Response<>(selectDriverResults);
    }

    @PostMapping(value = "/map")
    @ApiOperation(value = "地图", response = MapResult.class)
    public Response<MapResult> map(@ModelAttribute @ApiParam("请求参数") MapEnter enter) {
        return new Response<>(rtDeliveryService.map(enter));
    }

    @PostMapping(value = "/licensePlateList")
    @ApiOperation(value = "车牌号列表", response = ScooterLicensePlateResult.class)
    public Response<List<ScooterLicensePlateResult>> scooterLicensePlate(@ModelAttribute @ApiParam("请求参数") ScooterLicensePlateEnter enter) {
        return new Response<>(rtDeliveryService.scooterLicensePlate(enter));
    }

    @PostMapping(value = "/orderInfor")
    @ApiOperation(value = "司机订单列表", response = DriverDeliveryInfoResult.class)
    public Response<DriverDeliveryInfoResult> driverDeliveryInfor(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDeliveryService.driverDeliveryInfor(enter));
    }

    @PostMapping(value = "/scooterInfor")
    @ApiOperation(value = "车辆信息", response = ScooterMapResult.class)
    public Response<ScooterMapResult> scooterInfor(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDeliveryService.scooterInfor(enter));
    }

    @PostMapping(value = "/nodelist")
    @ApiOperation(value = "详情节点列表", response = DeliveryNodeResult.class)
    public Response<List<DeliveryNodeResult>> nodeList(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(rtDeliveryService.nodeList(enter));
    }

    @PostMapping(value = "/deliveryReset")
    @ApiOperation(value = "订单再次分配", response = GeneralResult.class)
    public Response<GeneralResult> deliveryReset(@ModelAttribute @ApiParam("请求参数") DeliveryResetEnter enter) {
        return new Response<>(rtDeliveryService.deliveryReset(enter));
    }
}
