package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.redescooter.ses.mobile.rps.dm.OpePurchasePartsB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePurchasePartsBService extends IService<OpePurchasePartsB> {


    int updateBatch(List<OpePurchasePartsB> list);

    int batchInsert(List<OpePurchasePartsB> list);

    int insertOrUpdate(OpePurchasePartsB record);

    int insertOrUpdateSelective(OpePurchasePartsB record);

}
