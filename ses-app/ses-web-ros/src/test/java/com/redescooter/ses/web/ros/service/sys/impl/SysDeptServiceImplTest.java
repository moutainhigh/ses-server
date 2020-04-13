package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.dept.DeptLevelEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.web.ros.service.sys.SysDeptService;
import com.redescooter.ses.web.ros.vo.sys.dept.SaveDeptEnter;
import com.redescooter.ses.web.ros.vo.tree.DeptTreeReslt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysDeptServiceImplTest {

    @Autowired
    private SysDeptService sysDeptService;

    @Test
    public void save() {

        SaveDeptEnter dept = new SaveDeptEnter();
        dept.setPrincipal(0);
        dept.setPId(new Long("1004220"));
        dept.setLevel(Integer.valueOf(DeptLevelEnums.DEPARTMENT.getValue()));
        dept.setName("财务部");
        dept.setCode("Finance Department");
        dept.setSort(1);
        dept.setUserId(0L);
        dept.setTenantId(0L);
        sysDeptService.save(dept);

    }

    @Test
    public void trees() {

        List<DeptTreeReslt> trees = sysDeptService.trees(new GeneralEnter());

        System.out.println(JSON.toJSONString(trees));
    }

    @Test
    public void topDeptartment() {

        DeptTreeReslt deptTreeReslt = sysDeptService.topDeptartment(new IdEnter(new Long("1004221")), null);

        System.out.println(JSON.toJSONString(deptTreeReslt));
    }
}
