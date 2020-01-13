package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PageBootTipResult
 * @description: PageBootTipResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/13 18:33
 */
@ApiModel(value = "引导页出参", description = "引导页出参")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class PageBootTipResult extends GeneralResult {
    @ApiModelProperty(value = "引导页开关")
    private Boolean pageBootTips;

    @ApiModelProperty(value = "店铺头像")
    private String avatar;

    @ApiModelProperty(value = "店铺名字")
    private String tenantName;
}
