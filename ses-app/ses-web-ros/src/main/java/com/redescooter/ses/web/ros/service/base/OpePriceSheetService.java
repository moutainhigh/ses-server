package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePriceSheet;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePriceSheetService extends IService<OpePriceSheet> {


    int updateBatch(List<OpePriceSheet> list);

    int batchInsert(List<OpePriceSheet> list);

    int insertOrUpdate(OpePriceSheet record);

    int insertOrUpdateSelective(OpePriceSheet record);

}





