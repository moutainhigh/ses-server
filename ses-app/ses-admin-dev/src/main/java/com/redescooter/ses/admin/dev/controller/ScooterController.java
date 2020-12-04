package com.redescooter.ses.admin.dev.controller;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆相关接口管理
 * @author assert
 * @date 2020/12/4 11:34
 */
@Api(tags = {"车辆相关接口管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/scooter", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScooterController {



}
