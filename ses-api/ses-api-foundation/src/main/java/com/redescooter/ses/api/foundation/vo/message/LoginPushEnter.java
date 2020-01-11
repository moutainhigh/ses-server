package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
import lombok.Data;

/**
 * description: LoginPushEnter
 * author: jerry.li
 * create: 2019-05-22 14:39
 */

@Data
public class LoginPushEnter extends GeneralEnter {

    @NotNull(code = ValidationExceptionCode.REGISTRATIONID_EMPTY, message = "RegistrationId is empty.")
    private String registrationId;

    /**
     * 登录绑定：0，注销解绑：1
     */
    private Integer status = 0;
}
