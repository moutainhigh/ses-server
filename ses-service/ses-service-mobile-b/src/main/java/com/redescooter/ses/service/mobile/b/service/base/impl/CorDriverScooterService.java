package com.redescooter.ses.service.mobile.b.service.base.impl;

import java.util.List;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CorDriverScooterService extends IService<CorDriverScooter>{


    int updateBatch(List<CorDriverScooter> list);

    int batchInsert(List<CorDriverScooter> list);

    int insertOrUpdate(CorDriverScooter record);

    int insertOrUpdateSelective(CorDriverScooter record);

}
