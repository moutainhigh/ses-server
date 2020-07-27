package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeFrStockProduct;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeFrStockProductService extends IService<OpeFrStockProduct>{


    int updateBatch(List<OpeFrStockProduct> list);

    int batchInsert(List<OpeFrStockProduct> list);

    int insertOrUpdate(OpeFrStockProduct record);

    int insertOrUpdateSelective(OpeFrStockProduct record);

}
