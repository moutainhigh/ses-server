package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import com.baomidou.mybatisplus.extension.service.IService;
public interface PlaCityService extends IService<PlaCity>{


    int updateBatch(List<PlaCity> list);

    int batchInsert(List<PlaCity> list);

    int insertOrUpdate(PlaCity record);

    int insertOrUpdateSelective(PlaCity record);

}
