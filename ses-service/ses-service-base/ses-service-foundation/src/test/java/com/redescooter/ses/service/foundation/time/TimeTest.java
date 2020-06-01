package com.redescooter.ses.service.foundation.time;

import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.tool.utils.DateUtil;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        Date startRenewAccountTime = DateUtil.parse("2020-03-20 16:00:00", DateConstant.DEFAULT_DATETIME_FORMAT);
        Date endRenewAccountTime = DateUtil.parse("2020-03-18 15:59:59", DateConstant.DEFAULT_DATETIME_FORMAT);

        System.out.println(DateUtil.timeComolete(endRenewAccountTime, startRenewAccountTime));
    }
    @Test
    public void test(){

        for (int i = 0; i < 1000; i++) {
            System.out.println(RandomUtils.nextInt(10000,99999));
        }
    }
}
