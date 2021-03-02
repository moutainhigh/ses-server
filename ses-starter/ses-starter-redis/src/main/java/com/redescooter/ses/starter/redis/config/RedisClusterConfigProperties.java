package com.redescooter.ses.starter.redis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author jerry
 * @Date 2021/1/31 10:19 下午
 * @Description RedisClusterConfigProperties
 **/

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisClusterConfigProperties {

    @Autowired
    private RedisClusterNodesConfigProperties redisClusterNodesConfigProperties;
    @Autowired
    private RedisJedisPoolConfigProperties redisJedisPoolConfigProperties;

    private int timeout;

    private String password;
}