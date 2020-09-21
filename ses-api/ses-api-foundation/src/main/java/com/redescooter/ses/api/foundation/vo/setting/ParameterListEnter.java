package com.redescooter.ses.api.foundation.vo.setting;

import com.redescooter.ses.api.common.enums.base.SystemTypeEnums;
import com.redescooter.ses.api.common.vo.base.PageEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "参数列表", description = "参数列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ParameterListEnter extends PageEnter {
    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "系统类型", hidden = true)
    private SystemTypeEnums systemType;
}
