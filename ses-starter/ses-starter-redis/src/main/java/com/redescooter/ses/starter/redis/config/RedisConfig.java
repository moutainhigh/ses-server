package com.redescooter.ses.starter.redis.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/12/2019 1:01 上午
 * @ClassName: RedisConfig
 * @Function: TODO
 */
@Configuration
public class RedisConfig {
    @Autowired
    private RedisConfigurationProperties redisConfigurationProperties;

    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nodeSet = new HashSet<>();
        for(String node :redisConfigurationProperties.getNodes()) {
            String[] split = node.split(":");
            nodeSet.add(new HostAndPort(split[0],Integer.valueOf(split[1])));
        }
        return new JedisCluster(nodeSet);
    }

}
