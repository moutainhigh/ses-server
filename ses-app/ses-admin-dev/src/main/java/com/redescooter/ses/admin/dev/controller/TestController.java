package com.redescooter.ses.admin.dev.controller;


import com.redescooter.ses.admin.dev.vo.scooter.AdminScooterDTO;
import com.redescooter.ses.admin.dev.vo.scooter.QueryAdminScooterParamDTO;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.StringResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"test"})
@CrossOrigin
@RestController
@RequestMapping(value = "/scooter", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @ApiOperation(value = "查询车辆列表")
    @GetMapping(value = "/test")
    public Response<StringResult> test(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {

        System.out.println(enter.toString());
        StringResult stringResult =new StringResult();
        stringResult.setRequestId(enter.getRequestId());
        stringResult.setValue("test");
        return new Response<>();
    }
}
