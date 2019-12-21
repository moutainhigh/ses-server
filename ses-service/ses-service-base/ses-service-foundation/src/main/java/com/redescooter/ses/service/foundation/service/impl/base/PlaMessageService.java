package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMessage;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaMessageService extends IService<PlaMessage>{


    int updateBatch(List<PlaMessage> list);

    int batchInsert(List<PlaMessage> list);

    int insertOrUpdate(PlaMessage record);

    int insertOrUpdateSelective(PlaMessage record);

}
