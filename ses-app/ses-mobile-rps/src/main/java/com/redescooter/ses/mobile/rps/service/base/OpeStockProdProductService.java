package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeStockProdProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeStockProdProductService extends IService<OpeStockProdProduct> {


    int updateBatch(List<OpeStockProdProduct> list);

    int batchInsert(List<OpeStockProdProduct> list);

    int insertOrUpdate(OpeStockProdProduct record);

    int insertOrUpdateSelective(OpeStockProdProduct record);

}

