package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyDocmentTypeEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyDocumentInvoiceStatusEnums;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyUpdateDocumentInvoidSatusEnter {
    @ApiModelProperty(value = "单据类型")
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCTYPE_IS_EMPTY, message = "单据类型为空")
    private SellsyDocmentTypeEnums doctype;

    @ApiModelProperty(value = "单据状态")
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCUMENT_STATUS_IS_EMPTY, message = "单据状态为空")
    private SellsyDocumentInvoiceStatusEnums step;

}
