package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询单据类型数量入参 DTO
 * @author assert
 * @date 2020/12/30 17:36
 */
@Data
@ApiModel(value = "查询单据类型数量入参")
public class CountByOrderTypeParamDTO extends GeneralEnter {

    @ApiModelProperty(value = "出库单类型 1整车 2组装件 3部件", dataType = "Integer", required = true)
    private Integer type;

    @ApiModelProperty(value = "出库单状态 0未出库 1已出库", dataType = "Integer", required = true)
    private Integer status;

}
