package com.redescooter.ses.web.ros.controller.website;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:Website
 * @description: Website
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 11:58
 */
@Log4j2
@Api(tags = {"官网模块"})
@CrossOrigin
@RestController
@RequestMapping(value = "/website")
public class WebsiteController {


}
