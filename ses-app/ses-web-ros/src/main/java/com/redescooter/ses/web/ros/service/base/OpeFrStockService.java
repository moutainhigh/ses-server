package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeFrStock;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeFrStockService extends IService<OpeFrStock> {


    int updateBatch(List<OpeFrStock> list);

    int batchInsert(List<OpeFrStock> list);

    int insertOrUpdate(OpeFrStock record);

    int insertOrUpdateSelective(OpeFrStock record);

}

