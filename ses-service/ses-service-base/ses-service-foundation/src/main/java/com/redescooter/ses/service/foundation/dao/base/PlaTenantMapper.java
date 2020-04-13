package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenant;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaTenantMapper extends BaseMapper<PlaTenant> {
    int updateBatch(List<PlaTenant> list);

    int updateBatchSelective(List<PlaTenant> list);

    int batchInsert(@Param("list") List<PlaTenant> list);

    int insertOrUpdate(PlaTenant record);

    int insertOrUpdateSelective(PlaTenant record);
}