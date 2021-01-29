package com.redescooter.ses.mobile.rps.vo.combinorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组装清单对象 DTO
 * @author assert
 * @date 2021/1/27 13:42
 */
@Data
@ApiModel(value = "组装清单对象")
public class CombinationListDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "组装单id", dataType = "Long")
    private Long combinId;

    @ApiModelProperty(value = "bomId", dataType = "Long")
    private Long bomId;

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

}
