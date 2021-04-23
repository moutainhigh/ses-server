package com.redescooter.ses.web.ros.controller.product;

import com.redescooter.ses.web.ros.service.restproduction.SalesPriceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 销售价格控制器
 * @Author Chris
 * @Date 2021/4/23 20:13
 */
@Api(value = "销售价格控制器", tags = "销售价格控制器")
@CrossOrigin
@RestController
@RequestMapping("/sales/price")
public class SalesPriceController {

    @Autowired
    private SalesPriceService salesPriceService;

















}
