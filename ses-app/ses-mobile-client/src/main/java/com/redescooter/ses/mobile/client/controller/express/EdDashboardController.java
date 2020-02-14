package com.redescooter.ses.mobile.client.controller.express;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = {"统计模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/ed/dashboard", method = RequestMethod.POST)
public class EdDashboardController {

}
