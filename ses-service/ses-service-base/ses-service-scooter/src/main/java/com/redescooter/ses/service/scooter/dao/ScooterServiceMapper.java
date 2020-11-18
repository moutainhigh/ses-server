package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;
import org.apache.ibatis.annotations.Param;

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
}
