package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.*;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyQueryDocumentPaymentListEnter {
    private String docid;

    private String doctype;

    private String payid;

    private String transactionid;

    private String amount_type;

    private String step;

    private String bankdepositid;

    private String paymentMethod;

    private String fee;

    private String directdebittype;

    private String partner_payment_reference;

    private String inBank;

    private String cycle_id;

    private String deletable;

    private String amount_net;

    private String relatedId;

    private String relatedType;

    private String relatedDate;

    private String relatedAmount;

    private String relatedMedium;

    private String relatedMediumTxt;

    private String relatedIdent;

    private String relatedNotes;

    private String relatedFullLabel;

    private String formatted_relatedDate;

    private String formatted_relatedAmount;
}
