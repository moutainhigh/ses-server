package com.redescooter.ses.web.delivery.controller.express;

import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.web.delivery.service.RtDeliveryService;
import com.redescooter.ses.web.delivery.service.RtDriverService;
import com.redescooter.ses.web.delivery.service.express.EdOrderService;
import com.redescooter.ses.web.delivery.vo.*;
import com.redescooter.ses.web.delivery.vo.edorder.*;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 10:52 上午
 * @ClassName: EdOrderController
 * @Function: TODO
 */
@Api(tags = {"快递订单"})
@CrossOrigin
@RestController
@RequestMapping(value = "/ed/orders", method = RequestMethod.POST)
public class EdOrderController {

    @Autowired
    private EdOrderService edOrderService;

    @Autowired
    private RtDriverService rtDriverService;

    @Autowired
    private RtDeliveryService rtDeliveryService;

    @GetMapping(value = "/download")
    @ApiOperation(value = "模板下载")
    public void download(HttpServletResponse response) {
        edOrderService.download(response);
    }

    @PostMapping(value = "/importOrders")
    @ApiOperation(value = "订单导入", response = GeneralResult.class)
    public Response<ImportExcelOrderResult> importOrders(@ModelAttribute @ApiParam("请求参数") ImportExcelOrderEnter enter) {
        return new Response<>(edOrderService.importOrders(enter));
    }

    @PostMapping(value = "/countStatus")
    @ApiOperation(value = "状态统计", response = Map.class)
    public Response<Map<String, Integer>> countStatus(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(edOrderService.countStatus(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "订单列表", response = PageResult.class)
    public Response<PageResult<QueryExpressOrderByPageResult>> list(@ModelAttribute @ApiParam("请求参数") QueryExpressOrderByPageEnter enter) {
        return new Response<>(edOrderService.list(enter));
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "订单详情", response = GeneralResult.class)
    public Response<QueryOrderDetailResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(edOrderService.details(enter));
    }

    @PostMapping(value = "/map")
    @ApiOperation(value = "地图接口", response = ExpressOrderMapResult.class)
    public Response<ExpressOrderMapResult> expressOrderMap(@ModelAttribute @ApiParam("请求参数") ExpressOrderMapEnter enter) {
        return new Response<>(edOrderService.expressOrderMap(enter));
    }

    @PostMapping(value = "/diverOrderInfor")
    @ApiOperation(value = "司机订单列表", response = DiverOrderInforResult.class)
    public Response<DiverOrderInforResult> diverOrderInfor(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(edOrderService.diverOrderInfor(enter));
    }

    @PostMapping(value = "/driverList")
    @ApiOperation(value = "司机列表", response = ListDriverResult.class)
    public Response<PageResult<ListDriverResult>> list(@ModelAttribute @ApiParam("请求参数") ListDriverPage page) {
        return new Response<>(rtDriverService.list(page));
    }

    @PostMapping(value = "/cancelOrder")
    @ApiOperation(value = "订单取消", response = DiverOrderInforResult.class)
    public Response<GeneralResult> cancelOrder(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(edOrderService.cancelOrder(enter));
    }

    @PostMapping(value = "/licensePlateList")
    @ApiOperation(value = "车牌号列表", response = ScooterLicensePlateResult.class)
    public Response<List<ScooterLicensePlateResult>> scooterLicensePlate(@ModelAttribute @ApiParam("请求参数") ScooterLicensePlateEnter enter) {
        return new Response<>(rtDeliveryService.scooterLicensePlate(enter));
    }

    @PostMapping(value = "/refuseOrderDetail")
    @ApiOperation(value = "拒绝订单详情", response = RefuseOrderDetailResult.class)
    public Response<RefuseOrderDetailResult> refuseOrderDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(edOrderService.refuseOrderDetail(enter));
    }

    @PostMapping(value = "/chanageOrder")
    @ApiOperation(value = "重新分配订单", response = GeneralResult.class)
    public Response<GeneralResult> chanageExpressOrder(@ModelAttribute @ApiParam("请求参数") ChanageExpressOrderEnter enter) {
        return new Response<>(edOrderService.chanageExpressOrder(enter));
    }
}
