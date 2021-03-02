package com.redescooter.ses.service.scooter.config.emqx;

import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * EMQ X相关定时任务
 * @author assert
 * @date 2020/12/22 13:59
 */
@Slf4j
@Component
public class EmqXScheduledJob {

    @Resource
    private MqttClientUtil mqttClientUtil;

    /**
     * EMQ X定时检测断开重连
     */
//    @Scheduled(cron = "0 */1 * * * ?")
//    public void reConnection() {
//        MqttClient client = MqttClientUtil.getClient();
//        if (!client.isConnected()) {
//            try {
//                client.connect();
//                // 重新订阅消息主题
//                mqttClientUtil.subscribe(new String[]{EmqXTopicConstant.SCOOTER_ECU_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_BBI_REPORTED_TOPIC,
//                        EmqXTopicConstant.SCOOTER_BMS_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_MCU_REPORTED_TOPIC,
//                        EmqXTopicConstant.SCOOTER_ALL_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_LOCK_REPORTED_TOPIC});
//            } catch (Exception e) {
//                log.error("【EMQ X服务连接失败】----{}", ExceptionUtils.getStackTrace(e));
//            }
//
//        }
//    }

}
