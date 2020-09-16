package com.redescooter.ses.web.ros.vo.setting;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.*;

import io.swagger.annotations.*;

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
}
