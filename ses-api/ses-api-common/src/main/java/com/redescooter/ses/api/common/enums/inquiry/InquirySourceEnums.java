package com.redescooter.ses.api.common.enums.inquiry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:InquirySourceEnums
 * @description: InquirySourceEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/15 16:03
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum InquirySourceEnums {

    //询价单
    INQUIRY("INQUIRY", "INQUIRY", "1"),
    //预订单
    ORDER_FORM("ORDER_FORM", "ORDER_FORM", "2"),
    // 系统自动创建
    SYS_FORM("SYS_FORM", "SYS_FORM", "3");

    private String code;

    private String message;

    private String value;
}
