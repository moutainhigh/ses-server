package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaPushResult;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaPushResultService extends IService<PlaPushResult>{


    int updateBatch(List<PlaPushResult> list);

    int batchInsert(List<PlaPushResult> list);

    int insertOrUpdate(PlaPushResult record);

    int insertOrUpdateSelective(PlaPushResult record);

}
