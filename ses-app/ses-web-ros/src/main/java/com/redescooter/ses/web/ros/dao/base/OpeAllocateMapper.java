package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeAllocateMapper extends BaseMapper<OpeAllocate> {
    int updateBatch(List<OpeAllocate> list);

    int batchInsert(@Param("list") List<OpeAllocate> list);

    int insertOrUpdate(OpeAllocate record);

    int insertOrUpdateSelective(OpeAllocate record);
}