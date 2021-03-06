package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasProduct;

import java.util.List;

public interface OpePurchasProductService extends IService<OpePurchasProduct>{


    int updateBatch(List<OpePurchasProduct> list);

    int batchInsert(List<OpePurchasProduct> list);

    int insertOrUpdate(OpePurchasProduct record);

    int insertOrUpdateSelective(OpePurchasProduct record);

}
