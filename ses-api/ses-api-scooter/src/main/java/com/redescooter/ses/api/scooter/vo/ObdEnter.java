package com.redescooter.ses.api.scooter.vo;


import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.*;

/**
 * @description: ObdEnter
 * @author: Darren
 * @create: 2019/02/22 10:49
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObdEnter extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message =
            "scooter id is empty.")
    private Long scooterId;
    @NotEmpty(code = ValidationExceptionCode.LONGITUDE_IS_EMPTY, message =
            "userLongitude is not empty.")
    private String userLongitude;
    @NotEmpty(code = ValidationExceptionCode.LATITUDE_IS_EMPTY, message =
            "userLatitude is not empty.")
    private String userLatitude;

}
