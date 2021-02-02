package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 委托单列表查询返回结果对象 DTO
 * @author assert
 * @date 2021/1/4 10:01
 */
@Data
@ApiModel(value = "委托单列表查询返回结果对象")
public class QueryEntrustOrderResultDTO extends GeneralResult {

    @ApiModelProperty(value = "委托单id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "委托单号", dataType = "String")
    private String entrustNo;

    @ApiModelProperty(value = "发货数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "委托单类型，1：整车，2：组装件，3：部件", dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "委托单状态，0：待发货 10：待签收 20：已签收", dataType = "Integer")
    private Integer status;

}
