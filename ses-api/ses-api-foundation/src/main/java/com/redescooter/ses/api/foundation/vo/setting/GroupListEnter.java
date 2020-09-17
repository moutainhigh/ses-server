package com.redescooter.ses.api.foundation.vo.setting;

import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/9/16 18:29
 *  @version：V ROS 1.7.1
 *  @Description:
 */
@ApiModel(value = "分组列表", description = "分组列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class GroupListEnter extends PageEnter {

    @ApiModelProperty(value = "关键字", required = false)
    private String keyword;

    @ApiModelProperty(value = "系统类型", hidden = true)
    private SystemTypeEnums systemType = SystemTypeEnums.REDE_ROS;
}
