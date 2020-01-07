package com.redescooter.ses.api.mobile.b.vo;

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
public class MobileBDeliveryChartResult extends GeneralResult {

    @ApiModelProperty(value = "非零统计")
    private List<MonthlyDeliveryChartResult> list;

    @ApiModelProperty(value = "全部统计")
    private List<MonthlyDeliveryChartResult> allList;

}
