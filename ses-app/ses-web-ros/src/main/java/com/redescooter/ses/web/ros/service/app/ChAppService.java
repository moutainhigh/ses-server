package com.redescooter.ses.web.ros.service.app;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.TokenResult;
import com.redescooter.ses.web.ros.dm.OpeWarehouseAccount;
import com.redescooter.ses.web.ros.vo.app.AppLoginEnter;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/28 10:38
 */
public interface ChAppService {

    /**
     * 登录
     */
    TokenResult login(AppLoginEnter enter);

    /**
     * 登出
     */
    GeneralResult logout(GeneralEnter enter);

    /**
     * 获得个人信息
     */
    OpeWarehouseAccount getUserInfo(GeneralEnter enter);



}
