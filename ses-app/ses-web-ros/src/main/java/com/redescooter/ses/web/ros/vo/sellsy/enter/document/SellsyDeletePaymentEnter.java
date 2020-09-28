package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import lombok.*;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDeletePaymentEnter {

    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_PAYMENT_ID_IS_EMPTY, message = "支付Id为空")
    private int payid;

    @NotNull(code = ThirdValidationExceptionCode.TYPE_IS_EMPTY, message = "发票类型为空")
    private String doctype;

    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_ID_IS_EMTPY, message = "发票id为空")
    private int docid;
}
