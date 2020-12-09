package com.redescooter.ses.mobile.client.service;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;

/**
 * 车辆相关业务接口
 * @author assert
 * @date 2020/11/18 15:29
 */
public interface ScooterService {

    /**
     * 查询车辆信息
     * @param: enter
     * @return: com.redescooter.ses.api.common.vo.scooter.BaseScooterResult
     * @author: assert
     * @date: 2020/11/17
     */
    BaseScooterResult getScooterInfo(GeneralEnter enter);

    /**
     * 车辆开关锁
     * @param: scooterLockDTO
     * @return: com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author: assert
     * @date: 2020/11/17
     */
    GeneralResult lock(ScooterLockDTO scooterLockDTO);

    /**
     * 车辆开关导航
     * @param: enter
     * @return: com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author: assert
     * @date: 2020/11/17
     */
    GeneralResult scooterNavigation(ScooterNavigationDTO enter);

}
