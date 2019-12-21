package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMailConfig;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaMailConfigService extends IService<PlaMailConfig>{


    int updateBatch(List<PlaMailConfig> list);

    int batchInsert(List<PlaMailConfig> list);

    int insertOrUpdate(PlaMailConfig record);

    int insertOrUpdateSelective(PlaMailConfig record);

}
