package com.redescooter.ses.web.ros.vo.bom.sales;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * @ClassName PriceUnitResult
 * @Author Jerry
 * @date 2020/03/03 17:06
 * @Description:
 */
@ApiModel(value = "价格单位出参", description = "价格单位出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PriceUnitResult extends GeneralResult {

    private String code;

    private String vlue;

    private String unit;
}
