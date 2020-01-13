package com.redescooter.ses.web.ros.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 26/12/2019 6:23 下午
 * @ClassName: RedisOne
 * @Function: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisOne {

    @Autowired
    private JedisCluster jedis;

    @Test
    public void test01() throws InterruptedException {
        //System.out.println("清空数据："+jedis.flushDB());
        System.out.println("判断某个键是否存在：" + jedis.exists("username"));
        System.out.println("新增<'username','wukong'>的键值对：" + jedis.set("username", "xiaohai"));
        System.out.println("是否存在:" + jedis.exists("username"));
        System.out.println("新增<'password','password'>的键值对：" + jedis.set("password", "123456"));
        //Set<String> keys = jedis.keys("*");
        // System.out.println("系统中所有的键如下："+keys);
        System.out.println("删除键password:" + jedis.del("password"));
        System.out.println("判断键password是否存在：" + jedis.exists("password"));
        System.out.println("设置键username的过期时间为10s:" + jedis.expire("username", 10));
        TimeUnit.SECONDS.sleep(2); // 线程睡眠2秒System.out.println("查看键username的剩余生存时间："+jedis.ttl("username"));
        System.out.println("查看键username的剩余生存时间：" + jedis.ttl("username"));
        System.out.println("移除键username的生存时间：" + jedis.persist("username"));
        System.out.println("查看键username的剩余生存时间：" + jedis.ttl("username"));
        System.out.println("查看键username所存储的值的类型：" + jedis.type("username"));

    }

    public void test1(){

    }


}
