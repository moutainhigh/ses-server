package com.redescooter.ses.api.foundation.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.vo.message.MessageListEnter;
import com.redescooter.ses.api.foundation.vo.message.MessageResult;
import com.redescooter.ses.api.foundation.vo.message.MessageSaveEnter;
import com.redescooter.ses.api.foundation.vo.message.ReadMessageEnter;
import com.redescooter.ses.api.foundation.vo.message.UnReadMessageCountResult;

import java.util.List;

/**
 * @ClassName:MessageService
 * @description: MessageService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 18:58
 */
public interface MessageService {

    /**
     * 消息分页
     *
     * @param enter
     * @return
     */
    PageResult<MessageResult> messageList(MessageListEnter enter);

    /**
     * 最近五条消息
     *
     * @param enter
     * @return
     */
    PageResult<MessageResult> recentMessages(PageEnter enter);

    /**
     * 消息读取
     *
     * @param enter
     * @return
     */
    GeneralResult readMessage(ReadMessageEnter enter);

    /**
     * 消息保存
     *
     * @param enter
     */
    void save(MessageSaveEnter enter);

    /**
     * 未读消息
     *
     * @param enter
     * @return
     */
    UnReadMessageCountResult unReadMessages(GeneralEnter enter);

}
