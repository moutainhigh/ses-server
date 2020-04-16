package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeAllocateB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAllocateBMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeAllocateB record);

    int insertOrUpdate(OpeAllocateB record);

    int insertOrUpdateSelective(OpeAllocateB record);

    int insertSelective(OpeAllocateB record);

    OpeAllocateB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeAllocateB record);

    int updateByPrimaryKey(OpeAllocateB record);

    int updateBatch(List<OpeAllocateB> list);

    int updateBatchSelective(List<OpeAllocateB> list);

    int batchInsert(@Param("list") List<OpeAllocateB> list);
}