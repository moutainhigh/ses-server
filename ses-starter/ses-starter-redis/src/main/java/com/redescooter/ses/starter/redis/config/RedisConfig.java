//package com.redescooter.ses.starter.redis.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.*;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author Mr.lijiating
// * @version V1.0
// * @Date: 23/12/2019 6:51 上午
// * @ClassName: RedisConfig
// * @Function: TODO
// */
//
//@Data
//@EnableCaching //开启注解
//@Configuration
//@ConfigurationProperties(prefix = "redis", ignoreUnknownFields = true)
//public class RedisConfig extends CachingConfigurerSupport {
//
//    private int maxTotal;
//    private int maxIdle;
//    private int minIdle;
//    private int timeout;
//    private int maxAttempts;
//    private String redisAddress;
//
//    @Bean
//    public JedisPoolConfig initJedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        return jedisPoolConfig;
//    }
//
////
////    @Bean
////    public JedisCluster initJedisCluster(JedisPoolConfig jedisPoolConfig) {
////        String[] split = redisAddress.split(";");
////        Set<HostAndPort> jedisClusterNode = new HashSet<>();
////        for (String address : split) {
////            String[] node = address.split(":");
////            HostAndPort hap = new HostAndPort(node[0], Integer.parseInt(node[1]));
////            jedisClusterNode.add(hap);
////        }
////        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, timeout, maxAttempts, jedisPoolConfig);
////        return jedisCluster;
////    }
////
////    /**
////     * retemplate相关配置
////     * @param factory
////     * @return
////     */
////    @Bean
////    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
////
////        RedisTemplate<String, Object> template = new RedisTemplate<>();
////        // 配置连接工厂
////        template.setConnectionFactory(factory);
////
////        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
////        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
////
////        ObjectMapper om = new ObjectMapper();
////        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
////        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
////        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
////        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////        jacksonSeial.setObjectMapper(om);
////
////        // 值采用json序列化
////        template.setValueSerializer(jacksonSeial);
////        //使用StringRedisSerializer来序列化和反序列化redis的key值
////        template.setKeySerializer(new StringRedisSerializer());
////
////        // 设置hash key 和value序列化模式
////        template.setHashKeySerializer(new StringRedisSerializer());
////        template.setHashValueSerializer(jacksonSeial);
////        template.afterPropertiesSet();
////
////        return template;
////    }
////
////    /**
////     * 对hash类型的数据操作
////     *
////     * @param redisTemplate
////     * @return
////     */
////    @Bean
////    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
////        return redisTemplate.opsForHash();
////    }
////
////    /**
////     * 对redis字符串类型数据操作
////     *
////     * @param redisTemplate
////     * @return
////     */
////    @Bean
////    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
////        return redisTemplate.opsForValue();
////    }
////
////    /**
////     * 对链表类型的数据操作
////     *
////     * @param redisTemplate
////     * @return
////     */
////    @Bean
////    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
////        return redisTemplate.opsForList();
////    }
////
////    /**
////     * 对无序集合类型的数据操作
////     *
////     * @param redisTemplate
////     * @return
////     */
////    @Bean
////    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
////        return redisTemplate.opsForSet();
////    }
////
////    /**
////     * 对有序集合类型的数据操作
////     *
////     * @param redisTemplate
////     * @return
////     */
////    @Bean
////    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
////        return redisTemplate.opsForZSet();
////    }
//
//}
