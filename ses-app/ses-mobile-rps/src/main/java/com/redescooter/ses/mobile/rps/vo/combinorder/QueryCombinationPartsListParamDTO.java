package com.redescooter.ses.mobile.rps.vo.combinorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询组装单产品部件列表入参对象 DTO
 * @author assert
 * @date 2021/1/27 15:49
 */
@Data
@ApiModel(value = "查询组装单产品部件列表入参对象")
public class QueryCombinationPartsListParamDTO extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.PRODUCT_ID_IS_EMPTY, message = "产品id不能为空")
    @ApiModelProperty(value = "产品id", dataType = "Long")
    private Long productId;

    @NotNull(code = ValidationExceptionCode.PRODUCT_TYPE_IS_EMPTY, message = "产品类型不能为空")
    @ApiModelProperty(value = "产品类型 1车辆 2组装件", dataType = "Integer")
    private Integer productType;

}
