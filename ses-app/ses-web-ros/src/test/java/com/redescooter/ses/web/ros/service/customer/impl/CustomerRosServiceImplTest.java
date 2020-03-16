package com.redescooter.ses.web.ros.service.customer.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.customer.CustomerRosService;
import com.redescooter.ses.web.ros.vo.account.AccountDeatilResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRosServiceImplTest {

    @Autowired
    private CustomerRosService customerRosService;

    @Test
    public void accountDeatil() {

        AccountDeatilResult accountDeatilResult = customerRosService.accountDeatil(new IdEnter(new Long("1000003")));

        System.out.println(JSON.toJSONString(accountDeatilResult));
    }
}
