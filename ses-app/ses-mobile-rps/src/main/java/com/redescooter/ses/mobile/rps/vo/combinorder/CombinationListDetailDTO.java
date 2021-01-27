package com.redescooter.ses.mobile.rps.vo.combinorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 组装清单详情对象 DTO
 * @author assert
 * @date 2021/1/27 15:19
 */
@Data
@ApiModel(value = "组装清单详情对象")
public class CombinationListDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "组装单id", dataType = "Long")
    private Long combinId;

    @ApiModelProperty(value = "所需部件总数", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "产品名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "产品编号", dataType = "String")
    private String number;

    @ApiModelProperty(value = "颜色名称(车辆使用)", dataType = "String")
    private String colorName;

    @ApiModelProperty(value = "颜色值(车辆使用)", dataType = "String")
    private String colorValue;

    @ApiModelProperty(value = "车型(车辆使用)", dataType = "String")
    private String groupName;

    @ApiModelProperty(value = "组装清单状态 0待组装 1已组装", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "组装清单类型 1车辆 2组装件", dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "组装单产品部件信息")
    private List<CombinationOrderPartsDTO> combinationOrderPartsList;

}
