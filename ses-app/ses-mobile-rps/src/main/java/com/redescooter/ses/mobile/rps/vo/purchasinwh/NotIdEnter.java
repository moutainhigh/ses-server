package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "没有ID入参", description = "没有ID入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class NotIdEnter extends GeneralEnter {
    @ApiModelProperty(value = "主键",required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;
    @ApiModelProperty(value = "入库数量", required = true)
    @NotNull(code = ValidationExceptionCode.QTY_IS_EMPTY, message = "入库数量不能为空")
    private int inWaitWhQty;
    @ApiModelProperty(value = "质检批次号")
    @NotNull(code = ValidationExceptionCode.BATCH_NO, message = "质检批次号不能为空")
    private String batchNo;
}
