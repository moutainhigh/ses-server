package com.redescooter.ses.service.foundation.time;

import com.redescooter.ses.tool.utils.DateUtil;
import org.junit.Test;

import java.util.Date;

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
        Date startRenewAccountTime = DateUtil.parse("2020-03-20 16:00:00", DateUtil.DEFAULT_DATETIME_FORMAT);
        Date endRenewAccountTime = DateUtil.parse("2020-03-18 15:59:59", DateUtil.DEFAULT_DATETIME_FORMAT);

        System.out.println(DateUtil.timeComolete(endRenewAccountTime, startRenewAccountTime));
    }
}
