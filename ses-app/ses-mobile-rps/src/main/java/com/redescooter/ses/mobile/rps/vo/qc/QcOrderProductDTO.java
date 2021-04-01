package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 质检单产品信息 DTO
 * @author assert
 * @date 2021/1/26 9:53
 */
@Data
@ApiModel(value = "质检单产品信息")
public class QcOrderProductDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id(产品id)", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "质检单id", dataType = "Long")
    private Long qcId;

    @ApiModelProperty(value = "bomId", dataType = "Long")
    private Long bomId;

    @ApiModelProperty(value = "产品名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "产品编号", dataType = "String")
    private String number;

    @ApiModelProperty("部件类型，6：仪表盘")
    private Integer partsType;

    @ApiModelProperty(value = "颜色名称(车辆使用)", dataType = "String")
    private String colorName;

    @ApiModelProperty(value = "颜色值(车辆使用)", dataType = "String")
    private String colorValue;

    @ApiModelProperty(value = "车型(车辆使用)", dataType = "String")
    private String groupName;

    @ApiModelProperty(value = "是否有序列号 true/false", dataType = "Boolean")
    private Boolean idClass;

    @ApiModelProperty(value = "质检数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "待质检数量(待质检列表展示)", dataType = "Integer")
    private Integer pendingQcQty;

    @ApiModelProperty(value = "合格数量", dataType = "Integer")
    private Integer qualifiedQty;

    @ApiModelProperty(value = "不合格数量", dataType = "Integer")
    private Integer unqualifiedQty;

}
