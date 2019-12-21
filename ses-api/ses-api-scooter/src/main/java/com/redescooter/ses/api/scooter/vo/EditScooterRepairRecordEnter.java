package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: EditScooterRepairRecordEnter
 * @author: Alex
 * @create: 2019/04/15 18:29
 */
@Getter
@Setter
public class EditScooterRepairRecordEnter extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "scooter id is empty.")
    private Long scooterId;

    @NotEmpty(code = ValidationExceptionCode.REPAIT_SCOOTERSTATUS_IS_EMPTY, message = "repair scooterstatus is not empty.")
    private String status;
}
