package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.scooter.vo.UpdateStatusEnter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:ScooterService
 * @description: ScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/26 20:34
 */
public interface ScooterService {
    /**
     * 车辆信息
     *
     * @param enter
     * @return
     */
    List<BaseScooterResult> scooterInfor(List<Long> enter);

    /**
     * 保存车辆
     *
     * @param enter
     * @return
     */
    GeneralResult saveScooter(BaseScooterResult enter);

    /**
     * 修改 车辆状态
     *
     * @param enter
     * @return
     */
    GeneralResult updateStatus(UpdateStatusEnter enter);
    /**
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/13 11:04
    * @Param:  ids
    * @Return: map
    * @desc:  车辆型号列表
    */
    Map<String, Integer> scooterTypeList(List<Long> ids);
}
