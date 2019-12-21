package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.scooter.exception.ValidationExceptionCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: ScooterBasicInfoEnter
 * @author: Alex
 * @create: 2019/04/30 07:55
 */
@Getter
@Setter
public class ScooterBasicInfoEnter extends GeneralEnter {
    @NotEmpty(code = ValidationExceptionCode.SCOOTER_LICENSE_IS_EMPTY,message = "scooter License is empty.")
    private String scooterLicense;
}
