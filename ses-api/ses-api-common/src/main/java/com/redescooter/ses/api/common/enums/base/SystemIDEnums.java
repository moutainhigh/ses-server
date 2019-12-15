package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 22/11/2019 5:40 下午
 * @ClassName: SystemIDEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
public enum SystemIDEnums {

    REDE_DELIVERY("REDE_DELIVERY", 1, "SaaS配送系统"),
    REDE_SES("REDE_SES", 1, "SaaS内部系统"),
    ;

    //系统编码
    private String systemId;

    //编码对应值
    private Integer value;

    //编码备注说明
    private String remark;

}
