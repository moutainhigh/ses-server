package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @ClassName:ScooterServiceMapper
 * @description: ScooterServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 15:13
 */
public interface ScooterServiceMapper {

    List<BaseScooterResult> scooterInfor(List<Long> enter);

    /**
     * 根据车牌号查询车辆信息
     *
     * @param enter
     * @return
     */
    List<BaseScooterResult> scooterInforByplates(List<String> enter);

    /**
     * @Description
     * @Author: alex
     * @Date: 2020/11/16 6:03 下午
     * @Param: id，scooterNo
     * @Return: BaseScooterResult
     * @desc: 车辆基本信息
     */
    BaseScooterResult scooterInfoByScooterNo(@Param("id") Long id, @Param("scooterNo") String scooterNo);

    /**
     * 根据Id更新车辆锁状态
     * @param status, id, userId
     * @return int
     * @author assert
     * @date 2020/11/19
     */
    int updateScooterStatusById(@Param("status") String status, @Param("id") Long id, @Param("userId") Long userId);

    /**
     * 根据Id查询车辆信息
     * @param id
     * @return com.redescooter.ses.api.common.vo.scooter.BaseScooterResult
     * @author assert
     * @date 2020/11/19
     */
    BaseScooterResult getScooterInfoById(Long id);

    /**
     * 根据tabletSn查询车辆锁状态
     * @param tabletSn
     * @return java.lang.String
     * @author assert
     * @date 2020/11/23
     */
    String getScooterStatusByTabletSn(String tabletSn);

    /**
     * 根据tabletSn查询车辆编号
     * @param tabletSn
     * @return java.lang.String
     * @author assert
     * @date 2020/11/24
     */
    String getScooterNoByTabletSn(String tabletSn);

    /**
     * 根据tabletSn更新车辆锁状态
     * @param tabletSn 平板序列号
     * @param status 车辆是否锁住 true是 false否
     * @param totalMiles 行驶总里程
     * @return int
     * @author assert
     * @date 2020/11/27
    */
    int updateScooterStatusAndTotalMilesByEcu(@Param("tabletSn") String tabletSn, @Param("status") String status,
                                      @Param("totalMiles") Integer totalMiles, @Param("date") Date date);

    /**
     * 查询所有车辆平板序列号
     * @param
     * @return java.util.List<java.lang.String>
     * @author assert
     * @date 2020/12/4
    */
    List<String> getAllScooterTabletSn();

    /**
     * 批量新增车辆信息
     * @param scooterList
     * @return int
     * @author assert
     * @date 2020/12/10
    */
    int batchInsertScooter(@Param("scooterList") List<ScoScooter> scooterList);

    /**
     * 查询车辆数量
     * @param
     * @return int
     * @author assert
     * @date 2020/12/15
     */
    int countByScooter();

    /**
     * 根据tabletSn修改车辆型号
     * @param tabletSn
     * @param scooterModel
     * @param updateTime
     * @return int
     * @author assert
     * @date 2020/12/16
     */
    int updateScooterModelByTabletSn(@Param("tabletSn") String tabletSn, @Param("scooterModel") Integer scooterModel,
                               @Param("updateTime") Date updateTime);

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
     * 查询当天生产车辆编号信息
     * @param
     * @return java.util.List<java.lang.String>
     * @author assert
     * @date 2021/2/1
     */
    List<String> getToDayScooterNos();

    /**
     * 修改sco_scooter的牌照
     */
    int updateScooterNo(@Param("id") Long id, @Param("licensePlate") String licensePlate);

}
