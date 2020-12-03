package com.redescooter.ses.service.scooter.config.emqx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * EMQ X配置信息
 * @author assert
 * @date 2020/11/11 15:02
 */
@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {

    /**
     * 超时时间
     */
    private int timeout;
    /**
     * 心跳时间
     */
    private int keepalive;
    /**
     * EMQ X服务地址
     */
    private String url;
    /**
     * EMQ X CA证书
     */
    private String ca;
    /**
     * EMQ X 客户端证书
     */
    private String clientCrt;
    /**
     * EMQ X客户端Key
     */
    private String clientKey;


    @Bean
    public MqttClientUtil getMqttPushClient(){
        MqttClientUtil mqttClientUtil = new MqttClientUtil();
        mqttClientUtil.connect(url, timeout, keepalive, ca, clientCrt, clientKey);
        return mqttClientUtil;
    }

}
