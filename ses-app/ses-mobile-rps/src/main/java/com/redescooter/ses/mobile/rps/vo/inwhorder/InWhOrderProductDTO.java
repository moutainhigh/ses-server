package com.redescooter.ses.mobile.rps.vo.inwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 入库单产品详情返回结果对象 DTO
 * @author assert
 * @date 2021/1/18 10:23
 */
@Data
@ApiModel(value = "入库单产品详情返回结果对象")
public class InWhOrderProductDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id(产品id)", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "入库单id", dataType = "Long")
    private Long inWhId;

    @ApiModelProperty(value = "bomId", dataType = "Long")
    private Long bomId;

    @ApiModelProperty(value = "部件类型(部件使用) 1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU", dataType = "Integer")
    private Integer partsType;

    @ApiModelProperty(value = "产品名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "产品编号", dataType = "String")
    private String number;

    @ApiModelProperty(value = "颜色名称 - 车辆入库单使用", dataType = "String")
    private String colorName;

    @ApiModelProperty(value = "颜色值 - 车辆入库单使用", dataType = "String")
    private String colorValue;

    @ApiModelProperty(value = "车型(高速、低速) - 车辆入库单使用", dataType = "String")
    private String groupName;

    @ApiModelProperty(value = "是否有序列号 true/false", dataType = "Boolean")
    private Boolean idClass;

    @ApiModelProperty(value = "应入库数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "不合格数量", dataType = "Integer")
    private Integer unqualifiedQty;

    @ApiModelProperty(value = "质检数量", dataType = "Integer")
    private Integer qcQty;

}
