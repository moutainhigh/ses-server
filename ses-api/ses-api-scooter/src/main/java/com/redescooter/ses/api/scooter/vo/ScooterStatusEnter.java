package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: ScooterStatusEnter
 * @author: Alex
 * @create: 2019/04/19 14:01
 */
@Getter
@Setter
public class ScooterStatusEnter extends GeneralEnter {
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "scooterId is empty.")
    private Long scooterId;
}
