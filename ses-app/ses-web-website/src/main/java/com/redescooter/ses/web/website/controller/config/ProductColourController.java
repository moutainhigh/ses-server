package com.redescooter.ses.web.website.controller.config;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.ProductColourService;
import com.redescooter.ses.web.website.vo.product.AddProductColourEnter;
import com.redescooter.ses.web.website.vo.product.ModityProductColourEnter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jerry
 * @Date 2021/1/7 11:08 下午
 * @Description 产品颜色关系关系管理
 **/
@Api(tags = {"ProductColour"})
@CrossOrigin
@RestController
@RequestMapping(value = "/productcolour")
public class ProductColourController {

    @Autowired
    private ProductColourService productColourService;

    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "创建产品颜色关系", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddProductColourEnter enter) {
        return new Response<>(productColourService.addProductColour(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/remove")
    @ApiOperation(value = "移除产品颜色关系", response = GeneralResult.class)
    public Response<GeneralResult> remove(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(productColourService.removeProductColour(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/modity")
    @ApiOperation(value = "产品颜色关系列表", response = GeneralResult.class)
    public Response<GeneralResult> modity(@ModelAttribute @ApiParam("请求参数") ModityProductColourEnter enter) {
        return new Response<>(productColourService.modityProductColour(enter));
    }
}
