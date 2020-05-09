package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassName:QueryMobileBOrderChartResult
 * @description: QueryMobileBOrderChartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/31 14:40
 */
@ApiModel(value = "订单数据出参", description = "订单数据出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryChartResult extends GeneralResult {

    @ApiModelProperty(value = "完成总计")
    private int completed = 0;

    @ApiModelProperty(value = "超时完成总计")
    private int timeoutComplete = 0;

    @ApiModelProperty(value = "拒绝总计总计")
    private int rejected = 0;

    @ApiModelProperty(value = "取消总计")
    private int cancel = 0;

    @ApiModelProperty(value = "当天总计订单")
    private int total = 0;

    @ApiModelProperty(value = "日期")

    private String times;

}
