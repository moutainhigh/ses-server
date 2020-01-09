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
 * @ClassName:TenantConfigInfoResult
 * @description: TenantConfigInfoResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/09 14:48
 */
@ApiModel(value = "店铺配置信息出参", description = "店铺配置信息出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class TenantConfigInfoResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "租户id")
    private Long tenantId;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "时区")
    private String timeZone;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "")
    private String address;

    @ApiModelProperty(value = "状态 OPEN、CLOSE 营业中、打烊")
    private String status;

    @ApiModelProperty(value = "从周几开始")
    private String startWeek;

    @ApiModelProperty(value = "从周几结束")
    private String endWeek;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "超时预期值,单位min")
    private Long timeoutExpectde;

    @ApiModelProperty(value = "估计配送持续时间,单位min")
    private Long estimatedDuration;

    @ApiModelProperty(value = "配送范围,单位km")
    private Long distributionRange;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}
