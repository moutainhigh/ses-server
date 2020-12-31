package com.redescooter.ses.web.ros.vo.specificat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 规格分组 DTO
 * @author assert
 * @date 2020/12/7 18:52
 */
@Data
@ApiModel(value = "规格分组")
public class SpecificGroupDTO {

    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 分组名称
     */
    @ApiModelProperty(value="分组名称")
    private String groupName;

    /**
     * 产品类型，1：整车，2：组装
     */
    @ApiModelProperty(value="产品类型 1整车 2组装")
    private Integer productionType;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

}
