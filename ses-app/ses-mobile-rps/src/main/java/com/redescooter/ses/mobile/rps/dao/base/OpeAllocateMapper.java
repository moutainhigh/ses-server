package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeAllocate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAllocateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeAllocate record);

    int insertOrUpdate(OpeAllocate record);

    int insertOrUpdateSelective(OpeAllocate record);

    int insertSelective(OpeAllocate record);

    OpeAllocate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeAllocate record);

    int updateByPrimaryKey(OpeAllocate record);

    int updateBatch(List<OpeAllocate> list);

    int updateBatchSelective(List<OpeAllocate> list);

    int batchInsert(@Param("list") List<OpeAllocate> list);
}