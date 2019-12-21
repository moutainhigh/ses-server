package com.redescooter.ses.api.scooter.service;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.scooter.vo.*;

/**
 * @description: ScooterIOTService
 * @author: Darren
 * @create: 2019/03/05 17:53
 */
public interface ScooterIOTService {

    /**
     * 锁定scooter
     *
     * @param enter
     * @return
     */
    ScooterIotResult lockOrUnlockScooter(LockOrUnLockScooterEnter enter);

    /**
     * 导航
     *
     * @param enter
     * @return
     */
    ScooterIotResult navigation(NavigationEnter enter);

    /**
     * OBD检测
     *
     * @param enter
     * @return
     */
    ObdResult obd(ObdEnter enter);


    ScooterIotResult lockOrLockScooterBox(LockOrLockScooterBoxEnter enter);

    GeneralResult editScooterAvailableStatus(EditScooterAvailableStatusEnter enter);
}
