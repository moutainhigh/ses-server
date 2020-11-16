package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PaymentTypeEnums
 * @description: PaymentTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 18:54 
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PaymentTypeEnums {

    PREPAYMENTS("PREPAYMENTS","预付款",1),
    MONTHLY("MONTHLY","月结",2),
    FULL_AMOUNT("FULL_AMOUNT","全款",3);


    private String code;

    private String message;

    private Integer value;
}
