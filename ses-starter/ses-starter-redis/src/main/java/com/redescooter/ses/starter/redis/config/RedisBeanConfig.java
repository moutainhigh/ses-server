package com.redescooter.ses.starter.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 2:07 上午
 * @ClassName: RedisBeanConfig
 * @Function: TODO
 */
@Configuration
public class RedisBeanConfig {

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nodeSet = new HashSet<>();
        for (String node : redisConfiguration.getRedisClusterConfiguration().getNodes()) {
            String[] split = node.split(":");
            nodeSet.add(new HostAndPort(split[0], Integer.valueOf(split[1])));
        }

        return new JedisCluster(nodeSet, redisConfiguration.getTimeout(), jedisPool());
    }

    @Bean
    public GenericObjectPoolConfig jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfiguration.getRedisJedisPoolConfiguration().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfiguration.getRedisJedisPoolConfiguration().getMaxWait());
        jedisPoolConfig.setMaxTotal(redisConfiguration.getRedisJedisPoolConfiguration().getMaxActive());
        jedisPoolConfig.setMinIdle(redisConfiguration.getRedisJedisPoolConfiguration().getMinIdle());
        return jedisPoolConfig;
    }


//    @Bean
//    public JedisPoolConfig jedisPool() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(redisConfiguration.getRedisJedisPoolConfiguration().getMaxIdle());
//        jedisPoolConfig.setMaxWaitMillis(redisConfiguration.getRedisJedisPoolConfiguration().getMaxWait());
//        jedisPoolConfig.setMaxTotal(redisConfiguration.getRedisJedisPoolConfiguration().getMaxActive());
//        jedisPoolConfig.setMinIdle(redisConfiguration.getRedisJedisPoolConfiguration().getMinIdle());
//        return jedisPoolConfig;
//    }
}
