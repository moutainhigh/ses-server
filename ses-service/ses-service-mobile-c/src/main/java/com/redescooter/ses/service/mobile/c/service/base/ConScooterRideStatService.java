package com.redescooter.ses.service.mobile.c.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.c.dm.base.ConScooterRideStat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ConScooterRideStatService extends IService<ConScooterRideStat> {


    int updateBatch(List<ConScooterRideStat> list);

    int batchInsert(List<ConScooterRideStat> list);

    int insertOrUpdate(ConScooterRideStat record);

    int insertOrUpdateSelective(ConScooterRideStat record);

}

