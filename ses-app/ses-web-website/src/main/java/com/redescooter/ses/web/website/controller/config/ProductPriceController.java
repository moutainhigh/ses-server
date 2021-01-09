package com.redescooter.ses.web.website.controller.config;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.ProductPriceService;
import com.redescooter.ses.web.website.vo.product.AddProductPriceEnter;
import com.redescooter.ses.web.website.vo.product.ProductPriceDetailsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/7 11:08 下午
 * @Description 产品价格服务
 **/
@Api(tags = {"Product Price"})
@CrossOrigin
@RestController
@RequestMapping(value = "/product/price")
public class ProductPriceController {

    @Autowired
    private ProductPriceService productPriceService;

    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "创建产品价格关系", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddProductPriceEnter enter) {
        return new Response<>(productPriceService.addProductPrice(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/details")
    @ApiOperation(value = "产品价格关系详情", response = ProductPriceDetailsResult.class)
    public Response<ProductPriceDetailsResult> details(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productPriceService.getProductPriceDetails(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "获取产品价格关系列表", response = ProductPriceDetailsResult.class)
    public Response<List<ProductPriceDetailsResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(productPriceService.getProductPriceList(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/remove")
    @ApiOperation(value = "移除产品价格关系列表", response = GeneralResult.class)
    public Response<GeneralResult> remove(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productPriceService.removeProductPrice(enter));
    }
}
