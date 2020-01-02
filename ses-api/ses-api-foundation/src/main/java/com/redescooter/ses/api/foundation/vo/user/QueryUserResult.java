package com.redescooter.ses.api.foundation.vo.user;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:QueryUserEnter
 * @description: QueryUserEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/01 18:42
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class QueryUserResult extends GeneralResult {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "系统ID")
    private String systemId;

    @ApiModelProperty(
            value = "应用ip，SAAS_WEB:SaaS配送，SAAS_APP:SaaS移动，SAAS_REPAIR_WEB:SaaS维修，SES_ROS:RedE办公系统，SES_DEVL:RedE开发系统")
    private String appId;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "登录类型")
    private Integer loginType;

    @ApiModelProperty(value = "状态 Normal,Lock,Cancel")
    private String status;

    @ApiModelProperty(value = "用户类型:1餐厅配送SaaS,2快递配送SaaS,3餐厅移动端,4快递移动端,5个人移动端,6维修端")
    private Integer userType;
}
