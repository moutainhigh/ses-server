package com.redescooter.ses.web.ros.service.base.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeStockProdProductService extends IService<OpeStockProdProduct>{


    int updateBatch(List<OpeStockProdProduct> list);

    int batchInsert(List<OpeStockProdProduct> list);

    int insertOrUpdate(OpeStockProdProduct record);

    int insertOrUpdateSelective(OpeStockProdProduct record);

}
