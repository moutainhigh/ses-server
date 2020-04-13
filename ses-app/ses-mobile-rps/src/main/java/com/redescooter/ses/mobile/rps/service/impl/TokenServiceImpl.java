package com.redescooter.ses.mobile.rps.service.impl;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.vo.user.UserToken;
import com.redescooter.ses.mobile.rps.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public UserToken checkAndGetSession(GeneralEnter enter) {
        return null;
    }
}
