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
    ;

    private String code;

    private String message;

    private String value;
}
