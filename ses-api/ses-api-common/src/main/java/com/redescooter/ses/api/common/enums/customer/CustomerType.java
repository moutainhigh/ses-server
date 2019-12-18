package com.redescooter.ses.api.common.enums.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: LoginType
 * @author: alex
 * @create: 2019/04/03 16:36
 */
@Getter
@AllArgsConstructor
public enum CustomerType {

    ENTERPRISE("ENTERPRISE", "公司"),
    PERSONAL("PERSONAL", "个人");

    private String type;

    private String message;

}
