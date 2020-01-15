package com.redescooter.ses.web.delivery.controller.express;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.delivery.service.express.OrderService;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderEnter;
import com.redescooter.ses.web.delivery.vo.excel.ImportExcelOrderResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 14/1/2020 10:52 上午
 * @ClassName: OrderController
 * @Function: TODO
 */
@Api(tags = {"OrderController"})
@CrossOrigin
@RestController
@RequestMapping(value = "/express/orders", method = RequestMethod.POST)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/download")
    @ApiOperation(value = "模板下载")
    public void download(HttpServletResponse response) {
       orderService.download(response);
    }


    @PostMapping(value = "/importOrders")
    @ApiOperation(value = "订单导入", response = GeneralResult.class)
    public Response<ImportExcelOrderResult> importOrders(@ModelAttribute @ApiParam("请求参数") ImportExcelOrderEnter enter) {
        return new Response<>(orderService.importOrders(enter));
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "订单列表", response = PageResult.class)
    public Response<PageResult<GeneralResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @PostMapping(value = "/details")
    @ApiOperation(value = "订单详情", response = GeneralResult.class)
    public Response<GeneralResult> details(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}
