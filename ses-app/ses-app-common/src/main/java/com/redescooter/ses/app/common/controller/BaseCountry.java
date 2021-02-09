package com.redescooter.ses.app.common.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.BaseCountryCodeEnter;
import com.redescooter.ses.api.common.vo.base.BaseCountryCodeResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.foundation.service.base.CountryCodeBaseService;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
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
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 27/12/2019 4:29 下午
 * @ClassName: BaseCountry
 * @Function: TODO
 */
@Api(tags = {"国家编码"})
@CrossOrigin
@RestController
@RequestMapping(value = "/country")
public class BaseCountry {

    @DubboReference
    private CountryCodeBaseService codeBaseService;

    @IgnoreLoginCheck
    @PostMapping(value = "/countryCodelist")
    @ApiOperation(value = "获取国家编码", notes = "获取国家编码", response = CityResult.class)
    public Response<List<BaseCountryCodeResult>> countryCodeList(@ModelAttribute @ApiParam("请求参数") BaseCountryCodeEnter enter) {
        return new Response<>(codeBaseService.getCountryCodeList(enter));
    }

}
