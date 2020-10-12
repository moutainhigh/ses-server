package com.redescooter.ses.web.ros.controller.restproduct;

import com.redescooter.ses.web.ros.service.restproduction.SaleScooterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @ClassNameSaleScooterService
 * @Description
 * @Author Aleks
 * @Date2020/10/12 17:02
 * @Version V1.0
 **/
@Api(tags = {"销售车辆"})
@CrossOrigin
@RestController
@RequestMapping(value = "/sale/scooter")
public class SaleScooterController {

    @Autowired
    private SaleScooterService saleScooterService;

}
