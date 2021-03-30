package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorScooterRideStat;

import java.util.List;

public interface CorScooterRideStatService extends IService<CorScooterRideStat> {


    int updateBatch(List<CorScooterRideStat> list);

    int batchInsert(List<CorScooterRideStat> list);

    int insertOrUpdate(CorScooterRideStat record);

    int insertOrUpdateSelective(CorScooterRideStat record);

}

