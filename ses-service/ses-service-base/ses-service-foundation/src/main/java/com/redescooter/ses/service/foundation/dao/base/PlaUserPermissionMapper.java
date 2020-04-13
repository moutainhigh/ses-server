package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPermission;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaUserPermissionMapper extends BaseMapper<PlaUserPermission> {
    int updateBatch(List<PlaUserPermission> list);

    int batchInsert(@Param("list") List<PlaUserPermission> list);

    int insertOrUpdate(PlaUserPermission record);

    int insertOrUpdateSelective(PlaUserPermission record);
}