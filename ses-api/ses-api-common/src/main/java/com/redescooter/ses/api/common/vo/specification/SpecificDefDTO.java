package com.redescooter.ses.api.common.vo.specification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 规格自定义项 DTO
 * @author assert
 * @date 2020/12/7 16:59
 */
@Data
@ApiModel(value = "规格自定义项")
public class SpecificDefDTO implements Serializable {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "所属规格id", dataType = "Long")
    private Long specificatId;

    @ApiModelProperty(value = "自定义项分组id(入参时无需传递)", dataType = "Long")
    private Long specificDefGroupId;

    @ApiModelProperty(value="自定义名称", dataType = "String", required = true)
    private String defName;

    @ApiModelProperty(value="自定义值", dataType = "String", required = true)
    private String defValue;

    @ApiModelProperty(value = "创建人", dataType = "Long")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private Date createdTime;

    @ApiModelProperty(value = "更新人", dataType = "Long")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间", dataType = "Date")
    private Date updatedTime;

}
