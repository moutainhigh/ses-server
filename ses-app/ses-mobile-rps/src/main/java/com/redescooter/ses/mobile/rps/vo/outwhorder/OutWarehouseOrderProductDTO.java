package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 出库单产品信息 DTO
 * @author assert
 * @date 2021/1/4 18:27
 */
@Data
@ApiModel(value = "出库单产品信息")
public class OutWarehouseOrderProductDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id(产品id)", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "出库单id", dataType = "Long")
    private Long outWhId;

    @ApiModelProperty(value = "产品名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "产品编号", dataType = "String")
    private String number;

    @ApiModelProperty(value = "颜色名称 - 车辆出库单使用", dataType = "String")
    private String colorName;

    @ApiModelProperty(value = "颜色值 - 车辆出库单使用", dataType = "String")
    private String colorValue;

    @ApiModelProperty(value = "车型(高速、低速) - 车辆出库单使用", dataType = "String")
    private String groupName;

    @ApiModelProperty(value = "是否有序列号 true/false", dataType = "Boolean")
    private Boolean idClass;

    @ApiModelProperty(value = "出库总数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "已出库数量", dataType = "Integer")
    private Integer alreadyOutWhQty;

}
