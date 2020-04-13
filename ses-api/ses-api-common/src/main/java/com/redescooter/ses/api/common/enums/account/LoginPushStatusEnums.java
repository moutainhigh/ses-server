package com.redescooter.ses.api.common.enums.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:LoginPushStatusEnums
 * @description: LoginPushStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 13:10
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum LoginPushStatusEnums {

    LOGIN_IN("LOGIN_IN", "登录", 0),
    LOGIN_OUT("LOGIN_OUT", "注销", 1);

    private String code;

    private String message;

    private Integer value;
}
