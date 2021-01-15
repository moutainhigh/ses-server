package com.redescooter.ses.mobile.rps.vo.inwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询入库单列表返回结果对象 DTO
 * @author assert
 * @date 2021/1/15 17:55
 */
@Data
@ApiModel(value = "查询入库单列表返回结果对象")
public class QueryInWhOrderResultDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "入库单号", dataType = "String")
    private String inWhNo;

    @ApiModelProperty(value = "入库数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "入库单类型 1车辆 2组装件 3部件", dataType = "Integer")
    private Integer orderType;

    @ApiModelProperty(value = "入库类型 1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6:报废入库，7:调拨入库",
            dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "入库单状态 1：草稿，:10：待质检，20：质检中，25：待入库，30：已入库", dataType = "Integer")
    private Integer status;

}
