package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasBQcItem;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpePurchasBQcItemService extends IService<OpePurchasBQcItem>{


    int updateBatch(List<OpePurchasBQcItem> list);

    int batchInsert(List<OpePurchasBQcItem> list);

    int insertOrUpdate(OpePurchasBQcItem record);

    int insertOrUpdateSelective(OpePurchasBQcItem record);

}
