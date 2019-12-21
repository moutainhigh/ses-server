package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.math.BigDecimal;

/**
 * @description: GetScooterStatusResult
 * @author: Darren
 * @create: 2019/03/07 09:54
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetScooterStatusResult extends GeneralResult {

    private String scooterNo;

    private BigDecimal scooterLongitule;

    private BigDecimal scooterLatitude;

    private String geohash;

    private Integer battery;
}
