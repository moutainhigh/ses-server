package com.redescooter.ses.web.ros.vo.bom.supplierChaim;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductPriceDetailEnter
 * @description: ProductPriceDetailEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/02 11:22
 */
@ApiModel(value = "价格详情入参", description = "价格详情入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScPriceEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long id;

    @ApiModelProperty(value = "价格类型，指（整车、部品、零部件），传值见TAPD", required = true)
    @NotNull(code = ValidationExceptionCode.SALES_PRICE_TYPE_IS_EMPTY, message = "销售价格类型")
    private String priceType;

    @ApiModelProperty(value = "是否在sales 调用，默认false")
    private Boolean serviceType = Boolean.FALSE;
}
