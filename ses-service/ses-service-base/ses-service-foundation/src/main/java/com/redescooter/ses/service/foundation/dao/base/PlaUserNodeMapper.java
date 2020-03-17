package com.redescooter.ses.service.foundation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaUserNode;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PlaUserNodeMapper extends BaseMapper<PlaUserNode> {
    int updateBatch(List<PlaUserNode> list);

    int batchInsert(@Param("list") List<PlaUserNode> list);

    int insertOrUpdate(PlaUserNode record);

    int insertOrUpdateSelective(PlaUserNode record);
}