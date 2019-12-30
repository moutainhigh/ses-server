package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.redescooter.ses.service.mobile.b.dm.base.CorDriver;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CorDriverService extends IService<CorDriver>{


    int updateBatch(List<CorDriver> list);

    int batchInsert(List<CorDriver> list);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);

}
