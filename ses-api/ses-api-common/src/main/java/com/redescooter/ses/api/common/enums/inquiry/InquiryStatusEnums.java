package com.redescooter.ses.api.common.enums.inquiry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:InquiryStatusEnums
 * @description: InquiryStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/06 10:41
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum InquiryStatusEnums {

    UNPROCESSED("UNPROCESSED", "未处理", "1"),
    PROCESSED("PROCESSED", "已处理", "2"),
    DECLINE("DECLINE", "拒绝", "3"),
    UNPAY_DEPOSIT("UNPAY_DEPOSIT", "定金未支付（支付失败）", "4"),
    PAY_DEPOSIT("PAY_DEPOSIT", "定金已支付", "5"),
    PAY_LAST_PARAGRAPH("PAY_LAST_PARAGRAPH", "尾款支付", "6"),
    ;

    private String code;

    private String message;

    private String value;
}
