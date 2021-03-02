package com.redescooter.ses.mobile.rps.vo.combinorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询组装单列表入参对象 DTO
 * @author assert
 * @date 2021/1/27 9:59
 */
@Data
@ApiModel(value = "查询组装单列表入参对象")
public class QueryCombinationOrderParamDTO extends PageEnter {

    @NotNull(code = ValidationExceptionCode.COMBINATION_ORDER_TYPE_IS_EMPTY, message = "组装单类型不能为空")
    @ApiModelProperty(value = "组装单类型 1车辆组装单 2组装件组装单", dataType = "Integer", required = true)
    private Integer orderType;

    @NotNull(code = ValidationExceptionCode.STATUS_IS_EMPTY, message = "组装单状态不能为空")
    @ApiModelProperty(value = "组装单状态 0未完成(备料完成、组装中、组装完成、质检中) 1完成(质检完成)", dataType = "Integer", required = true)
    private Integer status;

}
