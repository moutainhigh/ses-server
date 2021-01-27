package com.redescooter.ses.mobile.rps.vo.inwhorder;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询入库单列表入参对象 DTO
 * @author assert
 * @date 2021/1/15 17:49
 */
@Data
@ApiModel(value = "查询入库单列表入参对象")
public class QueryInWhOrderParamDTO extends PageEnter {

    @NotNull(code = ValidationExceptionCode.IN_WH_ORDER_TYPE_IS_EMPTY, message = "出库单类型不能为空")
    @ApiModelProperty(value = "入库单类型 1整车 2组装件 3部件", dataType = "Integer", required = true)
    private Integer orderType;

    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "入库类型不能为空")
    @ApiModelProperty(value = "入库类型 1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6:报废入库，7:调拨入库",
            dataType = "Integer", required = true)
    private Integer type;

    @NotNull(code = ValidationExceptionCode.STATUS_IS_EMPTY, message = "入库单状态不能为空")
    @ApiModelProperty(value = "入库单状态 0未入库 1已入库(历史记录查询)", dataType = "Integer", required = true)
    private Integer status;

}
