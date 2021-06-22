package com.redescooter.ses.api.mobile.c.service;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.StringEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;

/**
 * 车辆C端相关业务接口
 * @author assert
 * @date 2020/11/18 16:44
 */
public interface ScooterMobileCService {

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

    /**
     * 登录后验证此账号下是否有车
     */
    BooleanResult checkScooter(GeneralEnter enter);

    /**
     * 登录后如果账号下没车进行绑车操作
     */
    GeneralResult bindScooter(StringEnter enter);
}
