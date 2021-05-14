package com.redescooter.ses.web.ros.controller.codebase;

import com.redescooter.ses.web.ros.service.codebase.RSNService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description RSN管理控制器
 * @Author Chris
 * @Date 2021/5/14 9:42
 */
@Api(value = "RSN管理控制器", tags = "RSN管理控制器")
@CrossOrigin
@RestController
@RequestMapping("/codebase/rsn")
public class RSNController {

    @Autowired
    private RSNService rsnService;













}
