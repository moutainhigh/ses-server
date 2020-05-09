package com.redescooter.ses.api.common.enums.bom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PriceTypeEnums
 * @description: PriceTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/02 13:12
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PriceTypeEnums {

    PART("PART", "部品", "1"),
    COMBINATION("COMBINATION", "组合（包含整车）", "2");

    private String code;

    private String name;

    private String value;
}
