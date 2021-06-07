package com.redescooter.ses.api.hub.service.operation;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;

/**
 * @Description
 * @Author Chris
 * @Date 2021/6/7 13:23
 */
public interface FrWhAppService {

    /**
     * 登出
     */
    GeneralResult logout(GeneralEnter enter);

}
