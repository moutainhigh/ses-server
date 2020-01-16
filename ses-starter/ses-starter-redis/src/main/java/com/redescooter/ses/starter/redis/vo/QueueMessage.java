package com.redescooter.ses.starter.redis.vo;

import com.redescooter.ses.starter.redis.enums.TopicQueueTypeEnums;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class QueueMessage extends MessageGeneralEnter {
    /**
     * 消息事件的类型
     */
    private TopicQueueTypeEnums type;
    /**
     * 消息唯一标识
     */
    private String id;
    /**
     * 为消费时不同类去处理
     */
    private String channel;
    /**
     * 具体消息 json
     */
    private String body;

    /**
     * 延时时间 被消费时间  取当前时间戳+延迟时间
     */
    private Long delayTime;

    /**
     * 定义可扩展的字段
     */
    private Map<String, String> exts = new HashMap<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
