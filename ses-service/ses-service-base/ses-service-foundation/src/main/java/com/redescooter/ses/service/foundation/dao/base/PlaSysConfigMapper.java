package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaSysConfig;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaSysConfigMapper extends BaseMapper<PlaSysConfig> {
    int updateBatch(List<PlaSysConfig> list);

    int batchInsert(@Param("list") List<PlaSysConfig> list);

    int insertOrUpdate(PlaSysConfig record);

    int insertOrUpdateSelective(PlaSysConfig record);
}