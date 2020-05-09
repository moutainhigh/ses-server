package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaTenantNode;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaTenantNodeMapper extends BaseMapper<PlaTenantNode> {
    int updateBatch(List<PlaTenantNode> list);

    int batchInsert(@Param("list") List<PlaTenantNode> list);

    int insertOrUpdate(PlaTenantNode record);

    int insertOrUpdateSelective(PlaTenantNode record);
}