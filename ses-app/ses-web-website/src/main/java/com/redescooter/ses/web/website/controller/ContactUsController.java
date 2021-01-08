package com.redescooter.ses.web.website.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 13/1/2020 4:43 下午
 * @ClassName: ProductModelController
 * @Function: 关于我们
 */
@Api(tags = {"Contact us for service"})
@CrossOrigin
@RestController
@RequestMapping(value = "/contactus")
public class ContactUsController {

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "关于我们列表", response = GeneralResult.class)
    public Response<List<GeneralResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存关于我们", response = GeneralResult.class)
    public Response<List<GeneralResult>> save(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }

    @IgnoreLoginCheck
    @PostMapping(value = "/details")
    @ApiOperation(value = "关于我们详情", response = GeneralResult.class)
    public Response<List<GeneralResult>> details(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }


}
