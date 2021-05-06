package com.redescooter.ses.web.ros.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Courtney
 * @since 2021-04-30
 */
@ApiModel(value = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(value = Include.NON_NULL)
public class OpeOperatingAccount {

  private static final long serialVersionUID = 1L;

   @ApiModelProperty(value = "主键")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;


   @ApiModelProperty(value = "邮箱")
   @TableField(value = "operating_email")
   private String operatingEmail;

    @ApiModelProperty(value = "逻辑删除(0:正常 1:删除)")
    @TableField(value = "dr")
    private Integer dr;


   @ApiModelProperty(value = "使用者")
   @TableField(value = "operating_user")
   private String operatingUser;

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
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  private Date createTime;

}