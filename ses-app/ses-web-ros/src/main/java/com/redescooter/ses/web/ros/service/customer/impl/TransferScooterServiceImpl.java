package com.redescooter.ses.web.ros.service.customer.impl;

import com.redescooter.ses.api.common.vo.base.IntResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.dao.CustomerServiceMapper;
import com.redescooter.ses.web.ros.service.customer.TransferScooterService;
import com.redescooter.ses.web.ros.vo.customer.ScooterCustomerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:TransferScooterServiceImpl
 * @description: TransferScooterServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:43
 */
@Service
public class TransferScooterServiceImpl implements TransferScooterService {
    @Autowired
    private CustomerServiceMapper customerServiceMapper;
    /**
     * 车辆用户分配信息
     *
     * @param enter
     */
    @Override
    public PageResult<ScooterCustomerResult> scooterCustomerResult(PageEnter enter) {
        int count = customerServiceMapper.scooterCustomerCount(enter);
        if (count==0){
            PageResult.createZeroRowResult(enter);
        }
        List<ScooterCustomerResult> scooterCustomerResults = customerServiceMapper.scooterCustomerResult(enter);
        return PageResult.create(enter,count,scooterCustomerResults);
    }

}
