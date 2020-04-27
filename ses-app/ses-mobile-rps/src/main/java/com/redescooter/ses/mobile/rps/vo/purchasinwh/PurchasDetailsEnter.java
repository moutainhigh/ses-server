package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
@ApiModel(value = "采购单列表入参", description = "采购单列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PurchasDetailsEnter extends PageEnter {
    @ApiModelProperty(value = "主键",required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;
}
