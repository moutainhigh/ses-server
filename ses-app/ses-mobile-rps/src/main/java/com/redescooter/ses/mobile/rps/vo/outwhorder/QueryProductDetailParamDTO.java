package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询出库单产品详情入参对象 DTO
 * @author assert
 * @date 2021/1/5 13:30
 */
@Data
@ApiModel(value = "查询出库单产品详情入参对象")
public class QueryProductDetailParamDTO extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "产品id不能为空")
    @ApiModelProperty(value = "产品id", dataType = "Long")
    private Long id;

    @NotNull(code = ValidationExceptionCode.OUT_WH_ORDER_TYPE_IS_EMPTY, message = "出库单类型不能为空")
    @ApiModelProperty(value = "出库单类型 1整车 2组装件 3部件", dataType = "Integer")
    private Integer orderType;

}
