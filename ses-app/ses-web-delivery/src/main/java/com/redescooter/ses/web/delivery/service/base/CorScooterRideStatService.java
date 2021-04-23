package com.redescooter.ses.web.delivery.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.delivery.dm.CorScooterRideStat;

import java.util.List;

public interface CorScooterRideStatService extends IService<CorScooterRideStat>{


    int updateBatch(List<CorScooterRideStat> list);

    int batchInsert(List<CorScooterRideStat> list);

    int insertOrUpdate(CorScooterRideStat record);

    int insertOrUpdateSelective(CorScooterRideStat record);

}
