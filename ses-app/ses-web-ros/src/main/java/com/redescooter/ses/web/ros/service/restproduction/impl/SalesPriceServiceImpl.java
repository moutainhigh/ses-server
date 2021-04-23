package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.service.base.OpeSalePriceService;
import com.redescooter.ses.web.ros.service.restproduction.SalesPriceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Chris
 * @Date 2021/4/23 20:16
 */
@Service
@Slf4j
public class SalesPriceServiceImpl implements SalesPriceService {

    @Autowired
    private OpeSalePriceService opeSalePriceService;

    @DubboReference
    private IdAppService idAppService;











}
