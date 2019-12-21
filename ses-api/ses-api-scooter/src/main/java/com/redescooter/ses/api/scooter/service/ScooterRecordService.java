package com.redescooter.ses.api.scooter.service;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.scooter.vo.*;

import java.util.List;

/**
 * @description: ScooterRecordService
 * @author: Alex
 * @create: 2019/03/22 18:05
 */
public interface ScooterRecordService {

    /**
     * scooter 充电记录
     * @param enter
     * @return
     */
    List<ScooterChargRecordResult> scooterChargRecord(ScooterChargRecordEnter enter);

    /**
     * 维修记录
     * @param enter
     * @return
     */
    List<ScooterRepairResult> scooteRepairRecord(ScooterRepairEnter enter);

    /**
     * 创建维修预约单 记录维修记录
     * @param enter
     * @return
     */
    GeneralResult saveScooteRepairRecord(SaveScooterRepairRecordEnter enter);

    /**
     * 更新 维修记录状态
     * @return
     */
    GeneralResult editScooterRepairRecord(EditScooterRepairRecordEnter enter);
}
