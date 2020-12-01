package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAllocatePartsB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeAllocatePartsBMapper extends BaseMapper<OpeAllocatePartsB> {
    int updateBatch(List<OpeAllocatePartsB> list);

    int batchInsert(@Param("list") List<OpeAllocatePartsB> list);

    int insertOrUpdate(OpeAllocatePartsB record);

    int insertOrUpdateSelective(OpeAllocatePartsB record);
}