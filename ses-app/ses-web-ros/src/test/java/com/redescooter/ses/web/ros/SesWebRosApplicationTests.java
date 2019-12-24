package com.redescooter.ses.web.ros;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisCluster;

@SpringBootTest
public class SesWebRosApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Reference
    private JedisCluster jedisCluster;

    @Test
    public void createAccount() {
        System.out.println("是否存在:"+jedisCluster.exists("f932ef6212ac472995454a7bf5a9786e"));
    }
}
