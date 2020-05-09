package com.redescooter.ses.web.ros.service.base.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenRosServiceImplTest {


    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void setAuth() {
        String key = "1002114";
        System.out.println(jedisCluster.exists(key));

        System.out.println(jedisCluster.get(key));
    }


}
