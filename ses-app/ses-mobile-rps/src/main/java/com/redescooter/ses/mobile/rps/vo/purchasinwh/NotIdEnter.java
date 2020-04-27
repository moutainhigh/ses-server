package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;
    @ApiModelProperty(value = "入库数量",required = true)
    @NotNull(code = ValidationExceptionCode.IN_WAIT_WH_QTY, message = "入库数量不能为空")
    private int inWaitWhQty;

}
