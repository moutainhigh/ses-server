package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasePartsB;

import java.util.List;

public interface OpePurchasePartsBService extends IService<OpePurchasePartsB> {


    int updateBatch(List<OpePurchasePartsB> list);

    int batchInsert(List<OpePurchasePartsB> list);

    int insertOrUpdate(OpePurchasePartsB record);

    int insertOrUpdateSelective(OpePurchasePartsB record);

}
