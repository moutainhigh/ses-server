package com.redescooter.ses.web.ros.vo.specificat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 查询规格类型详情返回结果 DTO
 * @author assert
 * @date 2020/12/8 13:29
 */
@Data
@ApiModel(value = "查询规格类型详情返回结果")
public class QuerySpecificTypeDetailResultDTO {

    @ApiModelProperty(value = "主键", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "规格名称", dataType = "String")
    private String specificatName;

    @ApiModelProperty(value = "规格类型编码", dataType = "String")
    private String specificatCode;

    @ApiModelProperty(value = "分组id", dataType = "Long")
    private Long groupId;

    @ApiModelProperty(value = "所属分组名称", dataType = "String")
    private String groupName;

    @ApiModelProperty(value = "创建人名称", dataType = "String")
    private String createdName;

    @ApiModelProperty(value="创建时间", dataType = "Date")
    private Date createdTime;

    @ApiModelProperty(value = "更新人", dataType = "String")
    private String updatedName;

    @ApiModelProperty(value = "修改时间", dataType = "Date")
    private Date updatedTime;

    @ApiModelProperty(value = "规格自定义项分组集合", dataType = "List<SpecificDefGroupDTO>")
    private List<SpecificDefGroupDTO> specificDefGroupList;

}
