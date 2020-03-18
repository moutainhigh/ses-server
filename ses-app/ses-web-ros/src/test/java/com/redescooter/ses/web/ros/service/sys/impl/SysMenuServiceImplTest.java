package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.menu.MenuTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.RouterMeta;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.menu.ModuleAuthResult;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuServiceImplTest {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private OpeSysMenuService menuService;

    @Test
    public void save() {
        SaveMenuEnter save = new SaveMenuEnter();
        save.setName("ROOT");
        save.setPermission("SYS::ROOT");
        save.setPath("/");
        save.setIcon("");
        save.setSort(0);
        save.setType(MenuTypeEnums.MENUS.getValue());
        save.setUserId(new Long("0"));
        sysMenuService.save(save);
    }

    @Test
    public void trees() {
        IdEnter idEnter = new IdEnter();
        idEnter.setId(0L);
        List<MenuTreeResult> trees = sysMenuService.trees(idEnter);
        System.out.println(JSON.toJSONString(trees));
    }

    @Test
    public void vueRouters() {
        List<VueRouter<MenuTreeResult>> vueRouters = sysMenuService.vueRouters(new GeneralEnter());
        System.out.println(JSON.toJSONString(vueRouters));
    }
}
