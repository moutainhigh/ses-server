package com.redescooter.ses.web.ros;

import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.WebResetPasswordEnter;
import com.redescooter.ses.starter.redis.service.JedisService;
import com.redescooter.ses.tool.utils.DateUtil;
import com.redescooter.ses.web.ros.dm.OpeCustomerInquiry;
import com.redescooter.ses.web.ros.service.base.OpeCustomerInquiryService;
import com.redescooter.ses.web.ros.service.monday.MondayService;
import com.redescooter.ses.web.ros.service.website.WebSiteTokenService;
import com.redescooter.ses.web.ros.vo.monday.MondayCreateResult;
import com.redescooter.ses.web.ros.vo.website.WebEditCustomerEnter;
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

import java.text.SimpleDateFormat;
import java.util.Date;
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
        String test = "RedEScooter2019";
        System.out.println(DigestUtils.md5Hex(test + "40382"));
    }


    @Test
    public void testSendMail() {
        BaseSendMailEnter enter = new BaseSendMailEnter();
        enter.setMail("aleks@redescooter.com");
        enter.setRequestId("123456789qazwsx");
        webSiteService.sendEmail(enter);
    }


    @Test
    public void forgetPassword() {
        WebResetPasswordEnter enter = new WebResetPasswordEnter();
        enter.setNewPassword("123");
        enter.setConfirmPassword("123");
        enter.setRequestId("123456789qazwsx");
        webSiteService.forgetPassword(enter);
    }


    @Test
    public void resetPassword() {
        WebResetPasswordEnter enter = new WebResetPasswordEnter();
        enter.setNewPassword("UoFdxiOnLrvPX2sHOe%2FEbd6nBxta5ym1lS1KDvfAiHZhLT%2BNT1dLSov2czROujJVj" +
                "%2FAD4vhHRYFLjSe6RvAl9n2KCdIlpBUjkcis1nRwTLnu2FG3wt8yGHyt43PxuUmU0Bqk8sq1q6X60f8sAYt2IAVjF0JNragbrhvNoDxZzAo3Eg8HpMgdzrDYCtSOuq7sLWPtkJ0KPwLthfp6nUJi8UeYw6hB8MklSZ8qb4BkbnQqA07VL1IZHeoJml0ziSRwbq656druCpMcGaZ9hQjTWugvlPFfBv79JjWdo4XlLk29VRmUmhy%2BG%2FHVVDGdoPo7ip4qIs5nDAFGRRRPMZ7ayw%3D%3D");
        enter.setConfirmPassword("Hbr%2F803ozulFPdiLAuuDJuvrleiBlyiXjdNdA7q0B6yrrPDK%2FKw3KtKTwymLHNq5SUItmW6oJ1sySL6Knnr8rxQguvM%2B4rQMHssA8Bha8UbRg%2BkHbMi" +
                "%2FvVUETKa5hRgq1na5OsMTby4o4OtJuxAtQbAhis2jt3lxDrYybjjSsLstzG7%2BRgLiIksQuIPdlB" +
                "%2BDathoOuH2OOo2uFbIiyLtHt2Fglm9E5z5rTlSu1jBo0wE08Mvy5qFXdI47AVkt9y6DM5QUdDk2Edpg5CUBpBiPvpCTMi3ZNqWZvC%2BtJ%2BNlrnD66dJJbwzQoFbVErZBlbRXtsarNtNl0ysu9oOEnDrXQ%3D%3D");
        enter.setOldPassword("fmWv3FpZb4uZj1l8DwBr6qlMBmr942Ye3RVvY%2FnBxQFdtml0645dgMp3E8T0uXAevVbmHwxHKKhVV9N%2BMrM7yhLS40SG5MBMl6Oz3qHasAkGfCb4g4gbX6%2FnhRebOGb%2BZ%2FDduxYtbjdP%2BVaSKGj%2Frcwr" +
                "%2FPQX%2B%2Fji7PWCWj4YAMIT%2B%2BPw2kA2hwfGOQFLLrWHUKKWbWa3UiHD" +
                "%2Bm1XBD3veXxwvnDhRo2OVravaxP4EP6wdcZqNxtqIZJptgA13AH2BWWamHZWdA9jrg57PK0mOltp5X75M03xM5eIHGRuUL1ZyunizTSW76LcB366sYD9MAmWSQez4LxXQZFz%2BSGfBQ%3D%3D");
        webSiteService.resetPassword(enter);
    }

    @Test
    public void editCustomer() {
        WebEditCustomerEnter enter = new WebEditCustomerEnter();
        enter.setAddress("");
        enter.setFirstName("");
        enter.setLastName("");
        webSiteService.editCustomer(enter);
    }

    @Test
    public void test() {
        // 对应配置文件中配置的加密密钥
        System.setProperty("jasypt.encryptor.password", "RedE");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
//        System.out.println("加密后的登录名： " + stringEncryptor.encrypt("root"));
//        System.out.println("加密后的密码： " + stringEncryptor.encrypt("1qaz2wsx"));
        System.out.println("consumer： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/consumer_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        System.out.println("scooter： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/scooter_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        System.out.println("platform： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/platform_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        System.out.println("corporate： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/corporate_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        System.out.println("operation： " + stringEncryptor.encrypt(
                "jdbc:mysql://172.31.5.73:4269/operation_prod?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull" +
                        "&serverTimezone=UTC&useLegacyDatetimeCode=false&useSSL=false"));

        ////
//
//        String url="+chN0fRMdoeLUWSKhrK2wbRtCS9HtHdgHojKHlPjwRbbf/NC9cLGUJGNbS8KTloYlKi43+n79TiDla0aWszCT
//        +pDuCXOcFQy3ycbBJXN4wqwDF5HVL1PObebjlCR29Q7bUW10W7cFdrrjhZN90u0zEtEYh1qzD7rW1rw8uUnFSuF69w9M3et6tHJYU2MpOS/jl/1u6e
//        /d3uhRRp225KLSZMS6PrR3EnsNfW6HQYByeVM3eZLZlBDUkebkhxKcU8tV1Qz4xSJbl9rNA1jqwFAIxhsFFI+uuLfiEe4u0/5DDewWR1NtvzOTEKKKlM1f9VyMDwwECar1IbYORA72Yik0Q==";
////
//        String userName="vWqurNhz5ZyGRrJm4tjVnA==";
////
//        String password="Q/B0gpzvU8K2HB+l+DVlQn1L/n3F2RZ8gSpIZT4CkvqqOg9GfxXs+Ouj4IJSNXrf";
////
//        System.out.println("解密的登录名： " + stringEncryptor.decrypt(userName));
//        System.out.println("解密的密码： " + stringEncryptor.decrypt(password));
//        System.out.println("url： " + stringEncryptor.decrypt(url));
    }

    @Test
    public void os() {
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
        String str = "sdasda";
//        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(str);
//        System.out.println(m.matches());
//
////        Pattern p1 = Pattern.compile(RegexpConstant.specialCharacters);
////        Matcher m2 = p.matcher(str);
//        System.out.println(Pattern.matches(RegexpConstant.specialCharacters, str));

        String regEx = "^((?![`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]).)*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        System.out.println(m.matches());
    }


    @Autowired
    private MondayService mondayService;

    @Autowired
    private OpeCustomerInquiryService opeCustomerInquiryService;

    @Test
    public void sendRequestByRestTemplateGet() {

        OpeCustomerInquiry opeCustomerInquiry = opeCustomerInquiryService.getById(1023406L);
        if (opeCustomerInquiry == null) {
            System.out.println("------------------------------------------------询价单不存在-----------------");
        }

        MondayCreateResult mondayCreateResult = mondayService.websiteBookOrder(opeCustomerInquiry);

        System.out.println(mondayCreateResult.getId()+"--------------------------------插入成功--------------------");

    }

    @Test
    public void dateTest(){
        Date date= new Date();
        SimpleDateFormat sdf= new SimpleDateFormat(DateUtil.DEFAULT_TIME_FORMAT);
        String datestr=sdf.format(date);// format  为格式化方法
        System.out.println(datestr);
    }

}
