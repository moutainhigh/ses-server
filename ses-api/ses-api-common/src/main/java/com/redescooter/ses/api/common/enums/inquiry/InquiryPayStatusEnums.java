package com.redescooter.ses.api.common.enums.inquiry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:InquiryPayStatusEnums
 * @description: InquiryPayStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/13 19:15
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum InquiryPayStatusEnums {

    UNPAY_DEPOSIT("UNPAY_DEPOSIT", "定金未支付", "1"),
    PAY_DEPOSIT("PAY_DEPOSIT", "定金支付", "2"),
    PAY_LAST_PARAGRAPH("PAY_LAST_PARAGRAPH", "尾款支付", "3"),
    ;


    private String code;

    private String message;

    private String value;
}
