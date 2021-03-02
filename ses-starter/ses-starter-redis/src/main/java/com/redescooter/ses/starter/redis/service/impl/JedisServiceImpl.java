package com.redescooter.ses.starter.redis.service.impl;

import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.Map;

import com.redescooter.ses.starter.redis.service.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.JedisCluster;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/12/2019 1:11 上午
 * @ClassName: JedisServiceImpl
 * @Function: TODO
 */
@Service
public class JedisServiceImpl implements JedisService {


    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public boolean exists(String key) {
        boolean flag = false;
        flag = jedisCluster.exists(key);
        return flag;
    }

    @Override
    public String set(String key, String value, int seconds) {
        String responseResult = jedisCluster.set(key, value);
        if (seconds != 0) {
            jedisCluster.expire(key, seconds);
        }
        return responseResult;
    }

    @Override
    public String getSet(String key, String value, int seconds) {
        String jedisClusterSet = jedisCluster.getSet(key, value);
        jedisCluster.expire(key, seconds);
        return jedisClusterSet;
    }

    @Override
    public String get(String key) {
        String str = jedisCluster.get(key);
        return str;
    }

    @Override
    public Long geoadd(String key, double longitude, double latitude, byte[] obj) {
        return jedisCluster.geoadd(key, longitude, latitude, String.valueOf(obj));
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return jedisCluster.georadius(key, longitude, latitude, radius, unit);
    }

    @Override
    public void delKey(String key) {
        jedisCluster.del(key);
    }

    @Override
    public void delNativeKey(String key) {
        jedisCluster.del(key);
    }

    @Override
    public Map<String, Object> getMapData(String key) {
        String str = jedisCluster.get(key);
        Map<String, Object> map = JSON.parseObject(str, Map.class);
        return map;
    }

    /**
     * 如为第一次，则加上锁，每次调用值会自动加1
     *
     * @param key
     * @param seconds
     * @return
     */
    @Override
    public boolean lock(String key, int seconds) {
        if (jedisCluster.incr(key) == 1) {
            jedisCluster.expire(key, seconds);
            return false;
        }
        return true;
    }

    @Override
    public void unlock(String key) {
        jedisCluster.del(key);
    }

    @Override
    public String getLocakValue(String key) {
        return jedisCluster.get(key);
    }

}

