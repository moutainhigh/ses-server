package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePurchasBQcItem;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePurchasBQcItemService extends IService<OpePurchasBQcItem>{


    int updateBatch(List<OpePurchasBQcItem> list);

    int batchInsert(List<OpePurchasBQcItem> list);

    int insertOrUpdate(OpePurchasBQcItem record);

    int insertOrUpdateSelective(OpePurchasBQcItem record);

}
