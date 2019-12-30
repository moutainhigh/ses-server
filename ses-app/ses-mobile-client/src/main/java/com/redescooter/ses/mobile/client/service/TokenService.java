package com.redescooter.ses.mobile.client.service;

import com.redescooter.ses.api.common.vo.base.BaseSendMailEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.foundation.vo.user.UserToken;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/12/2019 11:12 下午
 * @ClassName: TokenService
 * @Function: TODO
 */
public interface TokenService {

    UserToken checkAndGetSession(GeneralEnter enter);

    GeneralResult sendCode(BaseSendMailEnter enter);
}
