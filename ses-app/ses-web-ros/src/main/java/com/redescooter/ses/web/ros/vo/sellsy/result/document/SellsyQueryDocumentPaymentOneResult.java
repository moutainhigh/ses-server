package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.*;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyQueryDocumentPaymentOneResult {
    private String status;

    private String date;

    private String amount;

    private String medium;

    private String mediumTxt;

    private String ident;

    private String notes;

    private String corpid;

    private String ownerid;

    private String docid;

    private String id;

    private String formatted_displayedDate;

    private String dueAmount;
}
