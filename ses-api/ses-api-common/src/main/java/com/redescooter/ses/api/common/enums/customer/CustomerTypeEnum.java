package com.redescooter.ses.api.common.enums.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 9:04 上午
 * @ClassName: CustomerTypeEnum
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
public enum CustomerTypeEnum {

    ENTERPRISE("ENTERPRISE", "公司","1"),
    PERSONAL("PERSONAL", "个人","2");

    private String code;

    private String message;

    private String value;

    public static CustomerTypeEnum getErrorCodeByCode(String code) {
        for (CustomerTypeEnum item : CustomerTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
