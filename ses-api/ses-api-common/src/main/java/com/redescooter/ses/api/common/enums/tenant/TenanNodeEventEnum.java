package com.redescooter.ses.api.common.enums.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 功能描述: 租户节点事件
 *
 * @auther: jerry
 * @date: 2019-05-31 00:45
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TenanNodeEventEnum {

    CREAT("CREAT", "creat", "创建", "1"),
    ACTIVE("ACTIVE", "ACTIVE", "激活", "2"),
    OVERDUE("OVERDUE", "overdue", "过期", "3"),
    RENEW("RENEW", "renew", "续费", "4"),
    FROZEN("FROZEN", "frozen", "冻结", "5"),
    UNFREEZE("UNFREEZE", "Unfreeze", "解除冻结", "6");

    private String code;

    private String message;

    private String name;

    private String value;

    public static TenanNodeEventEnum getErrorCodeByCode(String code) {
        for (TenanNodeEventEnum item : TenanNodeEventEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
