package com.redescooter.ses.api.mobile.c.vo;

import java.util.HashMap;
import java.util.Map;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 8/1/2020 3:59 下午
 * @ClassName: AllMobileBScooterChartResult
 * @Function: TODO
 */
@ApiModel(value = "车辆数据出参", description = "车辆数据出参")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class AllScooterChartResult extends GeneralResult {
    @ApiModelProperty(value = "全部统计")
    private Map<String, ScooterChartResult> allMap = new HashMap<>();
}
