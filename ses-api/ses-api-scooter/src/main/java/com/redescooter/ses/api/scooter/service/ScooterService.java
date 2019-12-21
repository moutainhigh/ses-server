package com.redescooter.ses.api.scooter.service;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.scooter.vo.*;
import com.redescooter.ses.api.scooter.vo.bo.SaveScooterBo;

import java.util.List;

/**
 * @description: ScooterService
 * @author: Darren
 * @create: 2019/01/22 15:09
 */
public interface ScooterService {

    /**
     * 添加scooter
     *
     * @param scooterEnters
     * @return
     */
    void saveScooter(List<SaveScooterBo> scooterEnters);

    /**
     * 获取scooter的基本信息
     *
     * @param enter
     * @return
     */
    GetScooterBasicInfoResult getScooterBasicInfo(GetScooterBasicInfoEnter enter);

    /**
     * 获取scooter的基本信息
     *
     * @param enter
     * @return
     */
    List<GetScooterBasicInfoResult> getScooterBasicInfoList(List<Long> enter);

    /**
     * 获取 AVAILABLE_STATUS 状态
     *
     * @param enter
     * @return
     */
    ScooterStatusResult scooterStatus(ScooterStatusEnter enter);

    /**
     * 获取scooter状态
     *
     * @param enter
     * @return
     */
    GetScooterStatusResult getScooterStatus(GetScooterStatusEnter enter);

    /**
     * 更新scooter状态
     *
     * @param enter
     * @return
     */
    GeneralResult updateScooterStatus(UpdateScooterStatusEnter enter);

    /**
     * 根据车牌号查询scooter的基本信息
     *
     * @param enter
     * @return
     */
    ScooterBasicInfoResult getScooterBasicInfoByScooterLicense(ScooterBasicInfoEnter enter);

    /**
     * 根据车辆id 计算车辆运行总天数
     * @param enter
     * @return
     */
    Integer calculationSooterSumDays(GetScooterBasicInfoEnter enter);
}
