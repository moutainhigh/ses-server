package com.redescooter.ses.web.ros.vo.supplierChaim;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductPriceHistroyListEnter
 * @description: ProductPriceHistroyListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/02 15:06
 */
@ApiModel(value = "价格列表入参", description = "价格列表入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductPriceHistroyListEnter extends PageEnter {
    @ApiModelProperty(value = "id", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long id;

    @ApiModelProperty(value = "是否在salse 模块调用，默认为false")
    private Boolean serviceType = Boolean.FALSE;

    @ApiModelProperty(value = "价格类型，见TAPD", required = true)
    @NotNull(code = ValidationExceptionCode.SALES_PRICE_TYPE_IS_EMPTY, message = "销售价格类型")
    private String priceType;
}
