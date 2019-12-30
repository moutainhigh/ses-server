package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorDriverScooter;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorDriverScooterMapper extends BaseMapper<CorDriverScooter> {
    int updateBatch(List<CorDriverScooter> list);

    int batchInsert(@Param("list") List<CorDriverScooter> list);

    int insertOrUpdate(CorDriverScooter record);

    int insertOrUpdateSelective(CorDriverScooter record);
}