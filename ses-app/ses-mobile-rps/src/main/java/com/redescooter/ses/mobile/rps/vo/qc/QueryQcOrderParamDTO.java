package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.mobile.rps.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询质检单列表入参对象 DTO
 * @author assert
 * @date 2021/1/25 17:35
 */
@Data
@ApiModel(value = "查询质检单列表入参对象")
public class QueryQcOrderParamDTO extends PageEnter {

    @NotNull(code = ValidationExceptionCode.QC_ORDER_TYPE_IS_EMPTY, message = "质检单类型不能为空")
    @ApiModelProperty(value = "质检单类型 1整车 2组装件 3部件", dataType = "Integer", required = true)
    private Integer orderType;

    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "质检类型不能为空")
    @ApiModelProperty(value = "质检类型 1采购 2退料 3生产 4发货 5返修 6其他", dataType = "Integer", required = true)
    private Integer type;

    @NotNull(code = ValidationExceptionCode.STATUS_IS_EMPTY, message = "质检单状态不能为空")
    @ApiModelProperty(value = "质检单状态 0未质检 1质检完成(历史记录查询)", dataType = "Integer", required = true)
    private Integer status;

}
