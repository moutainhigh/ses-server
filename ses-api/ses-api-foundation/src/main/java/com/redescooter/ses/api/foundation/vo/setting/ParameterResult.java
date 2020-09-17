package com.redescooter.ses.api.foundation.vo.setting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "参数配置列表", description = "参数配置列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ParameterResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "参数名称")
    private String parameterName;

    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "key")
    private String key;

    @ApiModelProperty(value = "value")
    private String value;

    @ApiModelProperty(value = "是否启用")
    private int enable;

    @ApiModelProperty(value = "创建人")
    private Long createdById;

    @ApiModelProperty(value = "创建人")
    private String createdByFirtName;

    @ApiModelProperty(value = "创建人")
    private String createdByLastName;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long upadtedById;

    @ApiModelProperty(value = "更新人")
    private String upadtedByFirtName;

    @ApiModelProperty(value = "更新人")
    private String upadtedByLastName;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}
