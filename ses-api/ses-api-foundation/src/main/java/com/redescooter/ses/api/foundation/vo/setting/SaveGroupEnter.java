package com.redescooter.ses.api.foundation.vo.setting;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
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
    @NotNull(code = ValidationExceptionCode.NAME_IS_EMPTY, message = "名称为空")
    private String groupName;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "是否启用", allowableValues = "0,1")
    @NotNull(code = ValidationExceptionCode.ENABLE_IS_EMPTY, message = "启用按钮为空")
    private String enable;
}
