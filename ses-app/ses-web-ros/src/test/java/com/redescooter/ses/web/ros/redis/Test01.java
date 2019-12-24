package com.redescooter.ses.web.ros.redis;

import com.redescooter.ses.starter.redis.constants.Status;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/12/2019 11:35 上午
 * @ClassName: Test01
 * @Function: TODO
 */
public class Test01 {

    @Test
    public void redis() {
        Long time = Status.ExpireEnum.LOGIN_TOKE.getTimeUnit().toMinutes(Status.ExpireEnum.LOGIN_TOKE.getTime());
        Long time31 = Status.ExpireEnum.UNREAD_MSG.getTimeUnit().toSeconds(Status.ExpireEnum.UNREAD_MSG.getTime());
        Long time30 = TimeUnit.SECONDS.toMinutes(Status.ExpireEnum.UNREAD_MSG.getTime());
        System.out.println(time);
        System.out.println(time31);
        System.out.println(time30);
    }
}
