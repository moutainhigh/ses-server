package com.redescooter.ses.api.foundation.vo.setting;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:40
 *  @version：V ROS 1.7.1
 *  @Description:
 */
@ApiModel(value = "添加分组", description = "添加分组")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveGroupEnter extends GeneralEnter {

    @ApiModelProperty(value = "Id")
    private Long id;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "是否启用")
    private Boolean enable;
}
