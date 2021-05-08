package com.redescooter.ses.service.hub.source.admin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel(value = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OperatingUpdateStatus {


    @ApiModelProperty(value = "邮箱")
    @TableField(value = "operating_email")
    private String operatingEmail;


    @ApiModelProperty(value = "状态")
    @TableField(value = "status")
    private Integer status;


}
