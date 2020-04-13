package com.redescooter.ses.starter.redis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/12/2019 1:01 上午
 * @ClassName: RedisConfiguration
 * @Function: TODO
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfiguration {

    @Autowired
    private RedisClusterConfiguration redisClusterConfiguration;
    @Autowired
    private RedisJedisPoolConfiguration redisJedisPoolConfiguration;

    private int timeout;


}
