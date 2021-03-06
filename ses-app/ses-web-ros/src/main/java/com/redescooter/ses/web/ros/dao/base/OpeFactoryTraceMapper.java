package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeFactoryTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeFactoryTraceMapper extends BaseMapper<OpeFactoryTrace> {
    int updateBatch(List<OpeFactoryTrace> list);

    int batchInsert(@Param("list") List<OpeFactoryTrace> list);

    int insertOrUpdate(OpeFactoryTrace record);

    int insertOrUpdateSelective(OpeFactoryTrace record);
}