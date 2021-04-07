package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasProduct;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpePurchasProductService extends IService<OpePurchasProduct>{


    int updateBatch(List<OpePurchasProduct> list);

    int batchInsert(List<OpePurchasProduct> list);

    int insertOrUpdate(OpePurchasProduct record);

    int insertOrUpdateSelective(OpePurchasProduct record);

}
