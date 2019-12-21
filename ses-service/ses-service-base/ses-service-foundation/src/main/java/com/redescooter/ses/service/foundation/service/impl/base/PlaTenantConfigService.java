package com.redescooter.ses.service.foundation.service.impl.base;

import java.util.List;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantConfig;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaTenantConfigService extends IService<PlaTenantConfig>{


    int updateBatch(List<PlaTenantConfig> list);

    int batchInsert(List<PlaTenantConfig> list);

    int insertOrUpdate(PlaTenantConfig record);

    int insertOrUpdateSelective(PlaTenantConfig record);

}
