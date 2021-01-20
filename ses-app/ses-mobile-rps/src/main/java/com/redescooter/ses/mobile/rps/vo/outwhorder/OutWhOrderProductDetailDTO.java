package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 出库单产品详情 DTO
 * @author assert
 * @date 2021/1/5 11:53
 */
@Data
@ApiModel(value = "出库单产品详情")
public class OutWhOrderProductDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "产品主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "bomId", dataType = "Long")
    private Long bomId;

    @ApiModelProperty(value = "出库单id", dataType = "Long")
    private Long outWhId;

    @ApiModelProperty(value = "产品名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "产品类型(部件产品使用) 1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination", dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "产品编号", dataType = "String")
    private String number;

    @ApiModelProperty(value = "车型(高速、低速) - 车辆出库单使用", dataType = "String")
    private String groupName;

    @ApiModelProperty(value = "颜色 - 车辆出库单使用", dataType = "String")
    private String color;

    @ApiModelProperty(value = "应出库数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "已出库数量", dataType = "Integer")
    private Integer alreadyOutWhQty;

    @ApiModelProperty(value = "不合格数量", dataType = "Integer")
    private Integer unqualifiedQty;

    @ApiModelProperty(value = "质检数量", dataType = "Integer")
    private Integer qcQty;

    @ApiModelProperty(value = "是否有序列号 true/false", dataType = "Boolean")
    private Boolean idClass;

    @ApiModelProperty(value = "产品序列号集合信息")
    private List<ProductSerialNumberDTO> productSerialNumberList;

}
