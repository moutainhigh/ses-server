package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeSysLog;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeSysLogMapper extends BaseMapper<OpeSysLog> {
    int updateBatch(List<OpeSysLog> list);

    int updateBatchSelective(List<OpeSysLog> list);

    int batchInsert(@Param("list") List<OpeSysLog> list);

    int insertOrUpdate(OpeSysLog record);

    int insertOrUpdateSelective(OpeSysLog record);
}