package com.redescooter.ses.web.ros.vo.account;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class OperatingAccountListResult {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "逻辑删除(0:正常 1:删除)")
    @TableField(value = "dr")
    private Integer dr;

    @ApiModelProperty(value = "使用者")
    @TableField(value = "operating_user")
    private String operatingUser;

    @ApiModelProperty(value = "邮箱")
    @TableField(value = "operating_email")
    private String operatingEmail;

    @ApiModelProperty(value = "密码")
    @TableField(value = "operating_password")
    private String operatingPassword;

    @ApiModelProperty(value = "部门")
    @TableField(value = "operating_department")
    private String operatingDepartment;

    @ApiModelProperty(value = "状态")
    @TableField(value = "status")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;
}
