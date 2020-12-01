package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import io.swagger.annotations.*;

/**
 * @ClassName:CheckProductNEnter
 * @description: CheckProductNEnter
 * @author: Alex @Version：1.3
 * @create: 2020/10/10 14:42
 */
@ApiModel(value = "产品编号校验", description = "")
@Data // 生成getter,setter等函数
@EqualsAndHashCode(callSuper = false)
public class CheckProductNEnter extends GeneralEnter {

    @ApiModelProperty(value = "Id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    @NotNull(code = ValidationExceptionCode.PRODUCT_NUM_IS_EMPTY, message = "产品编号为空")
    private String productN;

    @ApiModelProperty(value = "产品类型")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "产品类型为空")
    private Integer productionProductType;
}
