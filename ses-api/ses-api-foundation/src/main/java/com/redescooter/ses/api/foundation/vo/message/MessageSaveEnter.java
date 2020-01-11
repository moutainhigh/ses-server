package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * description: MessageSaveEnter
 * author: jerry.li
 * create: 2019-05-23 11:51
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MessageSaveEnter extends GeneralEnter {

    /**
     * 消息类型：推送消息PUSH，站内消息SITE
     */
    private String messageType;

    /**
     * 消息类型
     */
    private String bizType;

    /**
     * 业务Id
     */
    private String bizId;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息业务状态
     */
    private String bussinessStatus;
    /**
     * 消息内容参数
     */
    private String memo;
    /**
     * 消息优先级
     */
    private String messagePriority;

}
