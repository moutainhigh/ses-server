package com.redescooter.ses.service.scooter.config.emqx;

import com.redescooter.ses.tool.utils.ssl.SslUtil;
import lombok.extern.slf4j.Slf4j;
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
public class MqttClientUtil {

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
     * 建立与EMQ X服务之间的连接
     * @param host 服务地址
     * @param timeout 连接超时时间
     * @param keepalive 心跳时间
     * @param ca 客户端CA文件
     * @param clientCrt 客户端证书
     * @param clientKey 客户端签名
     * @param topics 需要订阅的消息主题
     */
    public void connect(String host, int timeout, int keepalive, String ca, String clientCrt, String clientKey, String[] topics){
        MqttClient client;
        try {
            // clientId随机生成,保证不重复(如果clientId重复会导致将上台客户机挤下线)
            String clientId = System.currentTimeMillis() + "";
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

                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
                        // EMQ X服务断开后会进入这个方法,可以在这里进行连接重试
                        log.error("【EMQ X服务连接已断开】----{}", ExceptionUtils.getStackTrace(cause));
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        /**
                         * scooter摩托车设备信息上报
                         */
                        EmqXThreadPoolExecutorUtil.getThreadPool().execute(() -> {
                            if (null != message.getPayload()) {
                                scooterDataReported.insertScooterData(new String(message.getPayload()), topic);
                            }
                        });
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        // 消息发送成功会进入这个方法
//                        log.info("deliveryComplete---------" + token.isComplete());
                    }
                });

                client.connect(options);

                /**
                 * 订阅消息主题
                 */
                if (null != topics) {
                    this.subscribe(topics);
                }

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

}
