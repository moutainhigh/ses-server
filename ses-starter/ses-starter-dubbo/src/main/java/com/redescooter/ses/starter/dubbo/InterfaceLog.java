package com.redescooter.ses.starter.dubbo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceLog implements Serializable {
    private String invokeTime = String.valueOf(System.currentTimeMillis());
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 调用方法名
     */
    private String methodName;
    /**
     * 来源方
     */
    private String senderName;
    /**
     * 来源地址
     */
    private String senderHost;
    /**
     * 接收方
     */
    private String receiverName;

    /**
     * 接收地址
     */
    private String receiverHost;
    /**
     * 服务组
     */
    private String srvGroup;
    /**
     * 服务版本
     */
    private String version;
    /**
     * 参数类型
     */
    private String[] paramTypes;
    /**
     * 参数值
     */
    private Object[] paramValues;
    /**
     * 返回
     */
    private Object resultValue;
    /**
     * 接口服务调用花费时间
     */
    private long costTime = 0L;
    /**
     * 异常信息
     */
    private String exceptionMsg;
    /**
     * 异常类信息
     */
    private String throwableClass;
    /**
     * 异常栈信息
     */
    private String stackTraces;

    @Override
    public String toString() {
        String slfStr;
        try {
            slfStr = JSON.toJSONString(this,SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            slfStr = toSimpleString();
        }
        return slfStr;
    }

    public String toSimpleString() {
        return String.format("{\"invokeTime\": \"%s\" , \"methodName\": \"%s\", \"senderName\": \"%s\", \"senderHost\": \"%s\", \"receiverName\": \"%s\", \"receiverHost\": \"%s\", \"srvGroup\": \"%s\", \"version\": \"%s\", \"paramTypes\": [], \"paramValues\": [], \"resultValue\": {}, \"costTime\": %s, \"throwableClass\": \"%s\", \"exceptionMsg\": \"%s\", \"stackTraces\": \"%s\"}", this.invokeTime, this.methodName, this.senderName, this.senderHost, this.receiverName, this.receiverHost, this.srvGroup, this.version, this.costTime, this.throwableClass, this.exceptionMsg, this.stackTraces);
    }

}