package com.redescooter.ses.api.common.enums.proxy.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 12:29 下午
 * @ClassName: MailConfigStatusEnums, PENDING待发送，SUCCESS发送成功，FAIL发送失败
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MailTaskStatusEnums {

    PENDING("PENDING", "待发送"),
    SUCCESS("SUCCESS", "发送成功"),
    FAIL("FAIL", "发送失败"),
    ;

    private String code;

    private String explain;

}
