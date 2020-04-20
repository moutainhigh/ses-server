package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpePriceSheet;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePriceSheetService extends IService<OpePriceSheet>{


    int updateBatch(List<OpePriceSheet> list);

    int batchInsert(List<OpePriceSheet> list);

    int insertOrUpdate(OpePriceSheet record);

    int insertOrUpdateSelective(OpePriceSheet record);

}
