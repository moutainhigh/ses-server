package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.delivery.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;
import org.springframework.data.annotation.Id;

/**
 * @ClassName:UpdateTenantConfigEnter
 * @description: UpdateTenantConfigEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/09 15:14
 */
@ApiModel(value = "修改店铺配置入参", description = "修改店铺配置入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateTenantConfigEnter extends GeneralEnter {

    @ApiModelProperty(value = "租户配置Id")
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "Id  为空")
    private Long tenantConfigId;

    @ApiModelProperty(value = "行业 ，1 餐厅 2快递")
    @NotNull(code = ValidationExceptionCode.INDUSTRY_IS_EMPTY, message = "租户行业  为空")
    private String industry;

    @ApiModelProperty(value = "开始日期，传 周一至周日 对应 1-7 编号 ")
    @NotNull(code = ValidationExceptionCode.BEGIN_WEEK_IS_EMPTY, message = "营业开始日期为空")
    private String startWeek;

    @ApiModelProperty(value = "结束日期，传 周一至周日 对应 1-7 编号 ")
    @NotNull(code = ValidationExceptionCode.END_WEEK_IS_EMPTY, message = "营业结束日期为空")
    private String endWeek;

    @ApiModelProperty(value = "开始时间")
    @NotNull(code = ValidationExceptionCode.BEGIN_TIME_IS_EMPTY, message = "营业开始时间为空")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    @NotNull(code = ValidationExceptionCode.END_TIME_IS_EMPTY, message = "营业结束时间为空")
    private String endTime;

    @ApiModelProperty(value = "估计持续时间,单位分钟")
    private Long estimatedDuration;

    @ApiModelProperty(value = "配送范围,单位km")
    private Long distributionRange;

}
