package com.redescooter.ses.api.common.constant;

/**
 * @ClassName:JedisConstant
 * @description: JedisConstant
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/23 14:09
 */
public interface JedisConstant {

    //jedis 枷锁 key值
    String JEDIS_KEY = "edTaskOrder";

    //默认超时时间为1s
    int DEFAULT_EXPIRE = 1;
}
