package com.redescooter.ses.service.foundation.time;

import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.tool.utils.date.DateUtil;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:TimeTest
 * @description: TimeTest
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/20 10:05
 */
public class TimeTest {
    @Test
    public void timeComplete() {
        Date startRenewAccountTime = DateUtil.parse("2020-03-20 16:00:00", DateConstant.DEFAULT_DATETIME_FORMAT);
        Date endRenewAccountTime = DateUtil.parse("2020-03-18 15:59:59", DateConstant.DEFAULT_DATETIME_FORMAT);

        System.out.println(DateUtil.timeComolete(endRenewAccountTime, startRenewAccountTime));
    }

    @Test
    public void test() {
        Map<String, String> hashmp = new HashMap();
        hashmp.put("aa", "111");
        System.out.println(hashmp.containsKey("aa"));
    }
}
