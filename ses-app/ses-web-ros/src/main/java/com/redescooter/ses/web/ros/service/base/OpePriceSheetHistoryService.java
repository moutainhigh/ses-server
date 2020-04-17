package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePriceSheetHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePriceSheetHistoryService extends IService<OpePriceSheetHistory> {


    int updateBatch(List<OpePriceSheetHistory> list);

    int batchInsert(List<OpePriceSheetHistory> list);

    int insertOrUpdate(OpePriceSheetHistory record);

    int insertOrUpdateSelective(OpePriceSheetHistory record);

}

