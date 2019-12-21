package com.redescooter.ses.service.foundation.service.impl.base;

import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       20/12/2019 2:05 下午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface PlaUserPermissionService extends IService<PlaUserPermission>{


    int updateBatch(List<PlaUserPermission> list);

    int batchInsert(List<PlaUserPermission> list);

    int insertOrUpdate(PlaUserPermission record);

    int insertOrUpdateSelective(PlaUserPermission record);

}
