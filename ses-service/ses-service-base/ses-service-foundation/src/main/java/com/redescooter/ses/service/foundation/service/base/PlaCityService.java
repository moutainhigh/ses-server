package com.redescooter.ses.service.foundation.service.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaCity;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       19/12/2019 5:48 上午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaCityService extends IService<PlaCity>{


    int updateBatch(List<PlaCity> list);

    int batchInsert(List<PlaCity> list);

    int insertOrUpdate(PlaCity record);

    int insertOrUpdateSelective(PlaCity record);

}
