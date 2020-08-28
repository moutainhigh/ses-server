package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyCatalogueEnums {

    item("item", "产品条目", "item"), service("service", "服务", "service");

    private String code;

    private String message;

    private String value;
}
