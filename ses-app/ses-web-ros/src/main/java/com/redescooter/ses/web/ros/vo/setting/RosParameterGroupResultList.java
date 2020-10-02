package com.redescooter.ses.web.ros.vo.setting;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author: alex
 * @Date: 2020/9/17 19:38
 * @version：V ROS 1.7.1
 * @Description:
 */
@ApiModel(value = "分组列表", description = "分组列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RosParameterGroupResultList extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "分组名称")
    private String name;
}
