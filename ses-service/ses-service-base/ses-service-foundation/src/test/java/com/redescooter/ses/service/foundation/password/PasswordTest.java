package com.redescooter.ses.service.foundation.password;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 30/12/2019 10:30 下午
 * @ClassName: PasswordTest
 * @Function: TODO
 */
public class PasswordTest {

    @Test
    public void test01() {
        String s = DigestUtils.md5Hex("12345678" + "94400");

        System.out.println(s);
    }
}
