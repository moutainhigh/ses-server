package com.redescooter.ses.api.mobile.b.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.mobile.b.vo.LockEnter;

/**
 * @ClassName:ScooterService
 * @description: ScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 13:21
 */
public interface ScooterMobileService {
    /**
     * scooter 信息
     *
     * @param enter
     * @return
     */
    BaseScooterResult scooterInfor(GeneralEnter enter);

    /**
     * @param enter
     * @return
     */
    GeneralResult lock(LockEnter enter);
}
