package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeStockProdProductService extends IService<OpeStockProdProduct> {


    int updateBatch(List<OpeStockProdProduct> list);

    int batchInsert(List<OpeStockProdProduct> list);

    int insertOrUpdate(OpeStockProdProduct record);

    int insertOrUpdateSelective(OpeStockProdProduct record);

}

