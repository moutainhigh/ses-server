package com.redescooter.ses.web.delivery.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redescooter.ses.api.foundation.service.base.UserTokenService;

import io.swagger.annotations.Api;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 30/12/2019 10:55 上午
 * @ClassName: TokenController
 * @Function: TODO
 */

@Api(tags = {"SaaS-Sign"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sign/token")
public class TokenController {

    @Reference
    private UserTokenService userTokenService;

}
