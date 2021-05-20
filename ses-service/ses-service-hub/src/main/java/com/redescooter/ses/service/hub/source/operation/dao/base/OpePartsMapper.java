package com.redescooter.ses.service.hub.source.operation.dao.base;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeParts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpePartsMapper extends BaseMapper<OpeParts> {
    int updateBatch(List<OpeParts> list);

    int batchInsert(@Param("list") List<OpeParts> list);

    int insertOrUpdate(OpeParts record);

    int insertOrUpdateSelective(OpeParts record);
}