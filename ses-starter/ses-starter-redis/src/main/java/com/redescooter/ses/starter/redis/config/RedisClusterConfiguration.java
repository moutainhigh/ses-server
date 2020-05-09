package com.redescooter.ses.starter.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/12/2019 1:08 上午
 * @ClassName: RedisClusterConfiguration
 * @Function: TODO
 */
@Data
@EnableCaching
@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterConfiguration {

    private Integer maxRedirects;

    private List<String> nodes = new ArrayList<>();

}
