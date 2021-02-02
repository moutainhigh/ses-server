package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author assert
 * @date 2021/1/25 17:34
 */
@Data
@ApiModel(value = "查询质检单返回结果对象")
public class QueryQcOrderResultDTO extends GeneralResult {

    @ApiModelProperty(value = "质检单主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "质检单号", dataType = "String")
    private String qcNo;

    @ApiModelProperty(value = "质检数量", dataType = "Integer")
    private Integer qcQty;

    @ApiModelProperty(value = "质检单据类型 1车辆 2组装件 3部件", dataType = "Integer")
    private Integer orderType;

    @ApiModelProperty(value = "质检类型 1采购 2退料 3生产 4发货 5返修 6其他", dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "质检状态 1待质检 10质检中 20质检完成", dataType = "Integer")
    private Integer status;

}
