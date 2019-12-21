package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaSysConfig;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaSysConfigService extends IService<PlaSysConfig>{


    int updateBatch(List<PlaSysConfig> list);

    int batchInsert(List<PlaSysConfig> list);

    int insertOrUpdate(PlaSysConfig record);

    int insertOrUpdateSelective(PlaSysConfig record);

}
