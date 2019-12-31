package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MobileBScooterChartResult
 * @description: MobileBScooterChartResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/31 18:44
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class MobileBScooterChartResult extends GeneralEnter {

    private String totalMileage;

    private String totalCo2;

    private String totalMoney;

//    Map<String,>
}
