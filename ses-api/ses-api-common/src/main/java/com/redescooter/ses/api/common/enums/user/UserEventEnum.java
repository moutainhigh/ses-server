package com.redescooter.ses.api.common.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:UserEventEnum
 * @description: UserEventEnum
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/17 15:36
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserEventEnum {
    CREATE("CREATE", "CREATE", "创建", "1"),
    ACTIVE("ACTIVE", "ACTIVE", "激活", "2"),
    OVERDUE("OVERDUE", "OVERDUE", "过期", "3"),
    RENEW("RENEW", "RENEW", "续费", "4"),
    FROZEN("FROZEN", "FROZEN", "冻结", "5"),
    UNFREEZE("UNFREEZE", "UNFREEZE", "解除冻结", "6");

    private String code;

    private String message;

    private String name;

    private String value;
}
