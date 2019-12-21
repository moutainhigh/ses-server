package com.redescooter.ses.api.scooter.vo;


import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.*;


/**
 * @description: NavigationEnter
 * @author: Darren
 * @create: 2019/02/22 10:47
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavigationEnter extends GeneralEnter {
    @NotEmpty(code = ValidationExceptionCode.EVENT_IS_EMPTY, message =
            "Navigation event cannot be empty.")
    private String event;
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message =
            "scooterId cannot be empty")
    private Long scooterId;
    @NotEmpty(code = ValidationExceptionCode.LONGITUDE_IS_EMPTY, message =
            "userLongitude is  not empty.")
    private String userLongitude;
    @NotEmpty(code = ValidationExceptionCode.LATITUDE_IS_EMPTY, message =
            "userLatitude is not empty.")
    private String userLatitude;
    @NotEmpty(code = ValidationExceptionCode.DESTINATION_IS_NOT_EMPTY, message =
            "destination cannot be empty")
    private String destination;
    @NotEmpty(code = ValidationExceptionCode.DESTINATION_LONGITUDE_IS_NOT_EMPTY, message =
            "destination Longitude cannot be empty")
    private String destinationLongitude;
    @NotEmpty(code = ValidationExceptionCode.DESTINATION_LATITUDE_IS_NOT_EMPTY, message =
            "destinationLatitude cannot be empty.")
    private String destinationLatitude;

    private Boolean bluetoothCommunication=Boolean.FALSE;
}
