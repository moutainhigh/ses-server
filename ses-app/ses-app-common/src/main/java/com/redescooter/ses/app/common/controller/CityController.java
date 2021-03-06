package com.redescooter.ses.app.common.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.CityNameEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.service.base.CityBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityByPageEnter;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.api.foundation.vo.common.CountryCityResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description: CityBaseControllerv 城市数据
 * author: jerry.li
 * <p>
 * create: 2019-05-27 17:40
 */
@Api(tags = {"城市档案"})
@CrossOrigin
@RestController
@RequestMapping(value = "/city")
public class CityController {

    @DubboReference
    private CityBaseService cityBaseService;

    @IgnoreLoginCheck
    @PostMapping(value = "/queryCityByParameterPage")
    @ApiOperation(value = "城市列表分页", response = CityResult.class)
    public Response<PageResult<CityResult>> queryCityByParameterPage(@ModelAttribute @ApiParam("请求参数") CityByPageEnter enter) {
        return new Response<>(cityBaseService.queryCityByParameterPage(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/queryCityDeatli")
    @ApiOperation(value = "获取城市详情", notes = "根据主键获取城市详情", response = CityResult.class)
    public Response<CityResult> queryAllCityPage(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(cityBaseService.queryCityDeatliById(enter));
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/queryChildlevel")
    @ApiOperation(value = "获取城市下级", response = CityResult.class)
    public Response<List<CityResult>> queryChildlevel(@ModelAttribute @ApiParam("请求参数") IdEnter enter) {
        return new Response<>(cityBaseService.queryChildlevel(enter));
    }

    @IgnoreLoginCheck
    @PostMapping("/countryCityPostCode")
    @ApiOperation(value = "Get PostCode")
    public Response<List<CountryCityResult>> countryCityPostCode(@ModelAttribute @ApiParam("Parameter") CityNameEnter cityNameEnter) {
        return new Response<>(cityBaseService.countryCityPostCode(cityNameEnter));
    }

}
