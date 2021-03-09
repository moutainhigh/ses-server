package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheet;

import java.util.List;

public interface OpeRegionalPriceSheetService extends IService<OpeRegionalPriceSheet> {


    int updateBatch(List<OpeRegionalPriceSheet> list);

    int batchInsert(List<OpeRegionalPriceSheet> list);

    int insertOrUpdate(OpeRegionalPriceSheet record);

    int insertOrUpdateSelective(OpeRegionalPriceSheet record);

}



