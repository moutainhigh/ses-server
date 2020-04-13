package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriver;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorDriverMapper extends BaseMapper<CorDriver> {
    int updateBatch(List<CorDriver> list);

    int batchInsert(@Param("list") List<CorDriver> list);

    int insertOrUpdate(CorDriver record);

    int insertOrUpdateSelective(CorDriver record);
}