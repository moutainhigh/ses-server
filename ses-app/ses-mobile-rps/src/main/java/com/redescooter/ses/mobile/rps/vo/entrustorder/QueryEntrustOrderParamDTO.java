package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 委托单列表查询入参对象 DTO
 * @author assert
 * @date 2021/1/4 9:56
 */
@Data
@ApiModel(value = "委托单列表查询入参对象")
public class QueryEntrustOrderParamDTO extends PageEnter {

    @ApiModelProperty(value = "委托单类型，1：整车，2：组装件，3：部件", dataType = "Integer", required = true)
    private Integer type;

    @ApiModelProperty(value = "委托单状态 0：待发货 1：已发货", dataType = "Integer", required = true)
    private Integer status;

}
