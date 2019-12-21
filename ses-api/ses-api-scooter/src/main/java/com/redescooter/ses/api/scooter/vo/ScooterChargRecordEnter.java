package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: ScooterChargRecordEnter
 * @author: Alex
 * @create: 2019/03/22 16:03
 */
@Getter
@Setter
public class ScooterChargRecordEnter extends GeneralEnter {
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message =
            "scooter Id is empty.")
    private Long scooterId;
}
