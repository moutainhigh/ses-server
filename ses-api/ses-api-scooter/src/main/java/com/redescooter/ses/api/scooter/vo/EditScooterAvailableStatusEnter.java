package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: EditScooterAvailableStatusEnter
 * @author: Alex
 * @create: 2019/04/29 10:07
 */
@Getter
@Setter
public class EditScooterAvailableStatusEnter extends GeneralEnter {
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "ScooterId is not empty")
    private Long scooterId;
    @NotEmpty(code = ValidationExceptionCode.SCOOTER_STATUS_IS_EMPTY,message ="scooter status is not empty.")
    private String status;
}
