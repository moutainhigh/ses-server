package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:QueryMobileBOrderChartResult
 * @description: QueryMobileBOrderChartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/31 14:40
 */
@ApiModel(value = "订单统计柱状图列表", description = "订单统计柱状图列表")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryChartListResult extends GeneralResult {

    @ApiModelProperty(value = "全部统计")
    private Map<String, DeliveryChartResult> map;

}
