package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorDriverScooter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorDriverScooterMapper extends BaseMapper<CorDriverScooter> {
    int updateBatch(List<CorDriverScooter> list);

    int batchInsert(@Param("list") List<CorDriverScooter> list);

    int insertOrUpdate(CorDriverScooter record);

    int insertOrUpdateSelective(CorDriverScooter record);
}