package com.redescooter.ses.app.common.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.service.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityByPageEnter;
import com.redescooter.ses.api.foundation.vo.common.CityEnter;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description: CityBaseControllerv 城市数据
 * author: jerry.li
 * 
 * create: 2019-05-27 17:40
 */
@Api(tags = {"城市档案"})
@CrossOrigin
@RestController
@RequestMapping(value = "/city")
public class CityController {

    @Reference
    private CityBaseService cityBaseService;

    @IgnoreLoginCheck
    @PostMapping(value = "/queryCityDeatli")
    @ApiOperation(value = "获取城市详情", notes = "根据主键获取城市详情", response = CityResult.class)
    public Response<CityResult> queryAllCityPage(@ModelAttribute @ApiParam("请求参数") CityEnter enter) {
        return new Response<>(cityBaseService.queryCityDeatliById(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/queryCityByParameter")
    @ApiOperation(value = "根据条件获取城市区域列表", response = CityResult.class)
    public Response<List<CityResult>> queryCityByParameter(@ModelAttribute @ApiParam("请求参数") CityEnter enter) {
        return new Response<>(cityBaseService.queryCityByParameter(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/queryCityByParameterPage")
    @ApiOperation(value = "根据条件获取城市列表(分页)", response = CityResult.class)
    public Response<PageResult<CityResult>> queryCityByParameterPage(@ModelAttribute @ApiParam("请求参数") CityByPageEnter enter) {
        return new Response<>(cityBaseService.queryCityByParameterPage(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/queryAllCity")
    @ApiOperation(value = "获取所有城市列表", response = CityResult.class)
    public Response<List<CityResult>> queryAllCity(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(cityBaseService.queryAllCity(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/queryAllCityPage")
    @ApiOperation(value = "获取所有城市列表（分页）", response = CityResult.class)
    public Response<PageResult<CityResult>> queryAllCityPage(@ModelAttribute @ApiParam("请求参数") PageEnter enter) {
        return new Response<>(cityBaseService.queryAllCityPage(enter));
    }

}
