package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantConfig;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaTenantConfigMapper extends BaseMapper<PlaTenantConfig> {
    int updateBatch(List<PlaTenantConfig> list);

    int updateBatchSelective(List<PlaTenantConfig> list);

    int batchInsert(@Param("list") List<PlaTenantConfig> list);

    int insertOrUpdate(PlaTenantConfig record);

    int insertOrUpdateSelective(PlaTenantConfig record);
}