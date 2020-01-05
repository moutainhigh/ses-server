package com.redescooter.ses.starter.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/12/2019 1:08 上午
 * @ClassName: RedisClusterConfiguration
 * @Function: TODO
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis.jedis.pool")
public class RedisJedisPoolConfiguration {

    private Integer maxActive;

    private Integer maxIdle;

    private Long maxWait;

    private Integer minIdle;
}
