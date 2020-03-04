package com.redescooter.ses.web.ros.controller.bom;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.ros.service.SalseRosService;
import com.redescooter.ses.web.ros.vo.bom.ProductPriceHistroyListEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.SalesServiceResult;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceEnter;
import com.redescooter.ses.web.ros.vo.bom.sales.SccPriceResult;
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
 * @ClassName:SalesServiceController
 * @description: SalesServiceController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/04 14:41
 */
@Api(tags = {"增值产品管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/bom/sales/service")
public class SalesServiceController {
    @Autowired
    private SalseRosService salseRosService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "增值服务", response = SalesServiceResult.class)
    public Response<PageResult<SalesServiceResult>> salesServiceList(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(salseRosService.salesServiceList(enter));
    }

    @PostMapping(value = "/productPrice")
    @ApiOperation(value = "增值服务产品价格修改", response = String.class)
    public Response<GeneralResult> editServiceProductPrice(@ModelAttribute @ApiParam("请求参数") SccPriceEnter enter) {
        return new Response<>(salseRosService.editServiceProductPrice(enter));
    }

    @PostMapping(value = "/priceDetail")
    @ApiOperation(value = "增值服务产品价格详情", response = SccPriceResult.class)
    public Response<SccPriceResult> servicerProductPriceDetail(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(salseRosService.priceDetail(enter));
    }

    @PostMapping(value = "/priceHistroyList")
    @ApiOperation(value = "增值产品价格历史列表", response = SccPriceResult.class)
    public Response<PageResult<SccPriceResult>> serviceProductPriceList(@ModelAttribute @ApiParam("请求参数") ProductPriceHistroyListEnter enter) {
        return new Response<>(salseRosService.priceList(enter));
    }
}
