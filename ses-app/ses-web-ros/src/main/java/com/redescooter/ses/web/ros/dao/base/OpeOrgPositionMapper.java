package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOrgPosition;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeOrgPositionMapper extends BaseMapper<OpeOrgPosition> {
    int updateBatch(List<OpeOrgPosition> list);

    int batchInsert(@Param("list") List<OpeOrgPosition> list);

    int insertOrUpdate(OpeOrgPosition record);

    int insertOrUpdateSelective(OpeOrgPosition record);
}