package com.redescooter.ses.api.hub.vo.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OperatingAccountListResult  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "逻辑删除(0:正常 1:删除)")
    private Integer dr;

    @ApiModelProperty(value = "使用者")
    private String name;

    @ApiModelProperty(value = "邮箱")
    private String loginName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "部门")
    private String deptName;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;
}
