package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpePurchaseScooterB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchaseScooterBService extends IService<OpePurchaseScooterB> {


    int updateBatch(List<OpePurchaseScooterB> list);

    int batchInsert(List<OpePurchaseScooterB> list);

    int insertOrUpdate(OpePurchaseScooterB record);

    int insertOrUpdateSelective(OpePurchaseScooterB record);

}
