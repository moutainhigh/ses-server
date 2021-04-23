package com.redescooter.ses.web.website.controller.config;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.ProductPartsService;
import com.redescooter.ses.web.website.vo.product.AddProductPartsEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jerry
 * @Date 2021/1/8 12:03 上午
 * @Description 产品配件关系服务
 **/
@Api(tags = {"ProductParts"})
@CrossOrigin
@RestController
@RequestMapping(value = "/product/parts")
public class ProductPartsController {

    @Autowired
    private ProductPartsService productPartsService;

    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "给产品添加配件", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddProductPartsEnter enter) {
        System.out.println("进来》》》》》》》》》》》》》》》》》》》》》》");
        return new Response<>(productPartsService.addProductParts(enter));
    }

}
