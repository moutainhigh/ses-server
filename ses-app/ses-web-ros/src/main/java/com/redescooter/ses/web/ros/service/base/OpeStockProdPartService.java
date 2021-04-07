package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeStockProdPart;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpeStockProdPartService extends IService<OpeStockProdPart> {


    int updateBatch(List<OpeStockProdPart> list);

    int batchInsert(List<OpeStockProdPart> list);

    int insertOrUpdate(OpeStockProdPart record);

    int insertOrUpdateSelective(OpeStockProdPart record);

}



