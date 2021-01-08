package com.redescooter.ses.web.website.controller;

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
 * @Description 产品配置服务
 **/
@Api(tags = {"ProductColour"})
@CrossOrigin
@RestController
@RequestMapping(value = "/productparts")
public class ProductPartsController {

    @Autowired
    private ProductPartsService productPartsService;

    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "创建产品配件", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddProductPartsEnter enter) {
        return new Response<>(productPartsService.addProductParts(enter));
    }

}
