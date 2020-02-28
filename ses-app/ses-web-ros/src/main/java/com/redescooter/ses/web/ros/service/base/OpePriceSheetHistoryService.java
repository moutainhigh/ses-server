package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePriceSheetHistory;
@Transactional
public interface OpePriceSheetHistoryService extends IService<OpePriceSheetHistory>{


    int updateBatch(List<OpePriceSheetHistory> list);

    int batchInsert(List<OpePriceSheetHistory> list);

    int insertOrUpdate(OpePriceSheetHistory record);

    int insertOrUpdateSelective(OpePriceSheetHistory record);

}
