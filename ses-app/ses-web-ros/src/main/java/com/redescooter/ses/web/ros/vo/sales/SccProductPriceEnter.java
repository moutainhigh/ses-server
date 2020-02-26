package com.redescooter.ses.web.ros.vo.sales;

import com.redescooter.ses.api.common.annotation.NotNull;
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
 * @ClassName:SccProductPriceEnter
 * @description: SccProductPriceEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 18:32
 */
@ApiModel(value = "产品报价保存入参", description = "产品报价保存入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SccProductPriceEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "法国报价")
//    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
    private String productFrPrice;

    @ApiModelProperty(value = "英国报价")
    private String productEnPrice;
}
