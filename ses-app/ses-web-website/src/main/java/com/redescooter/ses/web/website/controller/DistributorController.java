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
 * @Function: 产品型号服务
 */
@Api(tags = {"Distributor Management"})
@CrossOrigin
@RestController
@RequestMapping(value = "/model")
public class DistributorController {

    @IgnoreLoginCheck
    @PostMapping(value = "/list")
    @ApiOperation(value = "列表展示", response = GeneralResult.class)
    public Response<List<GeneralResult>> list(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>();
    }
}
