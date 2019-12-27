package com.redescooter.ses.service.scooter.dao;

import java.util.List;

import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;

/**
 * @ClassName:ScooterServiceMapper
 * @description: ScooterServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 15:13
 */
public interface ScooterServiceMapper {

    List<BaseScooterResult> scooterInfor(List<Long> enter);
}
