package com.redescooter.ses.api.common.enums.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:IndustryEnums
 * @description: CustomerIndustryEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/10 17:54
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TenanIndustryEnums {
    RESTAURANT("RESTAURANT", "餐厅", "1"),
    EXPRESS_DELIVERY("EXPRESS_DELIVERY", "快递", "2");

    private String code;

    private String message;

    private String value;

    public static TenanIndustryEnums getIndustryEnumByCode(String code) {
        for (TenanIndustryEnums item : TenanIndustryEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
