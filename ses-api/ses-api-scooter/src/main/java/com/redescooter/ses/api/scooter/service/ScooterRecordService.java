package com.redescooter.ses.api.scooter.service;

import java.util.List;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.api.common.vo.scooter.BaseScooterEnter;
import com.redescooter.ses.api.scooter.vo.SaveScooterRecordEnter;
import com.redescooter.ses.api.scooter.vo.ScooterRecordListEnter;
import com.redescooter.ses.api.scooter.vo.ScooterRecordListResult;

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
     * @param enter 为ScooterId
     */
    PageResult<ScooterRecordListResult> scooterRecordList(ScooterRecordListEnter enter);

}
