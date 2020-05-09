package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 8/1/2020 3:59 下午
 * @ClassName: AllMobileBScooterChartResult
 * @Function: TODO
 */
@ApiModel(value = "订单数据出参", description = "订单数据出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class AllMobileBScooterChartResult extends GeneralResult {
    @ApiModelProperty(value = "全部统计")
    private Map<String, MobileBScooterChartResult> allMap = new HashMap<>();
}
