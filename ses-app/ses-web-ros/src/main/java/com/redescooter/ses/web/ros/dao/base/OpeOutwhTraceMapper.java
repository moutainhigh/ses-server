package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOutwhTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOutwhTraceMapper extends BaseMapper<OpeOutwhTrace> {
    int updateBatch(List<OpeOutwhTrace> list);

    int batchInsert(@Param("list") List<OpeOutwhTrace> list);

    int insertOrUpdate(OpeOutwhTrace record);

    int insertOrUpdateSelective(OpeOutwhTrace record);
}
