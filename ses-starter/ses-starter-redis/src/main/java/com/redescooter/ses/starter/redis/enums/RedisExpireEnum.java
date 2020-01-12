package com.redescooter.ses.starter.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sun.applet.resources.MsgAppletViewer_sv;

import javax.validation.constraints.Min;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 7:16 上午
 * @ClassName: RedisExpireEnum
 * @Function: TODO redis key有效期
 */
@Getter
@AllArgsConstructor
public enum RedisExpireEnum {
    MONTH_1("MONTH_1", TimeUnit.SECONDS.convert(30L, TimeUnit.DAYS), "30天"),
    DAY_1("DAY_1", TimeUnit.SECONDS.convert(1L, TimeUnit.DAYS), "1天"),
    DAY_3("DAY_3", TimeUnit.SECONDS.convert(3L, TimeUnit.DAYS), "3天"),
    WEEK_1("WEEK_1", TimeUnit.SECONDS.convert(7L, TimeUnit.DAYS), "一周"),
    HOURS_24("HOURS_24", TimeUnit.SECONDS.convert(24L, TimeUnit.HOURS), "24小时"),
    MINUTES_60("MINUTES_60", TimeUnit.SECONDS.convert(1L, TimeUnit.HOURS), "1小时"),
    MINUTES_30("MINUTES_30", TimeUnit.SECONDS.convert(30L, TimeUnit.MINUTES), "30分钟"),
    MINUTES_15("MINUTES_15", TimeUnit.SECONDS.convert(15L, TimeUnit.MINUTES), "15分钟"),
    MINUTES_5("MINUTES_5", TimeUnit.SECONDS.convert(5L, TimeUnit.MINUTES), "5分钟"),
    MINUTES_3("MINUTES_3", TimeUnit.SECONDS.convert(3L, TimeUnit.MINUTES), "3分钟"),
    MINUTES_1("MINUTES_1", TimeUnit.SECONDS.convert(1L, TimeUnit.MINUTES), "1分钟"),
    ;

    /**
     * 过期时间
     */
    private String time;
    /**
     * 基本单位，秒
     */
    private long seconds;
    /**
     * 说明
     */
    private String message;

    public static long getSeconds(String time) {
        for (RedisExpireEnum item : RedisExpireEnum.values()) {
            if (item.getTime().equals(time)) {
                return item.getSeconds();
            }
        }
        return Long.parseLong("0");
    }
}
