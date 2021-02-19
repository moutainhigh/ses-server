package com.redescooter.ses.app.common.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.VerificationCodeEnter;
import com.redescooter.ses.app.common.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @IgnoreLoginCheck
    @PostMapping(value = "/verificationCode")
    @ApiOperation(value = "验证码校验", response = BooleanResult.class)
    public Response<BooleanResult> checkVerificationCode(@ModelAttribute @ApiParam("请求参数") VerificationCodeEnter enter) {
        return new Response<>(commonService.checkVerificationCode(enter));
    }

}
