package com.redescooter.ses.web.ros.service.sys.impl;

import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysDeptServiceImplTest {

    @Autowired
    private SysDeptService sysDeptService;

    @Test
    public void save() {

//        SaveDeptEnter dept = new SaveDeptEnter();
//        dept.setPrincipal(0);
//        dept.setLevel(0);
//        dept.setName("RdedGroup");
//        dept.setCode("RDEDGROUP");
//        dept.setSort(0);
//        dept.setUserId(0L);
//        dept.setTenantId(0L);

        SaveDeptEnter dept = new SaveDeptEnter();
        dept.setPrincipal(0);
        dept.setPId(new Long("1000002"));
        dept.setLevel(0);
        dept.setName("开发部");
        dept.setCode("Development Department");
        dept.setSort(1);
        dept.setUserId(0L);
        dept.setTenantId(0L);

        sysDeptService.save(dept);
    }

    @Test
    public void trees() {
    }
}
