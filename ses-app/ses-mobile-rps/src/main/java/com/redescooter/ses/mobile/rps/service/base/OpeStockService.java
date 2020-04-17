package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeStock;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeStockService extends IService<OpeStock> {


    int updateBatch(List<OpeStock> list);

    int batchInsert(List<OpeStock> list);

    int insertOrUpdate(OpeStock record);

    int insertOrUpdateSelective(OpeStock record);

}



