package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.enums.menu.MenuTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.RouterMeta;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.web.ros.dm.OpeSysMenu;
import com.redescooter.ses.web.ros.service.base.OpeSysMenuService;
import com.redescooter.ses.web.ros.service.sys.SysMenuService;
import com.redescooter.ses.web.ros.utils.TreeUtil;
import com.redescooter.ses.web.ros.vo.sys.menu.ModulePermissionsResult;
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

        int i = 1;
        long pId = 100000;
        this.otherMenu(i, pId);
    }

    @Test
    public void trees() {
        IdEnter idEnter = new IdEnter();
        idEnter.setId(0L);
        List<MenuTreeResult> trees = sysMenuService.trees(idEnter);
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
        save.setName("Inquiry");
        save.setPermission("SYS::SYS");
        save.setPath("/");
        save.setPId(pId);
        save.setIcon("");
        save.setSort(i);
        save.setType(MenuTypeEnums.checkCode(MenuTypeEnums.MENUS.getValue()));
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

    @Test
    public void getUserRouters() {
        List<VueRouter<MenuTreeResult>> routes = new ArrayList<>();
        //   List<MenuTreeResult> menus = sysMenuService.trees(new GeneralEnter());

        List<OpeSysMenu> list = menuService.list();

        list.forEach(menu -> {
            VueRouter<MenuTreeResult> route = new VueRouter<>();
            route.setId(String.valueOf(menu.getId()));
            route.setParentId(String.valueOf(menu.getPId()));
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getName());
            route.setMeta(new RouterMeta(menu.getName(), menu.getIcon(), true));
            routes.add(route);
        });

        List<VueRouter<MenuTreeResult>> vueRouters = TreeUtil.buildVueRouter(routes);
        System.out.println(JSON.toJSONString(vueRouters));

    }

    @Test
    public void modulePermissions() {

        Map<String, ModulePermissionsResult> stringModulePermissionsResultMap = sysMenuService.modulePermissions(new IdEnter());

        System.out.println(JSON.toJSONString(stringModulePermissionsResultMap));
    }

    @Test
    public void details() {
    }

    @Test
    public void delete() {
        GeneralResult delete = sysMenuService.delete(new IdEnter(new Long("1000007")));
        System.out.println(delete);
    }
}
