package com.redescooter.ses.service.scooter.config.emqx;

import com.redescooter.ses.starter.emqx.constants.EmqXTopicConstant;
import com.redescooter.ses.tool.utils.ssl.SslUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * MQTT客户端工具类 -- 消息订阅/发布
 * @author assert
 * @date 2020/11/18 10:38
 */
@Slf4j
@Component
public class MqttClientUtil implements MqttCallback {

    @Resource
    private ScooterDataReportedComponent scooterDataReported;

    private static MqttClient client;

    public static MqttClient getClient() {
        return client;
    }

    public static void setClient(MqttClient client) {
        MqttClientUtil.client = client;
    }

    /**
     * 建立连接
     * @param host
     * @param timeout
     * @param keepalive
     */
    public void connect(String host, int timeout, int keepalive, String ca, String clientCrt, String clientKey){
        MqttClient client;
        try {
            String clientId = "mqttx_" + RandomUtils.nextInt(0, 999999);
            client = new MqttClient(host, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setConnectionTimeout(timeout);
            options.setKeepAliveInterval(keepalive);
            // 是否自动重连 true/false
            options.setAutomaticReconnect(true);
            MqttClientUtil.setClient(client);
            try {
                // 通过ssl证书方式连接
                options.setSocketFactory(SslUtil.getSocketFactory(ca, clientCrt, clientKey,""));
                client.setCallback(this);
                client.connect(options);

                /**
                 * EMQ X初始化连接时需要订阅平板端上报的主题Topic
                 */
                this.subscribe(new String[]{EmqXTopicConstant.SCOOTER_ECU_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_BBI_REPORTED_TOPIC,
                        EmqXTopicConstant.SCOOTER_BMS_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_MCU_REPORTED_TOPIC,
                        EmqXTopicConstant.SCOOTER_ALL_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_LOCK_REPORTED_TOPIC});
            } catch (Exception e) {
                log.error("【EMQ X服务连接失败】----{}", ExceptionUtils.getStackTrace(e));
            }
        } catch (Exception e) {
            log.error("【EMQ X服务连接失败】----{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 消息发布，默认qos为0，非持久化
     * @param topic
     * @param pushMessage
     */
    public void publish(String topic,String pushMessage){
        publish(1, false, topic, pushMessage);
    }

    /**
     * 消息发布
     * @param qos 服务质量 1-非持久化,只会发送一次消息 2-至少会发送一条消息 3-准确一次送达
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos,boolean retained,String topic,String pushMessage){
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mTopic = MqttClientUtil.getClient().getTopic(topic);
        if(null == mTopic){
            log.info("topic not exist");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题，qos默认为0
     * @param topic
     */
    public void subscribe(String topic){
        subscribe(topic,0);
    }

    /**
     * 订阅某个主题
     * @param topic
     * @param qos
     */
    public void subscribe(String topic,int qos){
        try {
            MqttClientUtil.getClient().subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅多个主题
     * @param topics
     */
    public void subscribe(String[] topics){
        try {
            // 设置订阅消息的qos
            int[] qos = new int[topics.length];
            for (int i = 0; i < qos.length; i++) {
                qos[i] = 1;
            }

            MqttClientUtil.getClient().subscribe(topics, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量取消主题订阅
     * @param topics
     */
    public void unSubscribe(String[] topics) {
        try {
            MqttClientUtil.getClient().unsubscribe(topics);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * MqttCallback接口实现方法
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.error("【EMQ X服务连接已断开】----{}", ExceptionUtils.getStackTrace(cause));

        /**
         * EMQ X断开重连逻辑,暂时不开启,可以用于客户端被挤下线重连等....
         */
//        try {
//            log.info("【MQTT服务】---连接断开,正在重连....");
//            MqttClientUtil.getClient().connect();
//            log.info("【MQTT服务】---重连成功");
//            // 重连后重新订阅主题
//            MqttClientUtil.getClient().subscribe(new String[]{EmqXTopicConstant.SCOOTER_ECU_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_BBI_REPORTED_TOPIC,
//                    EmqXTopicConstant.SCOOTER_BMS_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_MCU_REPORTED_TOPIC,
//                    EmqXTopicConstant.SCOOTER_ALL_REPORTED_TOPIC, EmqXTopicConstant.SCOOTER_LOCK_REPORTED_TOPIC});
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (null != message.getPayload()) {
            /**
             * scooter摩托车设备信息上报
             */
            scooterDataReported.insertScooterData(new String(message.getPayload()), topic);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
//        log.info("deliveryComplete---------" + token.isComplete());
    }

}
