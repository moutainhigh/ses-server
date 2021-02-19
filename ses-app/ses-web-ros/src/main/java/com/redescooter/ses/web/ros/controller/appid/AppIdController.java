package com.redescooter.ses.web.ros.controller.appid;

import com.redescooter.ses.api.common.vo.base.AppIDResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.app.common.service.AppIdCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jerry
 * @Date 2021/2/19 3:28 下午
 * @Description APPID服务
 **/

@Api(tags = {"APPID服务"})
@CrossOrigin
@RestController
@RequestMapping(value = "/appid")
public class AppIdController {

    @Autowired
    private AppIdCommonService appIdCommonService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "应用列表", response = AppIDResult.class)
    public Response<List<AppIDResult>> appIdList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(appIdCommonService.list(enter));
    }
}
