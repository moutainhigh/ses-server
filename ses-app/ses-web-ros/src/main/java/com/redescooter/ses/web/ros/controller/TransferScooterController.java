package com.redescooter.ses.web.ros.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:TransferScooterController
 * @description: TransferScooterController
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:30
 */
@Api(tags = {"ROS-TransferScooter"})
@CrossOrigin
@RestController
@RequestMapping(value = "/transferscooter")
public class TransferScooterController{
}
