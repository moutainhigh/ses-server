package com.redescooter.ses.starter.redis;

import redis.clients.jedis.JedisCluster;

import java.util.Random;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 17/12/2019 11:03 上午
 * @ClassName: RedisLock
 * @Function: TODO
 */
public class RedisLock {

    /**
     * 加锁标志
     */
    public static final String LOCKED = "TRUE";
    /**
     * 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒
     */
    public static final long MILLI_NANO_CONVERSION = 1000 * 1000L;
    /**
     * 默认超时时间（毫秒）
     */
    public static final long DEFAULT_TIME_OUT = 1000;

    public static final Random RANDOM = new Random();
    /**
     * 锁的超时时间（秒），过期删除
     */
    public static final int EXPIRE = 3 * 60;

    private static JedisCluster jedisCluster;

    private static RedisLock instance = new RedisLock();

    public static RedisLock getInstance(JedisCluster jedisCluster) {
        RedisLock.jedisCluster = jedisCluster;
        return instance;
    }

    public RedisLock() {
    }

    /**
     * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
     *
     * @param lockKey
     * @param timeout 超时时间
     * @return 成功或失败标志
     */
    public boolean lock(String lockKey, long timeout) {
        return this.lock(lockKey, timeout, EXPIRE);
    }

    /**
     * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
     *
     * @param lockKey
     * @param timeout 超时时间
     * @param expire  锁的超时时间（秒），过期删除
     * @return 成功或失败标志
     */
    public boolean lock(String lockKey, long timeout, int expire) {
        long nano = System.nanoTime();
        timeout *= MILLI_NANO_CONVERSION;
        try {
            while ((System.nanoTime() - nano) < timeout) {
                if (jedisCluster.setnx(lockKey, LOCKED) == 1) {
                    jedisCluster.expire(lockKey, expire);
                    return true;
                }
                Thread.sleep(3, RANDOM.nextInt(500));
            }
            long ttl = jedisCluster.ttl(lockKey);
            if (ttl == -1) {
                this.unlock(lockKey);
                if (jedisCluster.setnx(lockKey, LOCKED) == 1) {
                    jedisCluster.expire(lockKey, EXPIRE);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Locking error", e);
        }
        return false;
    }

    /**
     * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
     *
     * @return 成功或失败标志
     */
    public boolean lock(String lockKey) {
        return lock(lockKey, DEFAULT_TIME_OUT);
    }

    /**
     * 解锁 无论是否加锁成功，都需要调用unlock 应该以： lock(); try { doSomething(); } finally {
     * unlock()； } 的方式调用
     */
    public void unlock(String lockKey) {
        jedisCluster.del(lockKey);
    }
}
