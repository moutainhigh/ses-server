package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductPriceHistory;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductPriceHistoryService extends IService<OpeProductPriceHistory> {

    int updateBatch(List<OpeProductPriceHistory> list);

    int batchInsert(List<OpeProductPriceHistory> list);

    int insertOrUpdate(OpeProductPriceHistory record);

    int insertOrUpdateSelective(OpeProductPriceHistory record);

}
