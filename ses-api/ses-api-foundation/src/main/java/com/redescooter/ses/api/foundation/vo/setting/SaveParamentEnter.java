package com.redescooter.ses.api.foundation.vo.setting;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "参数保存编辑", description = "参数保存编辑")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SaveParamentEnter extends GeneralEnter {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "参数名")
    @NotNull(code = ValidationExceptionCode.NAME_IS_EMPTY, message = "参数名称为空")
    private String parameterName;

    @ApiModelProperty(value = "分组Id")
    @NotNull(code = ValidationExceptionCode.GROUP_ID_IS_EMPTY, message = "分组Id为空")
    private Long groupId;

    @ApiModelProperty(value = "key")
    @NotNull(code = ValidationExceptionCode.KEY_IS_EMPTY, message = "主键名称为空")
    private String key;

    @ApiModelProperty(value = "value")
    @NotNull(code = ValidationExceptionCode.VALUE_IS_EMPTY, message = "属性值为空")
    private String value;

    @ApiModelProperty(value = "是否启用")
    @NotNull(code = ValidationExceptionCode.NAME_IS_EMPTY, message = "启用按钮为空")
    private int enable;

    @ApiModelProperty(value = "系统类型", hidden = true)
    private SystemTypeEnums systemType = SystemTypeEnums.REDE_ROS;

    @ApiModelProperty(value = "描述")
    private String desc;
}
