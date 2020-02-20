package com.redescooter.ses.api.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.mobile.c.vo.ScooterNavigationEnter;

/**
 * @ClassName:ScooterService
 * @description: IdScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 10:37
 */
public interface IdScooterService {

    /**
     * @desc: 开启关闭导航
     * @param: enter
     * @retrn: GeneralResult
     * @auther: alex
     * @date: 2020/2/20 11:11
     * @Version: SAAS 1.2
     */
    GeneralResult scooterNavigation(ScooterNavigationEnter enter);
}
