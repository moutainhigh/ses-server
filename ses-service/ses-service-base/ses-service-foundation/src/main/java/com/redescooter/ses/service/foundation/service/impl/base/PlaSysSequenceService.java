package com.redescooter.ses.service.foundation.service.impl.base;

import com.redescooter.ses.service.foundation.dm.base.PlaSysSequence;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaSysSequenceService extends IService<PlaSysSequence>{


    int updateBatch(List<PlaSysSequence> list);

    int batchInsert(List<PlaSysSequence> list);

    int insertOrUpdate(PlaSysSequence record);

    int insertOrUpdateSelective(PlaSysSequence record);

}
