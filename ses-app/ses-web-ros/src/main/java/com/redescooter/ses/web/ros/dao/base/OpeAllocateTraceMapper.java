package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocateTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAllocateTraceMapper extends BaseMapper<OpeAllocateTrace> {
    int updateBatch(List<OpeAllocateTrace> list);

    int batchInsert(@Param("list") List<OpeAllocateTrace> list);

    int insertOrUpdate(OpeAllocateTrace record);

    int insertOrUpdateSelective(OpeAllocateTrace record);
}