package com.redescooter.ses.web.website;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author jerry
 * @Date 2021/1/24 11:28 下午
 * @Description 加密测试
 **/

@Slf4j
public class EncryptTests extends SesWebsiteApplicationTests {

    @Autowired
    private StringEncryptor encryptor;

    /**
     * 加密
     *
     * @param string
     * @return
     */
    public String getEncryptResult(String string) {
        String encryptResult = encryptor.encrypt(string);
        log.info("原字符串：{} ,加密后字符串: {}", string, encryptResult);
        return encryptResult;
    }

    /**
     * 解密
     *
     * @param string
     * @return
     */
    public String getDecryptResult(String string) {
        String decryptResult = encryptor.decrypt(string);
        log.info("加密后字符串：{} ,原字符串: {}", string, decryptResult);
        return decryptResult;
    }

    @Test
    public void encryptor() {
        String name = getEncryptResult("root");
        String pwd = getEncryptResult("1qaz2wsx");
        System.out.println(name);
        System.out.println(pwd);

        log.info("==========================================");

        System.out.println(name);
        System.out.println(pwd);
    }
}
