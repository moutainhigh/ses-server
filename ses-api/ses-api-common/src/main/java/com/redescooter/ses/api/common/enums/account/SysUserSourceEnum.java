package com.redescooter.ses.api.common.enums.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: SysUserStatusEnum
 * @author: Alex
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SysUserSourceEnum {

    SYSTEM("SYSTEM","ROS系统添加","1"),
    WEBSITE("WEBSITE","官网注册","2");
    private String code;

    private String message;

    private String value;

}
