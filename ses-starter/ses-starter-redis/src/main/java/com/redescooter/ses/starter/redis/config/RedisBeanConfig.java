package com.redescooter.ses.starter.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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
@Slf4j
@Configuration
public class RedisBeanConfig extends CachingConfigurerSupport {

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

    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
        return cacheErrorHandler;
    }

    @org.springframework.context.annotation.Bean
    @Override
    public KeyGenerator keyGenerator() {
        //  设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
        // 使用：进行分割，可以很多显示出层级关系
        // 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法，我感觉很好用，大家感兴趣可以去了解下
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":" + String.valueOf(obj));
            }
            String rsToUse = String.valueOf(sb);
            log.info("自动生成Redis Key -> [{}]", rsToUse);
            return rsToUse;
        };
    }

    @org.springframework.context.annotation.Bean
    public CacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
        // 初始化缓存管理器，在这里我们可以缓存的整体过期时间什么的，我这里默认没有配置
        log.info("初始化 -> [{}]", "CacheManager RedisCacheManager Start");
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(jedisConnectionFactory);
        return builder.build();
    }

    @Bean
    public org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        //设置序列化
        FastJson2JsonRedisSerializer fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);

        // 配置redisTemplate
        org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate = new org.springframework.data.redis.core.RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        org.springframework.data.redis.serializer.RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer); // key序列化
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer); // value序列化
        redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
        redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer); // Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
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
