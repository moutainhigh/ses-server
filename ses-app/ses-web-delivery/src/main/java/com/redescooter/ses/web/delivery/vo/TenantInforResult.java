package com.redescooter.ses.web.delivery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:TenantInforResult
 * @description: TenantInforResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/09 14:35
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class TenantInforResult extends GeneralResult {

    @ApiModelProperty(value="ID")
    private Long id;

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

    @ApiModelProperty(value="时区")
    private String timeZone;

    @ApiModelProperty(value = "生效时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date effectiveTime;

    @ApiModelProperty(value = "激活时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date activationTime;

    @ApiModelProperty(value = "到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date expireTime;

    @ApiModelProperty(value="创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;

    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date updatedTime;

    @ApiModelProperty(value="引导页开关")
    private Boolean pageBootTips;

    @ApiModelProperty(value="头像")
    private String avatar;

    @ApiModelProperty(value = "行业，1餐厅，2快递")
    private String industry;

    @ApiModelProperty(value = "租户配置Id")
    private Long tenantConfigId;

    @ApiModelProperty(value = "开始日期，传 周一至周日 对应 1-7 编号 ")
    private String startWeek;

    @ApiModelProperty(value = "结束日期，传 周一至周日 对应 1-7 编号 ")
    private String endWeek;

    @ApiModelProperty(value = "开始时间")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "超时时间")
    private Long duration;

    @ApiModelProperty(value = "配送范围")
    private Long around;
}
