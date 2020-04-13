package com.redescooter.ses.api.foundation.service;


import com.redescooter.ses.api.common.vo.jiguang.PushJgResult;
import com.redescooter.ses.api.foundation.vo.message.MessagePushEnter;

import java.util.List;

/**
 * description: PushService 推送业务服务
 * author: jerry.li
 * create: 2019-05-20 11:22
 */

public interface PushService {

    /**
     * 推送全部, 不支持附加信息
     *
     * @return
     * @author jerry
     */
    List<PushJgResult> pushAll(MessagePushEnter enter);

    /**
     * 推送ios/Android 指定id
     *
     * @return
     * @author jerry
     */
    List<PushJgResult> push(MessagePushEnter enter);

    /**
     * 消息定时推送到PC
     *
     * @param enter
     * @return
     */
    List<PushJgResult> pushPC(MessagePushEnter enter);

    /**
     * 推送消息
     *
     * @param str
     */
    void pushMessage(String str);
}
