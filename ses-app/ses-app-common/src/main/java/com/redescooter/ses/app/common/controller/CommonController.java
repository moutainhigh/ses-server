package com.redescooter.ses.app.common.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.*;
import com.redescooter.ses.app.common.service.AppIdCommonService;
import com.redescooter.ses.app.common.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassNameverificationCodeController
 * @Description
 * @Author Joan
 * @Date2020/8/12 10:05
 * @Version V1.0
 **/
@Slf4j
@Api(tags = {"公共服务"})
@CrossOrigin
@RestController
@RequestMapping(value = "/base", method = RequestMethod.POST)
public class CommonController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private AppIdCommonService appIdCommonService;

    @IgnoreLoginCheck
    @PostMapping(value = "/verificationCode")
    @ApiOperation(value = "验证码校验", response = BooleanResult.class)
    public Response<BooleanResult> checkVerificationCode(@ModelAttribute @ApiParam("请求参数") VerificationCodeEnter enter) {
        return new Response<>(commonService.checkVerificationCode(enter));
    }

    @PostMapping(value = "/appid/list")
    @ApiOperation(value = "应用列表", response = AppIDResult.class)
    public Response<List<AppIDResult>> appIdList(@ModelAttribute @ApiParam("请求参数") GeneralEnter enter) {
        return new Response<>(appIdCommonService.list(enter));
    }


}
