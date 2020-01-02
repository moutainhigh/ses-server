package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @description: MonthlyOrderResult
 * @author: Alex
 * @create: 2019/06/25 15:03
 */
@ApiModel(value = "月订单统计出参", description = "月订单统计出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MonthlyDeliveryChartResult extends GeneralResult {

    @ApiModelProperty(value = "完成总计")
    private int completeTotal;

    @ApiModelProperty(value = "超时完成总计")
    private int delayeCompleteTotal;

    @ApiModelProperty(value = "拒绝总计总计")
    private int declined;

    @ApiModelProperty(value = "取消总计")
    private int cancelTotal;

    @ApiModelProperty(value = "当天总计订单")
    private int total;

    @ApiModelProperty(value = "日期")
    private String times;
}
