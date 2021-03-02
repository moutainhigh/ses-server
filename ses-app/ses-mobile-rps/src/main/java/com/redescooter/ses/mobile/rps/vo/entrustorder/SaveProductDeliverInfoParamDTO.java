package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 保存产品发货信息入参对象
 * @author assert
 * @date 2021/1/7 18:09
 */
@Data
@ApiModel(value = "保存产品发货信息入参对象")
public class SaveProductDeliverInfoParamDTO extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.PRODUCT_ID_IS_EMPTY, message = "产品id不能为空")
    @ApiModelProperty(value = "产品id", dataType = "Long", required = true)
    private Long productId;

    @NotNull(code = ValidationExceptionCode.PRODUCT_TYPE_IS_EMPTY, message = "产品类型不能为空")
    @ApiModelProperty(value = "产品类型 1车辆 2组装件 3部件", dataType = "Integer", required = true)
    private Integer productType;

    @ApiModelProperty(value = "发货数量(无码部件时传递)", dataType = "Integer")
    private Integer qty;

}
