package com.redescooter.ses.mobile.client.controller.personal;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:IdDashboardController
 * @description: IdDashboardController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 11:25
 */

@Slf4j
@Api(tags = {"数据统计模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/id", method = RequestMethod.POST)
public class IdDashboardController {

}
