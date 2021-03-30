package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.api.common.vo.scooter.SyncScooterDataDTO;
import com.redescooter.ses.api.scooter.vo.ScoScooterResult;
import com.redescooter.ses.api.scooter.vo.UpdateStatusEnter;
import com.redescooter.ses.api.scooter.vo.emqx.ScooterLockReportedDTO;

import java.math.BigDecimal;
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
     * @param syncScooterDataList
     * @return int
     * @author assert
     * @date 2020/12/10
    */
    int syncScooterData(List<SyncScooterDataDTO> syncScooterDataList);

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
     * @param tabletSn
     * @param scooterModel
     * @return int
     * @author assert
     * @date 2020/12/16
    */
    int syncScooterModel(String tabletSn, Integer scooterModel);

    /**
     * 根据tabletSn查询车辆信息
     * @param tabletSn
     * @return com.redescooter.ses.api.common.vo.scooter.BaseScooterResult
     * @author assert
     * @date 2020/11/26
     */
    BaseScooterResult getScooterByTabletSn(String tabletSn);

    /**
     * 根据tabletSn查询车辆id
     * @param tabletSn
     * @return java.lang.Long
     * @author assert
     * @date 2020/12/21
     */
    Long getScooterIdByTabletSn(String tabletSn);

    /**
     * 根据tabletSn查询车辆锁状态
     * @param tabletSn
     * @return java.lang.String
     * @author assert
     * @date 2020/11/23
     */
    String getScooterStatusByTabletSn(String tabletSn);

    /**
     * OMS删除车辆时  需要把对应的车辆数据也删除
     * @param sn
     */
    void deleteScooterData(String sn);

    /**
     * 查询当天生产车辆编号信息
     * @param
     * @return java.util.List<java.lang.String>
     * @author assert
     * @date 2021/2/1
    */
    List<String> getToDayScooterNos();

    /**
     * 根据scooterId找到最后一次的经纬度
     */
    Map<String, BigDecimal> getPositionByScooterId(Long scooterId);

    /**
     * 根据tabletSn查询sco_scooter
     */
    ScoScooterResult getScoScooterByTableSn(String tableSn);

    /**
     * 修改sco_scooter的scooter_no为整车rsn
     */
    GeneralResult updateScooterNo(Long id, String scooterNo, String licensePlate);

}
