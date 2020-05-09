package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.foundation.vo.message.MessageListEnter;
import com.redescooter.ses.api.foundation.vo.message.MessageResult;

import java.util.List;

/**
 * @ClassName:MessageServiceMapper
 * @description: MessageServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 19:39
 */
public interface MessageServiceMapper {

    /**
     * 消息列表
     *
     * @param enter
     * @return
     */
    int messageListCount(MessageListEnter enter);

    /**
     * 消息列表
     *
     * @param enter
     * @return
     */
    List<MessageResult> messageList(MessageListEnter enter);

    /**
     * 最近消息
     *
     * @param enter
     * @return
     */
    int recentMessagesCount(PageEnter enter);

    /**
     * 最近的消息列表
     *
     * @param enter
     * @return
     */
    List<MessageResult> recentMessageList(PageEnter enter);
}
