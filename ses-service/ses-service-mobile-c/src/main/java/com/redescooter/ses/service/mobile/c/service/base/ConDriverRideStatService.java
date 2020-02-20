package com.redescooter.ses.service.mobile.c.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.c.dm.base.ConDriverRideStat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface ConDriverRideStatService extends IService<ConDriverRideStat>{


    int updateBatch(List<ConDriverRideStat> list);

    int batchInsert(List<ConDriverRideStat> list);

    int insertOrUpdate(ConDriverRideStat record);

    int insertOrUpdateSelective(ConDriverRideStat record);

}
