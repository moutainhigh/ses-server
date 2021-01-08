package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.ProductModelService;
import com.redescooter.ses.web.website.vo.product.AddProductModelEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductModelEnter;
import com.redescooter.ses.web.website.vo.product.ProductModelDetailsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/7 11:08 下午
 * @Description 产品颜色关系关系管理
 **/
@Api(tags = {"ProductColour"})
@CrossOrigin
@RestController
@RequestMapping(value = "/productmodel")
public class ProductModelController {

    @Autowired
    private ProductModelService productModelService;

    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "创建产品颜色关系", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddProductModelEnter enter) {
        return new Response<>(productModelService.addProductModel(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/remove")
    @ApiOperation(value = "移除产品颜色关系", response = GeneralResult.class)
    public Response<GeneralResult> remove(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productModelService.removeProductModel(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/modity")
    @ApiOperation(value = "编辑产品颜色关系", response = GeneralResult.class)
    public Response<GeneralResult> modity(@ModelAttribute @ApiParam("请求参数") ModityProductModelEnter enter) {
        return new Response<>(productModelService.modityProductModel(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "获取产品颜色关系列表", response = GeneralResult.class)
    public Response<List<ProductModelDetailsResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(productModelService.getProductModelList(enter));
    }
}
