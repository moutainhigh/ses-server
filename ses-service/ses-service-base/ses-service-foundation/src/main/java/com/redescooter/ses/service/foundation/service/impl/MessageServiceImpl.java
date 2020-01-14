package com.redescooter.ses.service.foundation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redescooter.ses.api.common.enums.mesage.MessagePriorityEnums;
import com.redescooter.ses.api.common.enums.mesage.MessageStatus;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.foundation.exception.FoundationException;
import com.redescooter.ses.api.foundation.service.MessageService;
import com.redescooter.ses.api.foundation.vo.message.MessageListEnter;
import com.redescooter.ses.api.foundation.vo.message.MessageResult;
import com.redescooter.ses.api.foundation.vo.message.MessageSaveEnter;
import com.redescooter.ses.api.foundation.vo.message.ReadMessageEnter;
import com.redescooter.ses.api.foundation.vo.message.UnReadMessageCountResult;
import com.redescooter.ses.service.common.i18n.I18nServiceMessage;
import com.redescooter.ses.service.foundation.constant.SequenceName;
import com.redescooter.ses.service.foundation.dao.MessageServiceMapper;
import com.redescooter.ses.service.foundation.dao.base.PlaMessageMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMessage;
import com.redescooter.ses.service.foundation.exception.ExceptionCodeEnums;
import com.redescooter.ses.starter.common.service.IdAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    @Autowired
    private I18nServiceMessage i18nServiceMessage;
    @Reference
    private IdAppService idAppService;

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
        Locale locale = new Locale(enter.getLanguage(), enter.getCountry());
        List<MessageResult> messageResultList = messageServiceMapper.messageList(enter);
        if (CollectionUtils.isNotEmpty(messageResultList)) {
            messageResultList.forEach(item -> {
                Object[] args = StringUtils.isBlank(item.getMemo()) == true ? null : item.getMemo().split(",");
                item.setTitle(i18nServiceMessage.getMessage(item.getTitle(), args, locale));
                item.setContent(i18nServiceMessage.getMessage(item.getContent(), args, locale));
            });
        }
        return PageResult.create(enter, count, messageResultList);
    }

    /**
     * 最近五条消息
     *
     * @param enter
     * @return
     */
    @Override
    public PageResult<MessageResult> recentMessages(PageEnter enter) {
        //todo 缺少国家化配置
        int count = messageServiceMapper.recentMessagesCount(enter);
        if (count == 0) {
            return PageResult.createZeroRowResult(enter);
        }
        Locale locale = new Locale(enter.getLanguage(), enter.getCountry());
        List<MessageResult> messageResults = messageServiceMapper.recentMessageList(enter);
        if (CollectionUtils.isNotEmpty(messageResults)) {
            messageResults.forEach(item -> {
                Object[] args = StringUtils.isBlank(item.getMemo()) == true ? null : item.getMemo().split(",");
                item.setTitle(i18nServiceMessage.getMessage(item.getTitle(), args, locale));
                item.setContent(i18nServiceMessage.getMessage(item.getContent(), args, locale));
            });
        }

        return PageResult.create(enter, count, messageResults);
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
        // enter 为空全部已读 不为空 指定读取
        List<PlaMessage> plaMessages = null;
        if (CollectionUtils.isNotEmpty(enter.getIds())) {
            plaMessages = plaMessageMapper.selectBatchIds(enter.getIds());
        }
        if (CollectionUtils.isEmpty(enter.getIds())) {
            QueryWrapper<PlaMessage> plaMessageQueryWrapper = new QueryWrapper<>();
            plaMessageQueryWrapper.eq(PlaMessage.COL_TENANT_ID, enter.getTenantId());
            plaMessageQueryWrapper.eq(PlaMessage.COL_USER_ID, enter.getUserId());
            plaMessageQueryWrapper.eq(PlaMessage.COL_DR, 0);
            plaMessageQueryWrapper.eq(PlaMessage.COL_STATUS, MessageStatus.UNREAD.getValue());
            plaMessages = plaMessageMapper.selectList(plaMessageQueryWrapper);
        }

        if (CollectionUtils.isEmpty(plaMessages)) {
            return new GeneralResult(enter.getRequestId());
        }
        plaMessages.forEach(item -> {
            if (CollectionUtils.isNotEmpty(enter.getIds())) {
                if (!enter.getIds().contains(item.getId())) {
                    throw new FoundationException(ExceptionCodeEnums.MESSAGE_IS_NOT_EXIST.getCode(), ExceptionCodeEnums.MESSAGE_IS_NOT_EXIST.getMessage());
                }
                if (!StringUtils.equals(item.getStatus(), MessageStatus.UNREAD.getValue())) {
                    throw new FoundationException(ExceptionCodeEnums.STATUS_IS_REASONABLE.getCode(), ExceptionCodeEnums.STATUS_IS_REASONABLE.getMessage());
                }
            }
            item.setStatus(MessageStatus.READ.getValue());
            item.setUpdatedBy(enter.getUserId());
            item.setUpdatedTime(new Date());
        });

        plaMessageMapper.updateBatch(plaMessages);
        return new GeneralResult(enter.getRequestId());
    }

    /**
     * 消息保存
     *
     * @param enter
     * @return
     */
    @Override
    public void save(MessageSaveEnter enter) {
        PlaMessage record = new PlaMessage();

        BeanUtils.copyProperties(enter, record);
        record.setId(idAppService.getId(SequenceName.PLA_MESSAGE));
        record.setDr(0);
        record.setTenantId(enter.getTenantId());
        record.setMessagePriority(enter.getMessagePriority());
        record.setBusinessStatus(enter.getBussinessStatus());
        record.setStatus(MessageStatus.UNREAD.getValue());
        record.setCreatedTime(new Date());
        record.setCreatedBy(enter.getUserId());
        record.setUpdatedBy(enter.getUserId());
        record.setUpdatedTime(new Date());
        //发送时间
        record.setSendTime(new Date());

        plaMessageMapper.insert(record);
    }


    /**
     * 未读消息
     *
     * @param enter
     * @return
     */
    @Override
    public UnReadMessageCountResult unReadMessages(GeneralEnter enter) {

        QueryWrapper<PlaMessage> plaMessageQueryWrapper = new QueryWrapper<>();
        plaMessageQueryWrapper.eq(PlaMessage.COL_USER_ID, enter.getUserId());
        plaMessageQueryWrapper.eq(PlaMessage.COL_TENANT_ID, enter.getTenantId());
        plaMessageQueryWrapper.eq(PlaMessage.COL_STATUS, MessageStatus.UNREAD.getValue());

        // 查询所有未读的消息总数
        Integer unReadMessagesCount = plaMessageMapper.selectCount(plaMessageQueryWrapper);
        plaMessageQueryWrapper.eq(PlaMessage.COL_MESSAGE_PRIORITY, MessagePriorityEnums.FORCED_REMIND.getValue());
        plaMessageQueryWrapper.last("limit 1");
        PlaMessage plaMessage = plaMessageMapper.selectOne(plaMessageQueryWrapper);
        MessageResult messageResult = new MessageResult();
        BeanUtils.copyProperties(plaMessage, messageResult);

        // 消息国际化
        Locale locale = new Locale(enter.getLanguage(), enter.getCountry());
        Object[] args = StringUtils.isBlank(plaMessage.getMemo()) == true ? null : plaMessage.getMemo().split(",");

        messageResult.setTitle(i18nServiceMessage.getMessage(plaMessage.getTitle(), args, locale));
        messageResult.setContent(i18nServiceMessage.getMessage(plaMessage.getContent(), args, locale));

        return UnReadMessageCountResult.builder()
                .unReadTotal(unReadMessagesCount)
                .message(messageResult)
                .build();
    }
}
