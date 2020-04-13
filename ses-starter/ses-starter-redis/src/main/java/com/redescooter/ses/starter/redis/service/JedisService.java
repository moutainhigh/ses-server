package com.redescooter.ses.starter.redis.service;
import java.util.List;
import java.util.Map;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/12/2019 1:10 上午
 * @ClassName: JedisService
 * @Function: TODO
 */
public interface JedisService {

    /**
     * @Description: 是否存在
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    boolean exists(String key);

    /**
     * @Description:缓存set值
     * @Param:  seconds:缓存时间，不设置则为0
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    String set(String key,String value,int seconds);

    /**
     * @Description:  重新缓存getSet值
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    String getSet(String key,String value, int seconds);

    /**
     * @Description: 获取set值
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    String get(String key);

    /**
     * @Description: 添加地理位置
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    Long geoadd(String key,double longitude,double latitude,byte[] obj);

    /**
     * @Description: 地理位置查询
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,  GeoUnit unit);

    /**
     * @Description: 删除key
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    void delKey(String key);

    /**
     * @Description: 删除native key
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    void delNativeKey(String key);

    /**
     * @Description: 获取map格式的数据
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    Map<String ,Object> getMapData(String key);

    /**
     * @Description: 加锁，避免重复提交
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    boolean lock(String key,int seconds);

    /**
     * @Description: 解锁
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    void unlock(String key);

    /**
     * @Description: 统计锁定次数
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/5/16
     */
    String getLocakValue(String key);
}

