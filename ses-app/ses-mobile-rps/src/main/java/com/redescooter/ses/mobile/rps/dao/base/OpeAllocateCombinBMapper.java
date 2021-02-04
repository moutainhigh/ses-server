package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateCombinB;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeAllocateCombinBMapper extends BaseMapper<OpeAllocateCombinB> {
    int updateBatch(List<OpeAllocateCombinB> list);

    int batchInsert(@Param("list") List<OpeAllocateCombinB> list);

    int insertOrUpdate(OpeAllocateCombinB record);

    int insertOrUpdateSelective(OpeAllocateCombinB record);
}