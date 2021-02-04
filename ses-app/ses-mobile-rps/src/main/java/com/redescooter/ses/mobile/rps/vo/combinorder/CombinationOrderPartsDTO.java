package com.redescooter.ses.mobile.rps.vo.combinorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组装单产品部件信息 DTO
 * @author assert
 * @date 2021/1/27 10:15
 */
@Data
@ApiModel(value = "组装单产品部件信息")
public class CombinationOrderPartsDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "bomId", dataType = "Long")
    private Long bomId;

    @ApiModelProperty(value = "部件名称(中文名称)", dataType = "String")
    private String name;

    @ApiModelProperty(value = "部件编号", dataType = "String")
    private String number;

    @ApiModelProperty(value = "部件数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "是否有序列号 true/false", dataType = "Boolean")
    private Boolean idClass;

}
