package com.redescooter.ses.api.common.vo.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "公共参数", description = "公共参数")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class GeneralEnter implements Serializable {

    @ApiModelProperty(value = "序列号", hidden = true)
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请求id", hidden = true)
    private String requestId;

    @ApiModelProperty(value = "系统id", hidden = true)
    private String systemId;

    @ApiModelProperty(value = "APPid", hidden = true)
    private String appId;

    @ApiModelProperty(value = "token值", hidden = true)
    private String token;

    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "租户id", hidden = true)
    private Long tenantId;

    @ApiModelProperty(value = "版本", hidden = true)
    private String version;

    @ApiModelProperty(value = "时间", hidden = true)
    private Long timestamp;

    @ApiModelProperty(value = "客户端类型", hidden = true)
    private String clientType;

    @ApiModelProperty(value = "客户端id", hidden = true)
    private String clientIp;

    @ApiModelProperty(value = "时区", hidden = true)
    private String timeZone;

    @ApiModelProperty(value = "语言", hidden = true)
    private String language;

    @ApiModelProperty(value = "城市", hidden = true)
    private String country;

    // 这个地方不用deptId 是因为所有的入参都继承了这个类  里面有很多deptId了
    @ApiModelProperty(value = "部门id",hidden = true)
    private Long opeDeptId;
}
