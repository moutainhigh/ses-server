package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterStatus;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScoScooterStatusMapper extends BaseMapper<ScoScooterStatus> {
    int updateBatch(List<ScoScooterStatus> list);

    int batchInsert(@Param("list") List<ScoScooterStatus> list);

    int insertOrUpdate(ScoScooterStatus record);

    int insertOrUpdateSelective(ScoScooterStatus record);
}