package com.redescooter.ses.web.ros.vo.setting;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ParameterResult {

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
    private Boolean enable;

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
}
