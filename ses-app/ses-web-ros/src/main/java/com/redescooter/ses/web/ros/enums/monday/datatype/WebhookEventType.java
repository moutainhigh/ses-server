package com.redescooter.ses.web.ros.enums.monday.datatype;

import lombok.Getter;

/**
 * @ClassName:WebhookEventType
 * @description: WebhookEventType
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/03 11:00
 */
@Getter
public enum WebhookEventType {

    //传入通知
    incoming_notification,

    //改变列的值
    change_column_value,

    //更改特定的列值
    change_specific_column_value,

    //创建条目
    create_item,

    //创建一个更新
    create_update;

}
