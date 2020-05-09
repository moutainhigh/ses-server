package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:QcTemplateResultEnums
 * @description: QcTemplateResultEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/15 10:15
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum QcTemplateResultEnums {

    PASS("PASS","通过","1"),
    NG("NG","失败","2"),
    
    ;

    private String code;

    private String message;

    private String value;
}
