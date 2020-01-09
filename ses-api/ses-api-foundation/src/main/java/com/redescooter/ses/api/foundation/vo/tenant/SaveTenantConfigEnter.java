package com.redescooter.ses.api.foundation.vo.tenant;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveTenantConfigEnter
 * @description: SaveTenantConfigEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 14:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class SaveTenantConfigEnter extends GeneralEnter {

    @ApiModelProperty(value = "租户配置主键")
    private Long tenantConfigId;

    @ApiModelProperty(value = "租户行业")
    private  String industry;

    @ApiModelProperty(value = "开始日期，传 周一至周日 对应 1-7 编号 ")
    private String beginWeek;

    @ApiModelProperty(value = "结束日期，传 周一至周日 对应 1-7 编号 ")
    private String endWeek;

    @ApiModelProperty(value = "开始时间")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "估计持续时间,单位分钟")
    private Long estimatedDuration;

    @ApiModelProperty(value = "配送范围,单位km")
    private Long distributionRange;

    @ApiModelProperty(value = "租户Id，生成默认配置使用",hidden=true)
    private Long inputTenantId;

    @ApiModelProperty(value = "租户Id，生成默认配置使用",hidden=true)
    private Boolean tenantDefaultConfig=Boolean.FALSE;

}
