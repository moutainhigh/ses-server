package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: ScooterRepairEnter
 * @author: Alex
 * @create: 2019/03/22 16:09
 */
@Getter
@Setter
public class ScooterRepairEnter extends GeneralEnter {
    @NotNull(code = ValidationExceptionCode.SCOOTERID_IS_EMPTY, message = "scooter id is not empty.")
            private Long scooterId;
}
