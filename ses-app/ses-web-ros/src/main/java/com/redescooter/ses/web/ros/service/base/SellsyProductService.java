package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.SellsyProduct;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SellsyProductService extends IService<SellsyProduct> {


    int updateBatch(List<SellsyProduct> list);

    int batchInsert(List<SellsyProduct> list);

    int insertOrUpdate(SellsyProduct record);

    int insertOrUpdateSelective(SellsyProduct record);

}

