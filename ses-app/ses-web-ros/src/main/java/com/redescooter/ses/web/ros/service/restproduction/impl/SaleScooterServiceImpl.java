package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.dao.restproduction.SaleScooterMapper;
import com.redescooter.ses.web.ros.service.restproduction.SaleScooterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassNameSaleScooterServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/12 17:03
 * @Version V1.0
 **/
@Service
@Slf4j
public class SaleScooterServiceImpl implements SaleScooterService {

    @Autowired
    private SaleScooterMapper saleScooterMapper;

    @Reference
    private IdAppService idAppService;

}
