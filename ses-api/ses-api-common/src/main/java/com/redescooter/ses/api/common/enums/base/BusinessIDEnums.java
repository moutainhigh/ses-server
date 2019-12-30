package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 22/11/2019 5:52 下午
 * @ClassName: BusinessIDEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
public enum BusinessIDEnums {

    NORMAL("NORMAL", "1", "游离端"), PERSONAL("PERSONAL", "2", "个人端"), RESTAURANT("RESTAURANT", "3", "餐厅端"),
    EXPRESS("EXPRESS", "4", "快递端"),
    ;

    //业务编码
    private String code;

    //编码对应值
    private String value;

    //编码备注说明
    private String remark;

}
