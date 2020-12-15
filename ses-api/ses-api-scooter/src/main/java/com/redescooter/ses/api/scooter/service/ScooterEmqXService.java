package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.ScooterLockDTO;
import com.redescooter.ses.api.common.vo.scooter.ScooterNavigationDTO;
import com.redescooter.ses.api.common.vo.version.ReleaseAppVersionParamDTO;
import com.redescooter.ses.api.scooter.vo.emqx.SetScooterModelPublishDTO;


/**
 * 车辆EMQ X服务相关接口
 * @author assert
 * @date 2020/11/18 18:35
 */
public interface ScooterEmqXService {

    /**
     * 车辆开关锁 -- 通过EMQ X进行通知到平板端
     * @param scooterLockDTO
     * @param scooterId
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2020/11/19
     */
    GeneralResult lock(ScooterLockDTO scooterLockDTO, Long scooterId);

    /**
     * 车辆开关导航 -- 通过EMQ X进行通知到平板端
     * @param enter
     * @param scooterId
     * @param userServiceType 用户业务类型 1-B端 2-C端
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2020/11/19
     */
    GeneralResult scooterNavigation(ScooterNavigationDTO enter, Long scooterId, Integer userServiceType);

    /**
     * 车辆平板批量升级 -- 通过EMQ X进行通知到平板端
     * @param paramDTO
     * @return void
     * @author assert
     * @date 2020/12/4
    */
    void updateScooterTablet(ReleaseAppVersionParamDTO paramDTO);

    /**
     * 设置车辆型号 -- 通过EMQ X进行通知到平板端
     * @param publishDTO
     * @return com.redescooter.ses.api.common.vo.base.GeneralResult
     * @author assert
     * @date 2020/12/1
    */
    GeneralResult setScooterModel(SetScooterModelPublishDTO publishDTO);

}
