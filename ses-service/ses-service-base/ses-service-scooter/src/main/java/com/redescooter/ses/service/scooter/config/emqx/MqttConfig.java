package com.redescooter.ses.service.scooter.config.emqx;

import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * EMQ X配置信息
 * @author assert
 * @date 2020/11/11 15:02
 */
@Data
@Component
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


    /**
     * EMQ X消费者1：处理BBI相关上报数据
     */
    @Bean
    public MqttClientUtil getMqttSubscribeClientOne(){
        MqttClientUtil mqttClientUtil = new MqttClientUtil();
        mqttClientUtil.connect(url, timeout, keepalive, ca, clientCrt, clientKey, new String[]{EmqXTopicConstant.SCOOTER_BBI_REPORTED_TOPIC});
        return mqttClientUtil;
    }

    /**
     * EMQ X消费者2：处理ECU、MCU上报数据
     */
    @Bean
    public MqttClientUtil getMqttSubscribeClientTwo() {
        MqttClientUtil mqttClientUtil = new MqttClientUtil();
        mqttClientUtil.connect(url, timeout, keepalive, ca, clientCrt, clientKey, new String[]{EmqXTopicConstant.SCOOTER_ECU_REPORTED_TOPIC,
                EmqXTopicConstant.SCOOTER_MCU_REPORTED_TOPIC});
        return mqttClientUtil;
    }

    /**
     * EMQ X消费者3：处理车辆开关锁时整车上报数据
     */
    @Bean
    public MqttClientUtil getMqttSubscribeClientThree() {
        MqttClientUtil mqttClientUtil = new MqttClientUtil();
        mqttClientUtil.connect(url, timeout, keepalive, ca, clientCrt, clientKey, new String[]{EmqXTopicConstant.SCOOTER_ALL_REPORTED_TOPIC});
        return mqttClientUtil;
    }

    /**
     * EMQ X消息发送者：用于消息发送使用
     */
    @Bean
    public MqttClientUtil getMqttPushClient() {
        MqttClientUtil mqttClientUtil = new MqttClientUtil();
        mqttClientUtil.connect(url, timeout, keepalive, ca, clientCrt, clientKey, null);
        return mqttClientUtil;
    }

}
