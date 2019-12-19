package com.redescooter.ses.api.common.enums.proxy.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 11/10/2019 12:29 下午
 * @ClassName: MailConfigStatusEnums, normal正常，Disabled失效的
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MailTemplateStatusEnums {

    NORMAL("NORMAL", "正常"),
    DISABLED("DISABLED", "失效"),
    ;

    private String code;

    private String explain;

}
