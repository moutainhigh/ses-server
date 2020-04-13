package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.PageEnter;
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
}
