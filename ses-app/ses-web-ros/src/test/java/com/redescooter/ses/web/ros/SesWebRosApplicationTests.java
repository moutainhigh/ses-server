package com.redescooter.ses.web.ros;

import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.web.ros.vo.website.WebEditCustomerEnter;
import com.redescooter.ses.api.common.vo.base.WebResetPasswordEnter;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.encryption.StringEncryptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j
public class SesWebRosApplicationTests {

    @Before
    public void setUp() throws Exception {
        System.out.println("单元测试开始--------------------->");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("单元测试结束--------------------->");
    }

    @Autowired
    private JedisService jedisService;

    @Autowired
    private WebSiteTokenService webSiteService;

    @Test
    public void stream() {
        String test="RedEScooter2019";
        System.out.println(DigestUtils.md5Hex(test+ "40382"));
    }


    @Test
    public void testSendMail(){
        BaseSendMailEnter enter = new BaseSendMailEnter();
        enter.setMail("aleks@redescooter.com");
        enter.setRequestId("123456789qazwsx");
        webSiteService.sendEmail(enter);
    }


    @Test
    public void forgetPassword(){
        WebResetPasswordEnter enter = new WebResetPasswordEnter();
        enter.setNewPassword("123");
        enter.setConfirmPassword("123");
        enter.setRequestId("123456789qazwsx");
        webSiteService.forgetPassword(enter);
    }


    @Test
    public void resetPassword(){
        WebResetPasswordEnter enter = new WebResetPasswordEnter();
        enter.setNewPassword("UoFdxiOnLrvPX2sHOe%2FEbd6nBxta5ym1lS1KDvfAiHZhLT%2BNT1dLSov2czROujJVj%2FAD4vhHRYFLjSe6RvAl9n2KCdIlpBUjkcis1nRwTLnu2FG3wt8yGHyt43PxuUmU0Bqk8sq1q6X60f8sAYt2IAVjF0JNragbrhvNoDxZzAo3Eg8HpMgdzrDYCtSOuq7sLWPtkJ0KPwLthfp6nUJi8UeYw6hB8MklSZ8qb4BkbnQqA07VL1IZHeoJml0ziSRwbq656druCpMcGaZ9hQjTWugvlPFfBv79JjWdo4XlLk29VRmUmhy%2BG%2FHVVDGdoPo7ip4qIs5nDAFGRRRPMZ7ayw%3D%3D");
        enter.setConfirmPassword("Hbr%2F803ozulFPdiLAuuDJuvrleiBlyiXjdNdA7q0B6yrrPDK%2FKw3KtKTwymLHNq5SUItmW6oJ1sySL6Knnr8rxQguvM%2B4rQMHssA8Bha8UbRg%2BkHbMi%2FvVUETKa5hRgq1na5OsMTby4o4OtJuxAtQbAhis2jt3lxDrYybjjSsLstzG7%2BRgLiIksQuIPdlB%2BDathoOuH2OOo2uFbIiyLtHt2Fglm9E5z5rTlSu1jBo0wE08Mvy5qFXdI47AVkt9y6DM5QUdDk2Edpg5CUBpBiPvpCTMi3ZNqWZvC%2BtJ%2BNlrnD66dJJbwzQoFbVErZBlbRXtsarNtNl0ysu9oOEnDrXQ%3D%3D");
        enter.setOldPassword("fmWv3FpZb4uZj1l8DwBr6qlMBmr942Ye3RVvY%2FnBxQFdtml0645dgMp3E8T0uXAevVbmHwxHKKhVV9N%2BMrM7yhLS40SG5MBMl6Oz3qHasAkGfCb4g4gbX6%2FnhRebOGb%2BZ%2FDduxYtbjdP%2BVaSKGj%2Frcwr%2FPQX%2B%2Fji7PWCWj4YAMIT%2B%2BPw2kA2hwfGOQFLLrWHUKKWbWa3UiHD%2Bm1XBD3veXxwvnDhRo2OVravaxP4EP6wdcZqNxtqIZJptgA13AH2BWWamHZWdA9jrg57PK0mOltp5X75M03xM5eIHGRuUL1ZyunizTSW76LcB366sYD9MAmWSQez4LxXQZFz%2BSGfBQ%3D%3D");
        webSiteService.resetPassword(enter);
    }

    @Test
    public void editCustomer(){
        WebEditCustomerEnter enter = new WebEditCustomerEnter();
        enter.setAddress("");
        enter.setFirstName("");
        enter.setLastName("");
        enter.setTelephone("");
        webSiteService.editCustomer(enter);
    }

    @Test
    public void test() {
        // 对应配置文件中配置的加密密钥
//        System.setProperty("jasypt.encryptor.password", "RedE");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
//        System.out.println("加密后的登录名： " + stringEncryptor.encrypt("root"));
//        System.out.println("加密后的密码： " + stringEncryptor.encrypt("1qaz2wsx"));
//        System.out.println("url： " + stringEncryptor.encrypt("jdbc:mysql://localhost:3306/operation?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false&allowPublicKeyRetrieval=true\n"));
////
//
//        String url="+xA0J+wEWz4ZZRqskmzzm7X96KWtE+T+Xbhzg4ai/Ep4VGrri/KtE39cbwwJfBGV/swnJvt1t4d51O5MgV44fZ96gxpWCPg5PJgylytMVX98NcJ+3lUEu0rfIXi43GWvfiuEjtDfadtXZ+5mvMeTZsbw7f/Ko0t7RaP+uf/lW1NWbnmW8MToxyPMT2wgGeMi9uzGnxd3XoaPNKvOstmPB/NdiywUxWIIbCGSpCumB1nFk/vuz+w6fFtrLNXiA+VbogJ6hpmyoaDB+WrIv3MojEkBJNO4rDj02e+Zs93tYoiwT3+aB+sQ/NV7YBA6bgp9eJbTzdwbTYM2Yc9lmi5eRprTf3QPMwFCdf+WdTcqRRs=";
//
//        String userName="PUCVDfaGX5+GHC5sLQUkaw==";
//
//        String password="h4FTPWlTMiyjC3ibuv4QhvreAET9QoNL";
//
//        System.out.println("解密的登录名： " + stringEncryptor.decrypt(userName));
        System.out.println("解密的密码： " + stringEncryptor.decrypt("fHRxmETbcpUpah/tH49sOY+8Fl86wduHyV7en4kfiEsUeC/5OlB1ZwCTv6lPKVPS"));
//        System.out.println("url： " + stringEncryptor.decrypt(url));
    }

    @Test
    public void os(){
        String osName = System.getProperty("os.name");
        System.out.println(osName);
        if (osName.startsWith("Mac OS")) {
            // 苹果
            System.out.println("This is Mac");
        } else if (osName.startsWith("Windows")) {
            // windows
            System.out.println("This is windows");
        } else {
            // unix or linux
            System.out.println("This is unix or linux");
        }
    }

    @Test
    public void stest() {
        String str="sdasda";
//        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(str);
//        System.out.println(m.matches());
//
////        Pattern p1 = Pattern.compile(RegexpConstant.specialCharacters);
////        Matcher m2 = p.matcher(str);
//        System.out.println(Pattern.matches(RegexpConstant.specialCharacters, str));

        String regEx="^((?![`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]).)*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        System.out.println(m.matches());
    }

}
