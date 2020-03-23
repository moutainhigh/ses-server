package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpePurchasB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchasBService extends IService<OpePurchasB> {


    int batchInsert(List<OpePurchasB> list);

    int insertOrUpdate(OpePurchasB record);

    int insertOrUpdateSelective(OpePurchasB record);

}
