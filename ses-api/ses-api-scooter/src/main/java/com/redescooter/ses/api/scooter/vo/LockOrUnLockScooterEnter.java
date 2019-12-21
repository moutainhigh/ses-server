package com.redescooter.ses.api.scooter.vo;


import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.*;

/**
 * @description: LockOrUnLockScooterEnter
 * @author: Darren
 * @create: 2019/02/19 19:08
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockOrUnLockScooterEnter extends GeneralEnter {
    @NotEmpty(code = ValidationExceptionCode.EVENT_IS_EMPTY, message =
            "lock event is empty.")
    private String event;
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message =
            "scooterId is empty.")
    private Long scooterId;
    @NotNull(code = ValidationExceptionCode.LONGITUDE_IS_EMPTY, message =
            "userLongitude is empty.")
    private String userLongitude;
    @NotNull(code = ValidationExceptionCode.LATITUDE_IS_EMPTY, message =
            "userLatitude is empty.")
    private String userLatitude;

    private Boolean bluetoothCommunication=Boolean.FALSE;


}
