package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaI18nConfig;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaI18nConfigService extends IService<PlaI18nConfig>{


    int updateBatch(List<PlaI18nConfig> list);

    int batchInsert(List<PlaI18nConfig> list);

    int insertOrUpdate(PlaI18nConfig record);

    int insertOrUpdateSelective(PlaI18nConfig record);

}
