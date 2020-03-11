package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.menu.MenuTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuServiceImplTest {

    @Autowired
    private SysMenuService sysMenuService;

    @Test
    public void save() {

        int i = 1;
        long pId = 1000009;
        this.otherMenu(i, pId);
    }

    @Test
    public void trees() {
        List<MenuTreeResult> trees = sysMenuService.trees(new GeneralEnter());
        System.out.println(JSON.toJSONString(trees));
    }

    private void otherMenu(int i, long pId) {
        SaveMenuEnter save = new SaveMenuEnter();
//        save.setName("新增");
//        save.setCode("ADD");
//        save.setPermission("");
//        save.setPath("/Add");
//        save.setPId(pId);
//        save.setIcon("");
//        save.setSort(i);
//        save.setType(MenuTypeEnums.BUTTONS.getValue());
//        save.setUserId(new Long("0"));
//        sysMenuService.save(save);
//        ++i;
//        save.setName("更新");
//        save.setCode("UPDATE");
//        save.setPermission("");
//        save.setPath("/UPDATE");
//        save.setPId(pId);
//        save.setIcon("");
//        save.setSort(i);
//        save.setType(MenuTypeEnums.BUTTONS.getValue());
//        save.setUserId(new Long("0"));
//        sysMenuService.save(save);
//        ++i;
        save.setName("查询");
        save.setCode("QUERY");
        save.setPermission("");
        save.setPath("/QUERY");
        save.setPId(pId);
        save.setIcon("");
        save.setSort(i);
        save.setType(MenuTypeEnums.BUTTONS.getValue());
        save.setUserId(new Long("0"));
        sysMenuService.save(save);
//        ++i;
//        save.setName("删除");
//        save.setCode("delete");
//        save.setPermission("");
//        save.setPath("/delete");
//        save.setPId(pId);
//        save.setIcon("");
//        save.setSort(i);
//        save.setType(MenuTypeEnums.BUTTONS.getValue());
//        save.setUserId(new Long("0"));
//        sysMenuService.save(save);
    }

}
