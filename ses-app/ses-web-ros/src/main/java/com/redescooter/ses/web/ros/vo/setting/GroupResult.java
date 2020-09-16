package com.redescooter.ses.web.ros.vo.setting;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

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
    private String dec;

    @ApiModelProperty(value = "是否启用")
    private Boolean enable;

    @ApiModelProperty(value = "创建人")
    private Long createdById;

    @ApiModelProperty(value = "创建人")
    private String createdByFirstName;

    @ApiModelProperty(value = "创建人")
    private String createdByLastName;

    @ApiModelProperty(value = "更新人")
    private Long updatedById;

    @ApiModelProperty(value = "更新人")
    private String updatedByFirstName;

    @ApiModelProperty(value = "更新人")
    private String updatedByLastName;
}
