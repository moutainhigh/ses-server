package com.redescooter.ses.api.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 主要适用于页面为两个type 切换的
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ClassTypeEnums {

    TYPE_ONE("TYPE_ONE", 1, "草稿/do_in"), TYPE_TWO("TYPE_TWO", 2, "历史/部件");

    private String code;

    private Integer value;

    private String message;
}
