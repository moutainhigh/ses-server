package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTrace;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTraceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorExpressOrderTraceMapper extends BaseMapper<CorExpressOrderTrace> {
    long countByExample(CorExpressOrderTraceExample example);

    int deleteByExample(CorExpressOrderTraceExample example);

    List<CorExpressOrderTrace> selectByExample(CorExpressOrderTraceExample example);

    int updateByExampleSelective(@Param("record") CorExpressOrderTrace record, @Param("example") CorExpressOrderTraceExample example);

    int updateByExample(@Param("record") CorExpressOrderTrace record, @Param("example") CorExpressOrderTraceExample example);
}