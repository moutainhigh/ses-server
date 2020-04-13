package com.redescooter.ses.api.common.vo.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 20/12/2019 2:20 下午
 * @ClassName: BaseUserResult
 * @Function: TODO
 */
@ApiModel(value = "用户基础信息", description = "用户基础信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class BaseUserResult extends GeneralResult {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "系统ID")
    private String systemId;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "登录类型")
    private Integer loginType;

    @ApiModelProperty(value = "状态 Normal,Lock,Cancel")
    private String status;

    @ApiModelProperty(value = "用户类型:1餐厅配送SaaS,2快递配送SaaS,3餐厅移动端,4快递移动端,5个人移动端,6维修端")
    private Integer userType;

    @ApiModelProperty(value = "最后登录IP地址")
    private String lastLoginIp;

    @ApiModelProperty(value = "最后登录时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date lastLoginTime;

    @ApiModelProperty(value = "最后登录TOKEN")
    private String lastLoginToken;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date updatedTime;
}
