package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeStockPurchas;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeStockPurchasService extends IService<OpeStockPurchas> {


    int updateBatch(List<OpeStockPurchas> list);

    int batchInsert(List<OpeStockPurchas> list);

    int insertOrUpdate(OpeStockPurchas record);

    int insertOrUpdateSelective(OpeStockPurchas record);

}




