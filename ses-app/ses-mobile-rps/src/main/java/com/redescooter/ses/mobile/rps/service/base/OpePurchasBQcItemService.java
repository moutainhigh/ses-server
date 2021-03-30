package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasBQcItem;

import java.util.List;

public interface OpePurchasBQcItemService extends IService<OpePurchasBQcItem> {


    int updateBatch(List<OpePurchasBQcItem> list);

    int batchInsert(List<OpePurchasBQcItem> list);

    int insertOrUpdate(OpePurchasBQcItem record);

    int insertOrUpdateSelective(OpePurchasBQcItem record);

}















