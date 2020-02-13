package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;

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
    * @Description
    * @Author:  AlexLi
    * @Date:   2020/2/13 11:52
    * @Param:  ids
    * @Return: list
    * @desc: 车辆类型
    */
    List<CountByStatusResult> scooterTypeListByScooterIds(List<Long> ids);
}
