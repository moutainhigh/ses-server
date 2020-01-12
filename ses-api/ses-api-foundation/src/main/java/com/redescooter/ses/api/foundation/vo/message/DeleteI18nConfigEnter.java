package com.redescooter.ses.api.foundation.vo.message;


import com.redescooter.ses.api.common.annotation.NotZero;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
import lombok.Data;

/**
 * description: DeleteI18nConfigEnter
 * author jerry.li
 * create: 2019-05-08 02:56
 */

@Data
public class DeleteI18nConfigEnter extends GeneralEnter {

    @NotZero(code = ValidationExceptionCode.I18NCONFIG_ID_NOTZERO, message = "deleteI18n id is not notzero.")
    private Long id;
}
