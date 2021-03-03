package com.redescooter.ses.web.ros.controller.sales;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.ros.service.customer.InquiryService;
import com.redescooter.ses.web.ros.service.sales.SalesOrderServer;
import com.redescooter.ses.web.ros.vo.sales.ColorCountResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderDetailsResult;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderEnter;
import com.redescooter.ses.web.ros.vo.sales.SalesOrderListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: ses-server
 * @description: 官网销售预订单服务
 * @author: Jerry
 * @created: 2020/09/29 18:47
 */
@Log4j2
@Api(tags = {"销售订单服务"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sales")
public class SalesOrderController {

    @Autowired
    private SalesOrderServer salesOrderServer;

    @Autowired
    private InquiryService inquiryService;

    @PostMapping(value = "/colorCount")
    @ApiOperation(value = "颜色列表", response = ColorCountResult.class)
    public Response<List<ColorCountResult>> colorCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salesOrderServer.colorCount(enter));
    }

    @PostMapping(value = "/payStatusCount")
    @ApiOperation(value = "支付状态列表", response = Map.class)
    public Response<Map<String, Integer>> payStatusCount(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salesOrderServer.payStatusCount(enter));
    }

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(salesOrderServer.countStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "订单列表", response = SalesOrderListResult.class)
    public Response<PageResult<SalesOrderListResult>> list(@ModelAttribute @ApiParam("请求参数") SalesOrderEnter enter) {
        return new Response<>(salesOrderServer.list(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "订单详情", response = SalesOrderDetailsResult.class)
    public Response<SalesOrderDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salesOrderServer.details(enter));
    }

    @PostMapping(value = "/labels")
    @ApiOperation(value = "标签添加与取消", response = GeneralResult.class)
    public Response<GeneralResult> labels(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salesOrderServer.labels(enter));
    }

    @PostMapping(value = "/cancelWarn")
    @ApiOperation(value = "取消新订单提醒", response = GeneralResult.class)
    public Response<GeneralResult> cancelWarn(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salesOrderServer.cancelWarn(enter));
    }

    @PostMapping(value = "/lastParagraphEmail")
    @ApiOperation(value = "发送尾款邮件", response = GeneralResult.class)
    public Response<GeneralResult> lastParagraphEmail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(inquiryService.lastParagraphEmail(enter));
    }
}
