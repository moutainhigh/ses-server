package com.redescooter.ses.api.scooter.vo;


import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.*;

/**
 * @description: LockOrLockScooterBoxEnter
 * @author: Darren
 * @create: 2019/03/21 16:44
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockOrLockScooterBoxEnter extends GeneralEnter {

    @NotEmpty(code = ValidationExceptionCode.EVENT_IS_EMPTY, message =
            "lockbox event is empty.")
    private String event;
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message =
            "scooterId is empty.")
    private Long scooterId;
    @NotEmpty(code = ValidationExceptionCode.LONGITUDE_IS_EMPTY, message =
            "userLongitude is empty.")
    private String userLongitude;
    @NotEmpty(code = ValidationExceptionCode.LATITUDE_IS_EMPTY, message =
            "userLatitude is not empty.")
    private String userLatitude;

    private Boolean bluetoothCommunication=Boolean.FALSE;
}
