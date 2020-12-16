package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.scooter.vo.UpdateStatusEnter;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterLockReportedDTO;

import java.util.List;

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
    GeneralResult saveScooter(List<BaseScooterEnter> enter);

    /**
     * 修改 车辆状态
     *
     * @param enter
     * @return
     */
    GeneralResult updateStatus(UpdateStatusEnter enter);

    /**
     * 根据车牌号查询车辆信息
     *
     * @param enter
     * @return
     */
    List<BaseScooterResult> scooterInforByPlates(List<String> enter);
    /**
    * @Description
    * @Author: alex
    * @Date:   2020/11/16 6:01 下午
    * @Param:  id,scooterNo
    * @Return: BaseScooterResult
    * @desc: 车辆基本信息
    */
    BaseScooterResult scooterInfoByScooterNo(Long id,String scooterNo);

    /**
     * 根据scooterNo修改车辆锁状态 -- EMQ X
     * @param scooterLock
     * @return int
     * @author assert
     * @date 2020/11/23
     */
    int updateScooterStatusByJson(ScooterLockReportedDTO scooterLock);

    /**
     * 根据scooterId查询车辆实时信息
     * @param scooterId
     * @return com.redescooter.ses.api.common.vo.scooter.BaseScooterResult
     * @author assert
     * @date 2020/11/26
    */
    BaseScooterResult getScooterInfoById(Long scooterId);

    /**
     * 同步车辆数据
     * @param syncScooterData
     * @return int
     * @author assert
     * @date 2020/12/10
    */
    int syncScooterData(SyncScooterDataDTO syncScooterData);

    /**
     * 查询车辆数量
     * @param
     * @return int
     * @author assert
     * @date 2020/12/15
    */
    int countByScooter();

    /**
     * 同步车辆型号
     * @param id
     * @param scooterModel
     * @return int
     * @author assert
     * @date 2020/12/16
    */
    int syncScooterModel(Long id, Integer scooterModel);

    /**
     * 根据tabletSn查询车辆信息
     * @param tabletSn
     * @return com.redescooter.ses.api.common.vo.scooter.BaseScooterResult
     * @author assert
     * @date 2020/11/26
     */
    BaseScooterResult getScooterByTabletSn(String tabletSn);

}
