package com.redescooter.ses.starter.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/12/2019 1:08 上午
 * @ClassName: RedisConfigurationProperties
 * @Function: TODO
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisConfigurationProperties {
    private List<String> nodes = new ArrayList<>();

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}
