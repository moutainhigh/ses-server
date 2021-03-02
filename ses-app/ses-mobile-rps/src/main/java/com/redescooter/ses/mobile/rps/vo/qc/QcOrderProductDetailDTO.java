package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.mobile.rps.vo.outwhorder.ProductSerialNumberDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 质检单产品详情 DTO
 * @author assert
 * @date 2021/1/26 13:12
 */
@Data
@ApiModel(value = "质检单产品详情")
public class QcOrderProductDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "bomId", dataType = "Long")
    private Long bomId;

    @ApiModelProperty(value = "质检单id", dataType = "Long")
    private Long qcId;

    @ApiModelProperty(value = "产品名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "产品编号", dataType = "String")
    private String number;

    @ApiModelProperty(value = "部件类型(部件使用) 1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination 6：ECU",
            dataType = "Integer")
    private Integer partsType;

    @ApiModelProperty(value = "颜色名称(车辆使用)", dataType = "String")
    private String colorName;

    @ApiModelProperty(value = "颜色值(车辆使用)", dataType = "String")
    private String colorValue;

    @ApiModelProperty(value = "车型(车辆使用)", dataType = "String")
    private String groupName;

    @ApiModelProperty(value = "质检数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "合格数量", dataType = "Integer")
    private Integer qualifiedQty;

    @ApiModelProperty(value = "不合格数量", dataType = "Integer")
    private Integer unqualifiedQty;

    @ApiModelProperty(value = "是否有序列号 true/false", dataType = "Boolean")
    private Boolean idClass;

    @ApiModelProperty(value = "产品序列号集合")
    private List<ProductSerialNumberDTO> productSerialNumberList;

}
