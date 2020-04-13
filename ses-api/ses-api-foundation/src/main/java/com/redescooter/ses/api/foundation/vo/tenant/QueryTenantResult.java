package com.redescooter.ses.api.foundation.vo.tenant;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:QueryTenantResult
 * @description: QueryTenantResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/24 16:37
 */
@ApiModel(value = "店铺信息", description = "店铺信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class QueryTenantResult extends GeneralResult {
    @ApiModelProperty(value="ID")
    private Long id;

    @ApiModelProperty(value="逻辑删除标识 0正常 1删除")
    private Integer dr;

    @ApiModelProperty(value="P_ID")
    private Long pId;

    @ApiModelProperty(value="租户名，即客户名")
    private String tenantName;

    @ApiModelProperty(value="邮件")
    private String email;

    @ApiModelProperty(value="状态")
    private String status;

    @ApiModelProperty(value="null")
    private Long countryId;

    @ApiModelProperty(value="city_Id")
    private Long cityId;

    @ApiModelProperty(value="null")
    private Long distrustId;

    @ApiModelProperty(value="司机数量")
    private Integer driverCounts;

    @ApiModelProperty(value="销售")
    private Long salesId;

    @ApiModelProperty(value="来源渠道 官网/Email/电话")
    private String tenantSource;

    @ApiModelProperty(value="租户类型 企业/个人")
    private String tenantType;

    @ApiModelProperty(value="租户行业")
    private String tenantIndustry;

    @ApiModelProperty(value="地址")
    private String address;

    @ApiModelProperty(value="联系人")
    private String contact;

    @ApiModelProperty(value="职位")
    private String position;

    @ApiModelProperty(value="租户编码")
    private String tenantCode;

    @ApiModelProperty(value="经度")
    private BigDecimal longitude;

    @ApiModelProperty(value="纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value="电话")
    private String tel1;

    @ApiModelProperty(value="电话")
    private String tel2;

    @ApiModelProperty(value="电话")
    private String tel3;

    @ApiModelProperty(value="邮件")
    private String email1;

    @ApiModelProperty(value="邮件")
    private String email2;

    @ApiModelProperty(value="邮件")
    private String email3;

    @ApiModelProperty(value="时区")
    private String timeZone;

    @ApiModelProperty(value="生效时间")
    private Date effectiveTime;

    @ApiModelProperty(value="激活时间")
    private Date activationTime;

    @ApiModelProperty(value="到期时间")
    private Date expireTime;

    @ApiModelProperty(value="创建人")
    private Long createdBy;

    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

}
