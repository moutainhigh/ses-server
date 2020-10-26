package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOpTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOpTraceMapper extends BaseMapper<OpeOpTrace> {
    int updateBatch(List<OpeOpTrace> list);

    int batchInsert(@Param("list") List<OpeOpTrace> list);

    int insertOrUpdate(OpeOpTrace record);

    int insertOrUpdateSelective(OpeOpTrace record);
}