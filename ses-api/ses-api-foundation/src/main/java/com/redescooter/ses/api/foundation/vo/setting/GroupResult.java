package com.redescooter.ses.api.foundation.vo.setting;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:35
 *  @version：V ROS 1.7.1
 *  @Description:
 */
@ApiModel(value = "分组列表", description = "分组列表")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class GroupResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "是否启用")
    private Boolean enable;

    @ApiModelProperty(value = "创建人")
    private Long createdById;

    @ApiModelProperty(value = "创建人")
    private String createdByFirstName;

    @ApiModelProperty(value = "创建人")
    private String createdByLastName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedById;

    @ApiModelProperty(value = "更新人")
    private String updatedByFirstName;

    @ApiModelProperty(value = "更新人")
    private String updatedByLastName;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}
