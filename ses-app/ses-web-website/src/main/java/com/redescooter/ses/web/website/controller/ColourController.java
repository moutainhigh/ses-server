package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.ColourService;
import com.redescooter.ses.web.website.vo.colour.AddColourEnter;
import com.redescooter.ses.web.website.vo.product.ColourDetailsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/7 11:08 下午
 * @Description 颜色管理
 **/
@Api(tags = {"Colour"})
@CrossOrigin
@RestController
@RequestMapping(value = "/colour")
public class ColourController {

    @Autowired
    private ColourService colourService;

    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "创建产品颜色", response = GeneralResult.class)
    public Response<GeneralResult> addColour(@ModelAttribute @ApiParam("请求参数") AddColourEnter enter) {
        return new Response<>(colourService.addColour(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/remove")
    @ApiOperation(value = "移除产品颜色", response = GeneralResult.class)
    public Response<GeneralResult> remove(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(colourService.removeColour(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "获取产品颜色列表", response = ColourDetailsResult.class)
    public Response<List<ColourDetailsResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(colourService.getColourList(enter));
    }
}
