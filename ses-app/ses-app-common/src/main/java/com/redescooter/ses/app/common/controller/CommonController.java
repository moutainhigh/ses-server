package com.redescooter.ses.app.common.controller;

import com.redescooter.ses.api.common.annotation.IgnoreLoginCheck;
import com.redescooter.ses.api.common.vo.base.Response;
import com.redescooter.ses.api.common.vo.base.VerificationCodeEnter;
import com.redescooter.ses.api.foundation.vo.common.CityResult;
import com.redescooter.ses.app.common.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassNameverificationCodeController
 * @Description
 * @Author Joan
 * @Date2020/8/12 10:05
 * @Version V1.0
 **/
@Slf4j
@Api(tags = {"验证码校验"})
@CrossOrigin
@RestController
@RequestMapping(value = "/code", method = RequestMethod.POST)
public class CommonController {
  @Reference
  private CommonService commonService;

  @IgnoreLoginCheck
  @PostMapping(value = "/verificationCode")
  @ApiOperation(value = "验证码校验", response = CityResult.class)
  public Response<Boolean> queryCityByParameterPage(@ModelAttribute @ApiParam("请求参数") VerificationCodeEnter enter) {
    return new Response<>(commonService.checkVerificationCode(enter));
  }
}
