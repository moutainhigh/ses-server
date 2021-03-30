package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePriceSheetHistory;

import java.util.List;

public interface OpePriceSheetHistoryService extends IService<OpePriceSheetHistory> {


    int updateBatch(List<OpePriceSheetHistory> list);

    int batchInsert(List<OpePriceSheetHistory> list);

    int insertOrUpdate(OpePriceSheetHistory record);

    int insertOrUpdateSelective(OpePriceSheetHistory record);

}

