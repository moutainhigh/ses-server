package com.redescooter.ses.service.scooter.dao;

import com.redescooter.ses.api.scooter.vo.MobileRepairRecordEnter;
import com.redescooter.ses.api.scooter.vo.MobileRepairRecordResult;

import java.util.List;

/**
 * @ClassName:ScooterRecordService
 * @description: ScooterRecordService
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/14 18:34
 */
public interface ScooterRecordServiceMapper {

    /**
     * 维修记录
     *
     * @param enter
     * @return
     */
    int mobileRepairRecordCount(MobileRepairRecordEnter enter);

    /**
     * 维修记录
     *
     * @param enter
     * @return
     */
    List<MobileRepairRecordResult> mobileRepairRecordList(MobileRepairRecordEnter enter);
}
