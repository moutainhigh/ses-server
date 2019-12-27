package com.redescooter.ses.api.scooter.service;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterResult;

/**
 * @ClassName:ScooterService
 * @description: ScooterService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/26 20:34
 */
public interface ScooterService {

    List<BaseScooterResult> scooterInfor(List<Long> enter);

    GeneralResult saveScooter(BaseScooterResult enter);
}
