package com.redescooter.ses.api.scooter.vo;


import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.enums.ScooterActionResult;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.*;

import java.math.BigDecimal;

/**
 * @description: SaveScooterActionTraceEnter
 * @author: Darren
 * @create: 2019/02/20 10:30
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveScooterActionTraceEnter extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "Scooter Id cannot be empty.")
    private Long scooterId;
    @NotNull(code = ValidationExceptionCode.EVENT_IS_EMPTY, message = "Event cannot be empty.")
    private String event;
    @NotEmpty(code = ValidationExceptionCode.LONGITUDE_IS_EMPTY, message = "Longitude is empty.")
    private String userLongitude;
    @NotEmpty(code = ValidationExceptionCode.LATITUDE_IS_EMPTY, message = "Latitude is empty.")
    private String userLatitude;
    @NotNull(code = ValidationExceptionCode.ACTION_RESULT_IS_EMPTY, message = "Action result is empty.")
    private ScooterActionResult actionResult;
    @NotNull(code = ValidationExceptionCode.LONGITUDE_IS_EMPTY, message = "longitule is empty.")
    private BigDecimal longitule;
    @NotNull(code = ValidationExceptionCode.LATITUDE_IS_EMPTY, message = "latitude is empty.")
    private BigDecimal latitude;
    @NotNull(code = ValidationExceptionCode.GEOHASH_IS_EMPTY, message = "geohash is empty.")
    private String geohash;
    @NotNull(code = ValidationExceptionCode.BATTERY_IS_EMPTY, message = "battery is empty.")
    private Integer battery;

}
