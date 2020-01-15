package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooter;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScoScooterMapper extends BaseMapper<ScoScooter> {
    int updateBatch(List<ScoScooter> list);

    int updateBatchSelective(List<ScoScooter> list);

    int batchInsert(@Param("list") List<ScoScooter> list);

    int insertOrUpdate(ScoScooter record);

    int insertOrUpdateSelective(ScoScooter record);
}