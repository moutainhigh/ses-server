package com.redescooter.ses.api.common.enums.website;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ContantUsMessageType
 * @description: ContantUsMessageType 联系我们 消息类型
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/12 16:13
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ContantUsMessageType {
    
    LEAVE_MESSAGE("LEAVE_MESSAGE","留言","1"),
    REPLY("REPLY","回复","2");
    
    private String code;
    
    private String message;
    
    private String value;
}
