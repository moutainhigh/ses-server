package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.web.website.service.PartsService;
import com.redescooter.ses.web.website.vo.parts.AddPartsEnter;
import com.redescooter.ses.web.website.vo.parts.ModityPartsEnter;
import com.redescooter.ses.web.website.vo.parts.PartsDetailsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/1/7 11:08 下午
 * @Description 部件管理
 **/
@Api(tags = {"Parts"})
@CrossOrigin
@RestController
@RequestMapping(value = "/parts")
public class PartsController {

    @Autowired
    private PartsService partsService;

    @IgnoreLoginCheck
    @PostMapping(value = "/add")
    @ApiOperation(value = "创建部件管理", response = GeneralResult.class)
    public Response<GeneralResult> add(@ModelAttribute @ApiParam("请求参数") AddPartsEnter enter) {
        return new Response<>(partsService.addParts(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/remove")
    @ApiOperation(value = "移除部件管理", response = GeneralResult.class)
    public Response<GeneralResult> remove(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(partsService.removeParts(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "获取部件管理列表", response = PartsDetailsResult.class)
    public Response<List<PartsDetailsResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(partsService.getPartsList(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/modity")
    @ApiOperation(value = "获取部件管理列表", response = GeneralResult.class)
    public Response<GeneralResult> modity(@ModelAttribute @ApiParam("请求参数") ModityPartsEnter enter) {
        return new Response<>(partsService.modityParts(enter));
    }
}
