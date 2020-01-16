package com.redescooter.ses.starter.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 15/1/2020 9:10 下午
 * @ClassName: TopicQueueTypeEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TopicQueueTypeEnums {

    ACCOUNT_OPEN(1, "ACCOUNT_OPEN"),
    MESSAGE_NOTIFICATION(2, "MESSAGE_NOTIFICATION"),
    DATA_SAVE(3, "DATA_SAVE"),
    DATA_UPDATE(4, "DATA_UPDATE"),
    DATA_QUERY(5, "DATA_QUERY"),
    DATA_DELETE(6, "DATA_DELETE"),
    STATUS_UPDATE(7, "STATUS_UPDATE"),
    NODE_SAVE(8, "NODE_SAVE"),
    ;

    private int value;
    private String name;


}

