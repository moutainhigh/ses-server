package com.redescooter.ses.mobile.rps.service.base.impl;

import com.redescooter.ses.mobile.rps.dm.OpeStockProdPart;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeStockProdPartService extends IService<OpeStockProdPart>{


    int updateBatch(List<OpeStockProdPart> list);

    int batchInsert(List<OpeStockProdPart> list);

    int insertOrUpdate(OpeStockProdPart record);

    int insertOrUpdateSelective(OpeStockProdPart record);

}
