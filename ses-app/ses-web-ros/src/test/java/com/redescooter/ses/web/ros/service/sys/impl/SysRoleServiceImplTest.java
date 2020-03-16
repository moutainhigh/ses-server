package com.redescooter.ses.web.ros.service.sys.impl;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.sys.SysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleServiceImplTest {

    @Autowired
    private SysRoleService roleService;


    @Test
    public void delete() {

        GeneralResult delete = roleService.delete(new IdEnter(new Long("1000009")));

        System.out.println(delete);
    }

    @Test
    public void authorityDetails() {
    }
}
