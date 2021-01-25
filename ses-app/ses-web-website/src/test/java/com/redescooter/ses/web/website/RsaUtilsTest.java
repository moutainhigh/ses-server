package com.redescooter.ses.web.website;

import com.redescooter.ses.tool.crypt.RsaUtils;
import com.redescooter.ses.web.website.config.RequestsKeyProperties;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author jerry
 * @Date 2021/1/26 12:44 上午
 * @Description 解密测试
 **/
public class RsaUtilsTest extends SesWebsiteApplicationTests {

    @Autowired
    private RequestsKeyProperties requestsKeyProperties;

    /**
     * 私钥解密
     */
    @Test
    public void decrypt() {
        String email = RsaUtils.decrypt("Y/QA+11C0lPl6SHwx6bn7EesUWAZi5JD+iW+xBqL74tEvjKBiEkNSSpRPu4cglnW36uRrlcK2P1i9JziMXy0rDd3SHzk57/4Io5IGWBKt3dt6jB+yLThBWbDD3aLMrkY8OVxgPXhNLyj/onXcUk+MQE39GEyxyqSykpMY9wV6d8irMMk4U/U65A+yRN1kuukvNCu895nkaVhXKAxwGtjjMyg1Bm6x99SL960Irz17Y/puOsjPWq5DPGt6P1UdmuecYnPP19FJV6IfD5wne9hxHL7Sk6qRvFVU94yvprdkCU6RlbGRplM50AAkcRptBwz2qnXb7uxis+pDBVbgfyONA==", requestsKeyProperties.getPrivateKey());
        System.out.println(email);
    }

    /**
     * 公钥加密
     */
    @SneakyThrows
    @Test
    public void encrypt() {
        String email = RsaUtils.encrypt("jerry@redescooter.com", requestsKeyProperties.getPublicKey());
        System.out.println(email);
        System.out.println("--------------------------------");
        String password = RsaUtils.encrypt("123456", requestsKeyProperties.getPublicKey());
        System.out.println(password);
    }

}
