package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redescooter.ses.api.common.enums.mesage.MessageStatus;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MessageService;
import com.redescooter.ses.api.foundation.vo.message.MessageListEnter;
import com.redescooter.ses.api.foundation.vo.message.MessageResult;
import com.redescooter.ses.api.foundation.vo.message.ReadMessageEnter;
import com.redescooter.ses.service.foundation.dao.MessageServiceMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaMessageMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMessage;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:MessageServiceImpl
 * @description: MessageServiceImpl
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/10 19:38
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageServiceMapper messageServiceMapper;
    @Autowired
    private PlaMessageMapper plaMessageMapper;

    /**
     * 消息分页
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MessageResult> messageList(MessageListEnter enter) {
        int count = messageServiceMapper.messageListCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        return PageResult.create(enter, count, messageServiceMapper.messageList(enter));
    }

    /**
     * 最近五条消息
     *
     * @param enter
     * @return
     */
    public PageResult<MessageResult> recentMessages(PageEnter enter) {
        //todo 缺少国家化配置
        int count = messageServiceMapper.recentMessagesCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }

        return PageResult.create(enter, count, messageServiceMapper.recentMessageList(enter));
    }

    /**
     * 消息读取
     *
     * @param enter
     * @return
     */
    @Transactional
    @Override
    public GeneralResult readMessage(ReadMessageEnter enter) {
        List<PlaMessage> plaMessages = plaMessageMapper.selectBatchIds(enter.getIds());
        if (CollectionUtils.isEmpty(plaMessages)) {
            return new GeneralResult(enter.getRequestId());
        }
        plaMessages.forEach(item -> {
            if (!enter.getIds().contains(item.getId())) {
                throw new FoundationException(ExceptionCodeEnums.MESSAGE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.MESSAGE_IS_NOT_EXIST.getMessage());
            }
            if (!StringUtils.equals(item.getStatus(), MessageStatus.UNREAD.getValue())) {
                throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
            }
            item.setStatus(MessageStatus.READ.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });

        plaMessageMapper.updateBatch(plaMessages);
        return new GeneralResult(enter.getRequestId());
    }
}
