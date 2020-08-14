package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeContactUsTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeContactUsTraceMapper extends BaseMapper<OpeContactUsTrace> {
    int updateBatch(List<OpeContactUsTrace> list);

    int batchInsert(@Param("list") List<OpeContactUsTrace> list);

    int insertOrUpdate(OpeContactUsTrace record);

    int insertOrUpdateSelective(OpeContactUsTrace record);

    int updateBatchSelective(List<OpeContactUsTrace> list);
}