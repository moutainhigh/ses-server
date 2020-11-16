package com.redescooter.ses.mobile.client.controller.meter;

import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:MeterController
 * @description: MeterController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/16 17:26 
 */
@Slf4j
@Api(tags = {"车载仪表临时接口"})
@CrossOrigin
@RestController
@RequestMapping(value = "/meter", method = RequestMethod.POST)
public class MeterController {
}
