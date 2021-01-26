package com.redescooter.ses.mobile.rps.controller.combinorder;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组装单接口管理
 * @author assert
 * @date 2021/1/26 18:50
 */
@Api(tags = {"组装单管理"})
@CrossOrigin
@RestController
@RequestMapping(value = "/combin/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class CombinationOrderController {



}
