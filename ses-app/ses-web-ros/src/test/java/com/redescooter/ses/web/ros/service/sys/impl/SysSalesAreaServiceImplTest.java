package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.service.sys.SysSalesAreaService;
import com.redescooter.ses.web.ros.vo.tree.SalesAreaTressResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysSalesAreaServiceImplTest {

    @Autowired
    private SysSalesAreaService sysSalesAreaService;

    @Test
    public void list() {

        List<SalesAreaTressResult> list = sysSalesAreaService.list(new GeneralEnter());

        System.out.println(JSON.toJSONString(list));

    }
}
