package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询出库单列表返回结果对象 DTO
 * @author assert
 * @date 2021/1/4 16:45
 */
@Data
@ApiModel(value = "查询出库单列表返回结果对象")
public class QueryOutWarehouseOrderResultDTO extends GeneralResult {

    @ApiModelProperty(value = "出库单id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "出库单号", dataType = "String")
    private String outWarehouseNo;

    @ApiModelProperty(value = "出库数量", dataType = "int")
    private Integer qty;

    @ApiModelProperty(value = "出库类型 1发货 2组装 3补料 4其它", dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "出库单类型 1整车 2组装件 3部件", dataType = "Integer")
    private Integer orderType;

    @ApiModelProperty(value = "出库单状态 -1新建 10待出库 20已出库 30已取消", dataType = "Integer")
    private Integer status;

}
