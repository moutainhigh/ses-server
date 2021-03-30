package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheetHistory;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpeRegionalPriceSheetHistoryService extends IService<OpeRegionalPriceSheetHistory> {


    int updateBatch(List<OpeRegionalPriceSheetHistory> list);

    int batchInsert(List<OpeRegionalPriceSheetHistory> list);

    int insertOrUpdate(OpeRegionalPriceSheetHistory record);

    int insertOrUpdateSelective(OpeRegionalPriceSheetHistory record);

}




