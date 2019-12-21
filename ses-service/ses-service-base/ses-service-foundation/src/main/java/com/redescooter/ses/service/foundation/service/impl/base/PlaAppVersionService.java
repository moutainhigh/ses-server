package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersion;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaAppVersionService extends IService<PlaAppVersion>{


    int updateBatch(List<PlaAppVersion> list);

    int batchInsert(List<PlaAppVersion> list);

    int insertOrUpdate(PlaAppVersion record);

    int insertOrUpdateSelective(PlaAppVersion record);

}
