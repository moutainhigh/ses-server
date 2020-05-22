package com.redescooter.ses.web.ros.service.stripe.impl;

import com.redescooter.ses.api.common.enums.base.AppIDEnums;
import com.redescooter.ses.api.common.enums.base.SystemIDEnums;
import com.redescooter.ses.api.common.enums.proxy.mail.MailTemplateEventEnums;
import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.common.vo.base.StringResult;
import com.redescooter.ses.api.foundation.service.MailMultiTaskService;
import com.redescooter.ses.web.ros.SesWebRosApplicationTests;
import com.redescooter.ses.web.ros.service.stripe.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class StripeServiceImplTest extends SesWebRosApplicationTests {

    @Value("${stripe.secret_key}")
    private String API_SECRET_KEY;

    @Autowired
    private StripeService stripeService;
    @Reference
    private MailMultiTaskService mailMultiTaskService;
    /**
     * 支付授权单元测试
     */
    @Test
    public void testPaymentIntent() {

        StringResult result = stripeService.paymentIntent(new IdEnter(1004221l));

        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("result===={}", result.getValue());
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


    }
  @Test
  public void sendmail(){
      String eamil="joan@redescooter.com";
    String name = eamil.substring(0, eamil.indexOf("@"));
    BaseMailTaskEnter enter = new BaseMailTaskEnter();
    enter.setName(name);
    enter.setEvent(MailTemplateEventEnums.SUBSCRIPTION_PAY_SUCCEED_SEND_EAMIL.getName());
    enter.setMailAppId(AppIDEnums.SES_ROS.getSystemId());
    enter.setMailSystemId(SystemIDEnums.REDE_SES.getValue());
    enter.setToMail(eamil);
    enter.setCode("0");
    enter.setRequestId("0");
    enter.setUserRequestId("0");
    enter.setToUserId(0L);
    enter.setUserId(0L);
    mailMultiTaskService.subscriptionPaySucceedSendmail(enter);

  }

/*
  */
/**
   * post请求（用于请求json格式的参数）
   * @param url
   * @param params
   * @return
   *//*

  public static String doPost(String url, String params) throws Exception {

    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(url);// 创建httpPost
    httpPost.setHeader("Accept", "application/json");
    httpPost.setHeader("Content-Type", "application/json");
    String charSet = "UTF-8";
    StringEntity entity = new StringEntity(params, charSet);
    httpPost.setEntity(entity);
    CloseableHttpResponse response = null;

    try {

      response = httpclient.execute(httpPost);
      StatusLine status = response.getStatusLine();
      int state = status.getStatusCode();
      if (state == HttpStatus.SC_OK) {
        HttpEntity responseEntity = response.getEntity();
        String jsonString = EntityUtils.toString(responseEntity);
        return jsonString;
      }
      else{
        log.error("请求返回:"+state+"("+url+")");
      }
    }
    finally {
      if (response != null) {
        try {
          response.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      try {
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
*/


}
