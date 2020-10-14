package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProductionPartPriceSheet;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionPartPriceSheetService extends IService<OpeProductionPartPriceSheet> {

    int updateBatch(List<OpeProductionPartPriceSheet> list);

    int batchInsert(List<OpeProductionPartPriceSheet> list);

    int insertOrUpdate(OpeProductionPartPriceSheet record);

    int insertOrUpdateSelective(OpeProductionPartPriceSheet record);

}
