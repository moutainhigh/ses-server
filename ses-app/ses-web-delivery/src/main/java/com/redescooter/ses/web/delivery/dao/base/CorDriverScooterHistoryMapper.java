package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorDriverScooterHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorDriverScooterHistoryMapper extends BaseMapper<CorDriverScooterHistory> {
    int updateBatch(List<CorDriverScooterHistory> list);

    int batchInsert(@Param("list") List<CorDriverScooterHistory> list);

    int insertOrUpdate(CorDriverScooterHistory record);

    int insertOrUpdateSelective(CorDriverScooterHistory record);
}