package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;

import java.util.List;

public interface PlaUserPermissionService extends IService<PlaUserPermission> {


    int updateBatch(List<PlaUserPermission> list);

    int batchInsert(List<PlaUserPermission> list);

    int insertOrUpdate(PlaUserPermission record);

    int insertOrUpdateSelective(PlaUserPermission record);

}
