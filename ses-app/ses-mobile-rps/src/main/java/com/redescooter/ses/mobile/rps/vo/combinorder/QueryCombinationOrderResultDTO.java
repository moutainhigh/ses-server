package com.redescooter.ses.mobile.rps.vo.combinorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询组装单列表返回结果对象 DTO
 * @author assert
 * @date 2021/1/27 9:59
 */
@Data
@ApiModel(value = "查询组装单列表返回结果对象")
public class QueryCombinationOrderResultDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "组装单号", dataType = "String")
    private String combinNo;

    @ApiModelProperty(value = "组装单状态 1草稿 10待备料 20备料完成(待组装) 30组装中 40组装完成 45质检中 50质检完成",
            dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "组装数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "组装单类型 1车辆 2组装件", dataType = "Integer")
    private Integer type;

}
