package com.redescooter.ses.web.ros.controller;

import com.redescooter.ses.web.ros.service.CustomerProService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName:CustomerController
 * @description: CustomerController
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 10:07
 */
public class CustomerController {

    @Autowired
    private CustomerProService customerProService;
}
