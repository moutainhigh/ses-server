package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchaseScooterB;

import java.util.List;

public interface OpePurchaseScooterBService extends IService<OpePurchaseScooterB> {


    int updateBatch(List<OpePurchaseScooterB> list);

    int batchInsert(List<OpePurchaseScooterB> list);

    int insertOrUpdate(OpePurchaseScooterB record);

    int insertOrUpdateSelective(OpePurchaseScooterB record);

}
