package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductionPartPriceHistory;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionPartPriceHistoryService extends IService<OpeProductionPartPriceHistory> {

    int updateBatch(List<OpeProductionPartPriceHistory> list);

    int batchInsert(List<OpeProductionPartPriceHistory> list);

    int insertOrUpdate(OpeProductionPartPriceHistory record);

    int insertOrUpdateSelective(OpeProductionPartPriceHistory record);

}
