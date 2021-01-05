package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询出库单入参对象 DTO
 * @author assert
 * @date 2021/1/4 16:42
 */
@Data
@ApiModel(value = "查询出库单入参对象")
public class QueryOutWarehouseOrderParamDTO extends PageEnter {

    @NotNull(code = ValidationExceptionCode.OUT_WH_ORDER_TYPE_IS_EMPTY, message = "出库单类型不能为空")
    @ApiModelProperty(value = "出库单类型 1整车 2组装件 3部件", dataType = "Integer", required = true)
    private Integer orderType;

    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "出库类型不能为空")
    @ApiModelProperty(value = "出库类型 1销售调拨 2补料出库 3生产组装 4其它", dataType = "Integer", required = true)
    private Integer type;

    @NotNull(code = ValidationExceptionCode.STATUS_IS_EMPTY, message = "出库单状态不能为空")
    @ApiModelProperty(value = "出库单状态 0未出库 1已出库(历史记录查询)", dataType = "Integer", required = true)
    private Integer status;

}
