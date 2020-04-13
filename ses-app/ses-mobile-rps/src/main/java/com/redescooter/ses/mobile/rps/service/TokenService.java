package com.redescooter.ses.mobile.rps.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;

public interface TokenService {
    UserToken checkAndGetSession(GeneralEnter enter);
}
