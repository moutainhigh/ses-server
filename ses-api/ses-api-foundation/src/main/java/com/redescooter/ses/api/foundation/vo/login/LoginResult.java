package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @description: LoginResult
 * @author: Darren
 * @create: 2019/01/16 09:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResult extends GeneralResult {

    private String token;

    private boolean noPassword;

    private Integer accountType;

    List<SelectAccountResult> accountSelectionList;
}
