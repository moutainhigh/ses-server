package com.redescooter.ses.service.proxy.config;


import cn.jpush.api.JPushClient;

import javax.annotation.PostConstruct;

/**
 * description: JPushConfig
 * author: jerry.li
 * create: 2019-05-20 10:18
 */

public class JPushConfig {

    private String appkey;
    private String secret;
    private boolean apns;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isApns() {
        return apns;
    }

    public void setApns(boolean apns) {
        this.apns = apns;
    }

    private JPushClient jPushClient;


    /**
     * 推送客户端
     *
     * @return
     */
    @PostConstruct
    public void initJPushClient() {
        jPushClient = new JPushClient(secret, appkey);
    }

    /**
     * 获取推送客户端
     *
     * @return
     */
    public JPushClient getJPushClient() {
        return jPushClient;
    }

    /**
     * 区分开发和线上环境
     *
     * @return
     */
    public boolean getApns() {
        return apns;
    }
}
