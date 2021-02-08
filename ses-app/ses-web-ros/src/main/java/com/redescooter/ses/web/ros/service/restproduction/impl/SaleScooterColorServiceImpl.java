package com.redescooter.ses.web.ros.service.restproduction.impl;

import com.redescooter.ses.starter.common.service.IdAppService;
import com.redescooter.ses.web.ros.service.restproduction.SaleScooterColorService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @ClassNameSaleScooterColorServiceImpl
 * @Description
 * @Author Aleks
 * @Date2020/10/12 17:04
 * @Version V1.0
 **/
@Service
public class SaleScooterColorServiceImpl implements SaleScooterColorService {

    @DubboReference
    private IdAppService idAppService;

}
