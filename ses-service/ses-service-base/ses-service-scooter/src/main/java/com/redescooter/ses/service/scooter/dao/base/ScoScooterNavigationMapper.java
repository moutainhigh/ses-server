package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterNavigation;
import org.apache.ibatis.annotations.Param;

public interface ScoScooterNavigationMapper extends BaseMapper<ScoScooterNavigation> {
    int updateBatch(List<ScoScooterNavigation> list);

    int batchInsert(@Param("list") List<ScoScooterNavigation> list);

    int insertOrUpdate(ScoScooterNavigation record);

    int insertOrUpdateSelective(ScoScooterNavigation record);
}