package com.redescooter.ses.mobile.rps.vo.purchasinwh;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.api.common.vo.base.PageResult;
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
    @ApiModelProperty(value = "采购id")
    private Long id;
}
