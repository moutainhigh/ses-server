package com.redescooter.ses.web.delivery.service.base;

import java.util.List;
import com.redescooter.ses.web.delivery.dm.CorScooterRideStat;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CorScooterRideStatService extends IService<CorScooterRideStat>{


    int updateBatch(List<CorScooterRideStat> list);

    int batchInsert(List<CorScooterRideStat> list);

    int insertOrUpdate(CorScooterRideStat record);

    int insertOrUpdateSelective(CorScooterRideStat record);

}
