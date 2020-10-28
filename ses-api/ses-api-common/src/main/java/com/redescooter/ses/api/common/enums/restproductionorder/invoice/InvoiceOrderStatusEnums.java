package com.redescooter.ses.api.common.enums.restproductionorder.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  @author: alex
 *  @Date: 2020/10/26 14:33
 *  @version：V ROS 1.8.3
 *  @Description:
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum InvoiceOrderStatusEnums {


    MATERIALS_PRE("MATERIALS_PRE", "待备料", 0),
    STOCKING("STOCKING", "备料中", 10),
    BE_LOADED("BE_LOADED", "待装车", 20),
    BE_DELIVERED("BE_DELIVERED", "待发货", 30),
    BE_SIGNED("BE_SIGNED", "待签收", 40),
    RECEIVED("RECEIVED", "已签收", 50);

    private String code;

    private String message;

    private Integer value;
}
