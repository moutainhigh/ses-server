package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePartsDraft;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePartsDraftMapper extends BaseMapper<OpePartsDraft> {
    int updateBatch(List<OpePartsDraft> list);

    int batchInsert(@Param("list") List<OpePartsDraft> list);

    int insertOrUpdate(OpePartsDraft record);

    int insertOrUpdateSelective(OpePartsDraft record);
}