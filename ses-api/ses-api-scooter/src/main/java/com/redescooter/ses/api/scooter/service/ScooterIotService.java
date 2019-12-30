package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.IotScooterEnter;

/**
 * @ClassName:ScooterIotService
 * @description: ScooterIotService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/26 19:58
 */
public interface ScooterIotService {
    /**
     * 导航接口
     * @param enter
     * @return
     */
    GeneralResult navigation(IotScooterEnter enter);

    /**
     * 车锁
     * @param enter
     * @return
     */
    GeneralResult lock(IotScooterEnter enter);

    /**
     * obd 接口
     *
     * @param enter
     * @return
     */
    GeneralResult obd(IotScooterEnter enter);

    /**
     * 开解锁后备箱接口
     *
     * @param enter
     * @return
     */
    GeneralResult box(IotScooterEnter enter);
}
