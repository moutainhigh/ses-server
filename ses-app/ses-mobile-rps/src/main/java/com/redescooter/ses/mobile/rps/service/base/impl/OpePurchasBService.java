package com.redescooter.ses.mobile.rps.service.base.impl;

import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchasBService extends IService<OpePurchasB> {


    int batchInsert(List<OpePurchasB> list);

    int insertOrUpdate(OpePurchasB record);

    int insertOrUpdateSelective(OpePurchasB record);

}






