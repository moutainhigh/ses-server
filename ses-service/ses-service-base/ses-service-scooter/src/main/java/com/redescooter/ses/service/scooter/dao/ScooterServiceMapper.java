package com.redescooter.ses.service.scooter.dao;

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
     * 根据车牌号查询车辆信息
     *
     * @param enter
     * @return
     */
    List<BaseScooterResult> scooterInforByplates(List<String> enter);
}
