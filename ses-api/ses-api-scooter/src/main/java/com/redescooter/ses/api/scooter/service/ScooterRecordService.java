package com.redescooter.ses.api.scooter.service;

import com.redescooter.ses.api.common.vo.base.BooleanResult;
import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.common.vo.scooter.ScooterUpdateRecordCheckEnter;
import com.redescooter.ses.api.scooter.vo.MobileRepairRecordEnter;
import com.redescooter.ses.api.scooter.vo.MobileRepairRecordResult;
import com.redescooter.ses.api.scooter.vo.SaveScooterRecordEnter;
import com.redescooter.ses.api.scooter.vo.ScooterRecordListEnter;
import com.redescooter.ses.api.scooter.vo.ScooterRecordListResult;

import java.util.List;

/**
 * @ClassName:ScooterRecordService
 * @description: ScooterRecordService
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/26 20:52
 */
public interface ScooterRecordService {
    /**
     * 保存车辆操作记录
     * @param enter
     */
    void saveScooterRecords(List<SaveScooterRecordEnter<BaseScooterEnter>> enter);

    /**
     * 查询车辆操作记录
     *
     * @param enter 为ScooterId
     */
    PageResult<ScooterRecordListResult> scooterRecordList(ScooterRecordListEnter enter);


    /**
     * 维修记录
     *
     * @param enter
     * @return
     */
    PageResult<MobileRepairRecordResult> mobileRepairRecord(MobileRepairRecordEnter enter);

    /**
     * 校验平板升级更新记录
     */
    BooleanResult checkScooterUpdateRecord(ScooterUpdateRecordCheckEnter enter);

}
