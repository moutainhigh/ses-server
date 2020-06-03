package com.redescooter.ses.web.ros;

import com.redescooter.ses.api.common.constant.JedisConstant;
import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.WebEditCustomerEnter;
import com.redescooter.ses.api.common.vo.base.WebResetPasswordEnter;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

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
        enter.setNewPassword("123");
        enter.setConfirmPassword("123");
        enter.setOldPassword("123");
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
        System.setProperty("jasypt.encryptor.password", "RedE");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        System.out.println("加密后的登录名： " + stringEncryptor.encrypt("root"));
        System.out.println("加密后的密码： " + stringEncryptor.encrypt("1qaz2wsx"));
        System.out.println("url： " + stringEncryptor.encrypt("jdbc:mysql://192.168.2.200:3306/operation_test?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false&allowPublicKeyRetrieval=true"));
        System.out.println("解密的登录名： " + stringEncryptor.decrypt("V1yrV6NRTPV7Qiuik4Ngsg=="));
        System.out.println("解密的密码： " + stringEncryptor.decrypt("h4FTPWlTMiyjC3ibuv4QhvreAET9QoNL"));
        System.out.println("url： " + stringEncryptor.decrypt("2M1l/PCpkVqJEEnbVQTysqYLoiQHHARchswPpnoDRyXq3Metj7RNPGmvDH1pzgXOrUzNrjppkQf0eBAQkDOkwlBtVIrkkvvM+bwr1fsu31ax7Trr8TAjyaDuD1eS5piFJWwTWlf4ziuKEt+mNZkbOy8bcqnSlVaP11WYtcRxS7+PgeTfDEfrBSiyo7NBmqYUe2XAtqumXe6M8oc9RyhMSaSB+vIn47LTq0/PfSFQ9y2wt/HEGudRZ8WO234DetSt+h6iHtSM1XSf5VaXG/vwaCUUEXMSblQqx2NuoMvgjHMvedQ1YolMBYPTx2xqTKy8GVahohR1FSAQQ58kLkce14i+iLOgnnrHwsyQSwzKWNg="));
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


}
