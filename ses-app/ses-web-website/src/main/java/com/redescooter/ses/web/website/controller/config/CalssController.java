package com.redescooter.ses.web.website.controller.config;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.ProductClassService;
import com.redescooter.ses.web.website.vo.product.AddProductClassEnter;
import com.redescooter.ses.web.website.vo.product.ProductClassDetailsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/9 10:15 下午
 * @Description 产品大类服务
 **/
@Api(tags = {"Product Class"})
@CrossOrigin
@RestController
@RequestMapping(value = "/class")
public class CalssController {

    @Autowired
    private ProductClassService productClassService;

    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "Add Product Class", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddProductClassEnter enter) {
        return new Response<>(productClassService.addProductClass(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "Product Class list", response = GeneralResult.class)
    public Response< List<ProductClassDetailsResult> > list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(productClassService.getProductClassList(enter));
    }


}
