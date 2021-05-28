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
        String email = RsaUtils.decrypt("LUcrNXNrBOUDIxCcw4FM+RzkG69rDdNcxLll5ssVUEoD4GBKlauS/3f7RsEAHO3Z/EI6YwBWNXnVDjBOUBEqaeJh5Ff6boracW9dVPmPHmbGSt5NRHa7E7cC0b6fZYRbdt2HqQqLs45JRHEfs0RjrRBaozJpWfepVBeP9FJHxpgSKzMS44bxzuyC29EOQNIxkk+HNPkqUf3o0OU9OMQcOCXHg3wJQ3bTNd5Urt5GZj8Lr3HkgzdkrIYvQy1LYpMwIGjuBAzcYJNgcQMUCM9IlcBg84+9BUxzv5YpaagEdMM+xFoxyWPHGD1RnRlQ+5E88ih0Nub7fXMjthMg4rQ5Lw==", requestsKeyProperties.getPrivateKey());
        System.out.println(email+"{?????????????????????????????????}");
    }

    /**
     * 公钥加密
     */
    @SneakyThrows
    @Test
    public void encrypt() {
        String email = RsaUtils.encrypt("jennie@redescooter.com", requestsKeyProperties.getPublicKey());
        System.out.println(email);
        System.out.println("---------dsfdfdfdfd-----------------------");
        String password = RsaUtils.encrypt("13698653260", requestsKeyProperties.getPublicKey());
        System.out.println(password+"{<>>>>>>><><><><><LKJKJKJKM}");
        System.out.println("--------------------------------");
        String password1 = RsaUtils.encrypt("123456789", requestsKeyProperties.getPublicKey());
        System.out.println(password1+"{>>>>>>>>>>>>>>>>>>>>>>>>>>>>}");
    }

}
