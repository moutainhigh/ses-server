package com.redescooter.ses.api.foundation.vo.tenant;

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

    @ApiModelProperty(value = "超时预期值,单位分钟")
    private Long timeoutExpectde;

    @ApiModelProperty(value = "估计持续时间,单位分钟")
    private Long estimatedDuration;

    @ApiModelProperty(value = "配送范围,单位km")
    private Long distributionRange;

    @ApiModelProperty(value = "租户Id，生成默认配置使用",hidden=true)
    private Long inputTenantId;

    @ApiModelProperty(value = "租户Id，生成默认配置使用",hidden=true)
    private Boolean tenantDefaultConfig;

}
