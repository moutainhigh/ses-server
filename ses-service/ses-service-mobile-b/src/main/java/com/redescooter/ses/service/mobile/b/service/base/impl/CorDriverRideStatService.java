package com.redescooter.ses.service.mobile.b.service.base.impl;

import com.redescooter.ses.service.mobile.b.dm.base.CorDriverRideStat;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CorDriverRideStatService extends IService<CorDriverRideStat>{


    int updateBatch(List<CorDriverRideStat> list);

    int batchInsert(List<CorDriverRideStat> list);

    int insertOrUpdate(CorDriverRideStat record);

    int insertOrUpdateSelective(CorDriverRideStat record);

}
