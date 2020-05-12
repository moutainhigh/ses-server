package com.redescooter.ses.api.common.enums.website;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:TopClassEnums
 * @description: TopClassEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 17:42
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TopClassEnums {

    TOP_CASE("TOP_CASE", "后备箱", "1000000","99.00");

    private String code;

    private String message;

    private String value;

    private String price;
}
