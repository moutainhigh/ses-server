package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOrderQcTraceMapper extends BaseMapper<OpeOrderQcTrace> {
    int updateBatch(List<OpeOrderQcTrace> list);

    int batchInsert(@Param("list") List<OpeOrderQcTrace> list);

    int insertOrUpdate(OpeOrderQcTrace record);

    int insertOrUpdateSelective(OpeOrderQcTrace record);
}