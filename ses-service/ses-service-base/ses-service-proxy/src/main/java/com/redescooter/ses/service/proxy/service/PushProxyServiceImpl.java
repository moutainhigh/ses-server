package com.redescooter.ses.service.proxy.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PushTypeEnums;
import com.redescooter.ses.api.common.vo.jiguang.PushJgResult;
import com.redescooter.ses.api.proxy.constant.PushResultCode;
import com.redescooter.ses.api.proxy.service.PushProxyService;
import com.redescooter.ses.api.proxy.vo.jiguang.PushProxyEnter;
import com.redescooter.ses.service.proxy.config.PushConfigs;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * description: PushProxyServiceImpl
 * author: jerry.li
 * create: 2019-05-20 11:10
 */
@Component
@Slf4j
@Service
public class PushProxyServiceImpl implements PushProxyService {

    @Autowired
    private PushConfigs jPushConfig;

    /**
     * 广播 (所有平台，所有设备, 不支持附加信息)
     *
     * @param enter 推送内容
     * @return
     * @author jerry
     */
    @Override
    public PushJgResult pushAll(PushProxyEnter enter) {
        PushResult pushResult = sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(enter.getAlert()))
                .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns(enter)).build())
                .build(), enter);
        if (pushResult == null) {
            return PushJgResult.builder()
                    .code(PushResultCode.DEFAULT_CODE)
                    .message(PushResultCode.DEFAULT_MESSAGE)
                    .msg_id(PushResultCode.DEFAULT_MSGID)
                    .sendno(PushResultCode.DEFAULT_SEND_NO)
                    .statusCode(PushResultCode.DEFAULT_STATUS_CODE)
                    .build();
        }
        return PushJgResult.builder()
                .code(pushResult.error == null ? PushResultCode.SUCCESS_CODE : pushResult.error.getCode())
                .message(pushResult.error == null ? PushResultCode.SUCCESS_MESSAGE : pushResult.error.getMessage())
                .msg_id(pushResult.msg_id)
                .sendno(pushResult.sendno)
                .statusCode(pushResult.statusCode)
                .build();
    }

    /**
     * 指定或者多个用户进行推送 (一次推送最多 1000 个)
     *
     * @param enter 推送内容
     * @return
     * @author jerry
     */
    @Override
    public PushJgResult push(PushProxyEnter enter) {

        String type = enter.getType();
        switch (type) {
            case "android":
                return this.pushAndroid(enter, enter.getRegistids());
            case "ios":
                return this.pushIos(enter, enter.getRegistids());
            case "none":
                log.info("用户订单尚未推荐消息，原因是为推送不支持{}平台", type);
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * ios广播
     *
     * @param enter 推送内容
     * @return
     * @author jerry
     */
    @Override
    public PushJgResult pushIosAll(PushProxyEnter enter) {
        PushResult pushResult = sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.ios(enter.getAlert(), enter.getExtras()))
                .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns(enter)).build())
                .build(), enter);
        if (pushResult == null) {
            return PushJgResult.builder()
                    .code(PushResultCode.DEFAULT_CODE)
                    .message(PushResultCode.DEFAULT_MESSAGE)
                    .msg_id(PushResultCode.DEFAULT_MSGID)
                    .sendno(PushResultCode.DEFAULT_SEND_NO)
                    .statusCode(PushResultCode.DEFAULT_STATUS_CODE)
                    .build();
        }
        return PushJgResult.builder()
                .code(pushResult.error == null ? PushResultCode.SUCCESS_CODE : pushResult.error.getCode())
                .message(pushResult.error == null ? PushResultCode.SUCCESS_MESSAGE : pushResult.error.getMessage())
                .msg_id(pushResult.msg_id)
                .sendno(pushResult.sendno)
                .statusCode(pushResult.statusCode)
                .build();
    }

    /**
     * ios通过registid推送 (一次推送最多 1000 个)
     *
     * @param enter     推送内容
     * @param registids 推送id
     * @return
     * @author jerry
     */
    @Override
    public PushJgResult pushIos(PushProxyEnter enter, String... registids) {
        PushResult pushResult = sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registids))
                .setNotification(Notification.ios(enter.getAlert(), enter.getExtras()))
                .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns(enter)).build())
                .build(), enter);
        if (pushResult == null) {
            return PushJgResult.builder()
                    .code(PushResultCode.DEFAULT_CODE)
                    .message(PushResultCode.DEFAULT_MESSAGE)
                    .msg_id(PushResultCode.DEFAULT_MSGID)
                    .sendno(PushResultCode.DEFAULT_SEND_NO)
                    .statusCode(PushResultCode.DEFAULT_STATUS_CODE)
                    .build();
        }
        return PushJgResult.builder()
                .code(pushResult.error == null ? PushResultCode.SUCCESS_CODE : pushResult.error.getCode())
                .message(pushResult.error == null ? PushResultCode.SUCCESS_MESSAGE : pushResult.error.getMessage())
                .msg_id(pushResult.msg_id)
                .sendno(pushResult.sendno)
                .statusCode(pushResult.statusCode)
                .build();
    }

    /**
     * android广播
     *
     * @param enter 推送内容
     * @return
     * @author jerry
     */
    @Override
    public PushJgResult pushAndroidAll(PushProxyEnter enter) {
        PushResult pushResult = sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(enter.getAlert(), enter.getTitle(), enter.getExtras()))
                .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns(enter)).build())
                .build(), enter);
        if (pushResult == null) {
            return PushJgResult.builder()
                    .code(PushResultCode.DEFAULT_CODE)
                    .message(PushResultCode.DEFAULT_MESSAGE)
                    .msg_id(PushResultCode.DEFAULT_MSGID)
                    .sendno(PushResultCode.DEFAULT_SEND_NO)
                    .statusCode(PushResultCode.DEFAULT_STATUS_CODE)
                    .build();
        }
        return PushJgResult.builder()
                .code(pushResult.error == null ? PushResultCode.SUCCESS_CODE : pushResult.error.getCode())
                .message(pushResult.error == null ? PushResultCode.SUCCESS_MESSAGE : pushResult.error.getMessage())
                .msg_id(pushResult.msg_id)
                .sendno(pushResult.sendno)
                .statusCode(pushResult.statusCode)
                .build();
    }

    /**
     * android通过registid推送 (一次推送最多 1000 个)
     *
     * @param enter     推送内容
     * @param registids 推送id：registration_id
     * @return
     * @author jerry
     */
    @Override
    public PushJgResult pushAndroid(PushProxyEnter enter, String... registids) {
        PushResult pushResult = sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registids))
                .setNotification(Notification.android(enter.getAlert(), enter.getTitle(), enter.getExtras()))
                .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns(enter)).build())
                .build(), enter);

        if (pushResult == null) {
            return PushJgResult.builder()
                    .code(PushResultCode.DEFAULT_CODE)
                    .message(PushResultCode.DEFAULT_MESSAGE)
                    .msg_id(PushResultCode.DEFAULT_MSGID)
                    .sendno(PushResultCode.DEFAULT_SEND_NO)
                    .statusCode(PushResultCode.DEFAULT_STATUS_CODE)
                    .build();
        }
        return PushJgResult.builder()
                .code(pushResult.error == null ? PushResultCode.SUCCESS_CODE : pushResult.error.getCode())
                .message(pushResult.error == null ? PushResultCode.SUCCESS_MESSAGE : pushResult.error.getMessage())
                .msg_id(pushResult.msg_id)
                .sendno(pushResult.sendno)
                .statusCode(pushResult.statusCode)
                .build();
    }

    /**
     * android通过registid推送 (一次推送最多 1000 个)
     *
     * @param enter    推送内容
     * @param pushAims 别名推送
     * @return
     * @author jerry
     */
    @Override
    public PushJgResult pushAndroidOfAlias(PushProxyEnter enter, String... pushAims) {

        PushResult pushResult = sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience((enter.getPushAimsType().equals(PushTypeEnums.REGISTRATION_ID)) ? (Audience.registrationId(pushAims)) : (Audience.alias(pushAims)))
                .setNotification(Notification.android(enter.getAlert(), enter.getTitle(), enter.getExtras()))
                .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns(enter)).build())
                .build(), enter);
        if (pushResult == null) {
            return PushJgResult.builder()
                    .code(PushResultCode.DEFAULT_CODE)
                    .message(PushResultCode.DEFAULT_MESSAGE)
                    .msg_id(PushResultCode.DEFAULT_MSGID)
                    .sendno(PushResultCode.DEFAULT_SEND_NO)
                    .statusCode(PushResultCode.DEFAULT_STATUS_CODE)
                    .build();
        }
        return PushJgResult.builder()
                .code(pushResult.error == null ? PushResultCode.SUCCESS_CODE : pushResult.error.getCode())
                .message(pushResult.error == null ? PushResultCode.SUCCESS_MESSAGE : pushResult.error.getMessage())
                .msg_id(pushResult.msg_id)
                .sendno(pushResult.sendno)
                .statusCode(pushResult.statusCode)
                .build();
    }

    /**
     * 调用api推送
     *
     * @param pushPayload 推送实体
     * @return
     * @author jerry
     */
    @Async("proxy-executor")
    PushResult sendPush(PushPayload pushPayload, PushProxyEnter enter) {
        log.info("发送极光推送请求: {}", pushPayload);
        PushResult result = null;
        try {
            if (jPushConfig.getJPushClient(enter) != null && enter != null) {
                result = jPushConfig.getJPushClient(enter).sendPush(pushPayload);
            } else {
                log.error("极光推送失败，没有可用的APP应用实例");
            }
        } catch (APIConnectionException e) {
            log.error("极光推送连接异常: ", e);
        } catch (APIRequestException e) {
            log.error("极光推送请求异常: ", e);
        }
        if (result != null && result.isResultOK()) {
            log.info("极光推送请求成功: {}", result);
            return result;
        } else {
            log.info("极光推送请求失败: {}", result);
            return result;
        }
    }
}
