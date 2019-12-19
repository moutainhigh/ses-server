package com.redescooter.ses.api.common.enums.ros.customer;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:IndustryEnums
 * @description: IndustryEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/10 17:54
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum IndustryEnums {
    RESTAURANT("RESTAURANT", "餐厅",1),
    EXPRESS_DELIVERY("EXPRESS_DELIVERY", "快递",2);

    private String code;

    private String message;

    private Integer value;

    public static IndustryEnums getIndustryEnumByCode(String code) {
        for (IndustryEnums item : IndustryEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
