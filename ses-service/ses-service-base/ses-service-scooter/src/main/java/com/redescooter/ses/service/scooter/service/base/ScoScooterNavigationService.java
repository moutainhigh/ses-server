package com.redescooter.ses.service.scooter.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;

import java.util.List;

public interface ScoScooterNavigationService extends IService<ScoScooterNavigation>{


    int updateBatch(List<ScoScooterNavigation> list);

    int batchInsert(List<ScoScooterNavigation> list);

    int insertOrUpdate(ScoScooterNavigation record);

    int insertOrUpdateSelective(ScoScooterNavigation record);

}
