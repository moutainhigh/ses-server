package com.redescooter.ses.api.common.enums.tenant;

import com.redescooter.ses.api.common.enums.customer.CustomerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 10:45 上午
 * @ClassName: TenanTypeEnum
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
public enum TenanTypeEnum {

    ENTERPRISE("ENTERPRISE", "公司", "1"),

    PERSONAL("PERSONAL", "个人", "2");

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
