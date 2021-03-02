package com.redescooter.ses.api.foundation.vo.setting;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/3/2 11:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ApiModel(value = "参数列表出参", description = "参数列表出参")
public class ParameterListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    @ApiModelProperty(value = "参数名")
    private String parameterName;

    @ApiModelProperty(value = "key值")
    private String paramKey;

    @ApiModelProperty(value = "属性值")
    private String paramValue;

}
