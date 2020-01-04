package com.redescooter.ses.map.demo.http;

import com.redescooter.ses.map.demo.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 4/1/2020 1:14 下午
 * @ClassName: HttpTest
 * @Function: TODO
 */
@Slf4j
public class HttpTest {

    @Test
    public void contextLoads() {

        String url = "http://192.168.2.200/ses/t/delivery/w/api/order/delivery/list";
        try {
            Map<String, String> map = new HashMap<>();
            map.put("CHP", "{\"Content-Type\":\"application/x-www-form-urlencoded\",\"clientType\":\"PC\",\"token\":\"ef4a2a60f43b42dc916a2c40d112290e\",\"requestId\":\"ee6b73c70df5c046b10829d09ca33a9c520b\",\"language\":\"en\",\"timeZone\":\"GMT+0800\",\"version\":\"1.1\",\"appId\":\"1\"}");

            String result = HttpUtils.httpsPost(url, "", map);

            System.out.println("返回值>>>：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHttpTest() {

    }

}
