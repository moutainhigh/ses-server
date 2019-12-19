package com.redescooter.ses.api.common.enums.ros.customer;

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
public enum CustomerIndustryEnums {
    RESTAURANT("RESTAURANT", "餐厅",1),
    EXPRESS_DELIVERY("EXPRESS_DELIVERY", "快递",2);

    private String code;

    private String message;

    private Integer value;

    public static CustomerIndustryEnums getIndustryEnumByCode(String code) {
        for (CustomerIndustryEnums item : CustomerIndustryEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
