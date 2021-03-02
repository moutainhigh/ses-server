package com.redescooter.ses.api.common.vo.specification;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 规格自定义项分组 DTO
 * @author assert
 * @date 2020/12/7 16:48
 */
@Data
@ApiModel(value = "规格自定义项分组")
public class SpecificDefGroupDTO implements Serializable {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "自定义项分组名称", dataType = "String", required = true)
    private String name;

    @ApiModelProperty(value = "所属规格id", dataType = "Long")
    private Long specificatId;

    @ApiModelProperty(value = "创建人", dataType = "Long")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private Date createdTime;

    @ApiModelProperty(value = "更新人", dataType = "Long")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间", dataType = "Date")
    private Date updatedTime;

    @ApiModelProperty(value = "规格自定义项", dataType = "List<SpecificDefDTO>")
    private List<SpecificDefDTO> groupList;

}
