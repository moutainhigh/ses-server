package com.redescooter.ses.service.scooter.service.base;

import java.util.List;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ScoScooterNavigationService extends IService<ScoScooterNavigation>{


    int updateBatch(List<ScoScooterNavigation> list);

    int batchInsert(List<ScoScooterNavigation> list);

    int insertOrUpdate(ScoScooterNavigation record);

    int insertOrUpdateSelective(ScoScooterNavigation record);

}
