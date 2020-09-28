package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import lombok.*;

import java.sql.Timestamp;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentPaymentEnter {

    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_PAY_TIME_IS_EMPTY, message = "支付时间为空")
    private Timestamp date;

    @NotNull(code = ThirdValidationExceptionCode.AMOUNT_IS_EMPTY, message = "支付金额为空")
    private String amount;

    @NotNull(code = ThirdValidationExceptionCode.PAYMENT_TYPE_IS_EMPTY, message = "支付类型为空")
    private int medium;

    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_IDENT_IS_EMPTY, message = "订单编号为空")
    private String ident;

    private String notes;

    private String email;

    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCTYPE_IS_EMPTY, message = "文档类型为空")
    private String doctype;

    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_ID_IS_EMTPY, message = "文档ID为空")
    private String docid;

    private String deadlineid;
}
