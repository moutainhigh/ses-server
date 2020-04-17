package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePurchas;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpePurchasService extends IService<OpePurchas>{


    int updateBatch(List<OpePurchas> list);

    int batchInsert(List<OpePurchas> list);

    int insertOrUpdate(OpePurchas record);

    int insertOrUpdateSelective(OpePurchas record);

}
