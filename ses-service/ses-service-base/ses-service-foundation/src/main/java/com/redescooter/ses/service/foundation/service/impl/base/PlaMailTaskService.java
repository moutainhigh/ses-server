package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTask;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaMailTaskService extends IService<PlaMailTask>{


    int updateBatch(List<PlaMailTask> list);

    int batchInsert(List<PlaMailTask> list);

    int insertOrUpdate(PlaMailTask record);

    int insertOrUpdateSelective(PlaMailTask record);

}
