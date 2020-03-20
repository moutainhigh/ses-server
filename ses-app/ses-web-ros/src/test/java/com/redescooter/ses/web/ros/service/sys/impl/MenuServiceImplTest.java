package com.redescooter.ses.web.ros.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.redescooter.ses.api.common.constant.CacheConstants;
import com.redescooter.ses.api.common.enums.menu.MenuTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.router.VueRouter;
import com.redescooter.ses.starter.redis.enums.RedisExpireEnum;
import com.redescooter.ses.web.ros.service.sys.MenuService;
import com.redescooter.ses.web.ros.vo.sys.menu.SaveMenuEnter;
import com.redescooter.ses.web.ros.vo.tree.MenuTreeResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceImplTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("单元测试开始--------------------->");
    }

    @After
    public void tearDown() throws Exception {
    }


    @Autowired
    private MenuService menuService;

    @Autowired
    private JedisCluster jedisCluster;

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
        menuService.save(save);
    }

    @Test
    public void trees() {
        IdEnter idEnter = new IdEnter();
        List<MenuTreeResult> trees = menuService.trees(idEnter);
        System.out.println(JSON.toJSONString(trees));
    }

    @Test
    public void vueRouters() {
        List<VueRouter<MenuTreeResult>> vueRouters = menuService.vueRouters(new GeneralEnter());
        System.out.println(JSON.toJSONString(vueRouters));
    }

    @Test
    public void roleMenuAuth() {
        GeneralEnter enter = new GeneralEnter();
        enter.setUserId(1008376L);
        List<MenuTreeResult> results = menuService.roleMenuAuthTree(enter);

        System.out.println(JSON.toJSONString(results));
    }

    @Test
    public void roleMenuAuthTree() {
        IdEnter idEnter = new IdEnter();
        idEnter.setId(1008376L);
        List<MenuTreeResult> treeResults = menuService.roleMenuAuthTreeByRoleId(idEnter);
        System.out.println(JSON.toJSONString(treeResults));
    }

    @Test
    public void roleMenuAuthParallelByRoleId() {
        IdEnter idEnter = new IdEnter();
        idEnter.setId(1008376L);
        List<MenuTreeResult> treeResults = menuService.roleMenuAuthParallelByRoleId(idEnter);
        System.out.println(JSON.toJSONString(treeResults));
    }


    @Test
    public void roleMenuAuthParallel() {
        String key = new StringBuilder().append(1006358L).append(":::").append(CacheConstants.MENU_DETAILS).toString();
        if (!jedisCluster.exists(key)) {
            GeneralEnter enter = new GeneralEnter();
            enter.setUserId(1008371L);
            List<MenuTreeResult> menuTreeResults = menuService.roleMenuAuthParallel(enter);
            jedisCluster.set(key, JSON.toJSONString(menuTreeResults));
            jedisCluster.expire(key, new Long(RedisExpireEnum.MINUTES_1.getSeconds()).intValue());
            System.out.println("============数据库==========");
        } else {
            List<MenuTreeResult> menuTreeResults = JSON.parseArray(jedisCluster.get(key), MenuTreeResult.class);
            menuTreeResults.stream().forEach(m -> {
                System.out.println(m.getName() + "<><><><><><><><>" + m.getType() + "<><><><><><><><>" + m.getPath());
            });
            System.out.println("============缓存==========");

        }


    }

    @Test
    public void testRoleMenuAuthParallel() {
        GeneralEnter enter = new GeneralEnter();
        enter.setUserId(1008371L);
        List<MenuTreeResult> menuByRoleId = menuService.findMenuByRoleId(enter);

        System.out.println(JSON.toJSONString(menuByRoleId));
    }
}
