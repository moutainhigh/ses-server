package com.redescooter.ses.web.ros.controller.codebase;

import com.redescooter.ses.web.ros.service.codebase.VINService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description VIN管理控制器
 * @Author Chris
 * @Date 2021/5/14 9:45
 */
@Api(value = "VIN管理控制器", tags = "VIN管理控制器")
@CrossOrigin
@RestController
@RequestMapping("/codebase/vin")
public class VINController {

    @Autowired
    private VINService vinService;



















}
