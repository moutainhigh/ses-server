package com.redescooter.ses.starter.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;


@Data
@Configuration
@ConfigurationProperties(prefix = "redis", ignoreUnknownFields = true)
public class RedisConfig {

    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int timeout;
    private int maxAttempts;
    private String redisAddress;

    @Bean
    public JedisPoolConfig initJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        return jedisPoolConfig;
    }


    @Bean
    public JedisCluster initJedisCluster(JedisPoolConfig jedisPoolConfig) {
        String[] split = redisAddress.split(";");
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        for (String address : split) {
            String[] node = address.split(":");
            HostAndPort hap = new HostAndPort(node[0], Integer.parseInt(node[1]));
            jedisClusterNode.add(hap);
        }
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, timeout, maxAttempts, jedisPoolConfig);
        return jedisCluster;
    }
}
