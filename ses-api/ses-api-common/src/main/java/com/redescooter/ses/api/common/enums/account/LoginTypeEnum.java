package com.redescooter.ses.api.common.enums.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: LoginType
 * @author: Darren
 * @create: 2019/04/03 16:36
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    PASSWORD(1, "密码登录"), DYNAMIC_CODE(2, "动态验证码登录");

    private int code;

    private String message;

}
