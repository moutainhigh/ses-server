package com.redescooter.ses.web.ros.vo.specificat.dto;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 新增规格类型入参 DTO
 * @author assert
 * @date 2020/12/7 16:34
 */
@Data
@ApiModel(value = "新增规格类型入参")
public class InsertSpecificTypeParamDTO extends GeneralEnter {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "规格名称", dataType = "String", required = true)
    private String specificatName;

    @ApiModelProperty(value = "规格编码", dataType = "String")
    private String code;

    @ApiModelProperty(value = "分组id", dataType = "Long", required = true)
    private Long groupId;

    @ApiModelProperty(value = "规格自定义项分组集合(前端传入json数组)", dataType = "jsonArray", required = true)
    private String st;

    @ApiModelProperty(value = "创建人", dataType = "Long")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private Date createdTime;

    @ApiModelProperty(value = "更新人", dataType = "Long")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间", dataType = "Date")
    private Date updatedTime;

}
