package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeAssemblyOrder record);

    int insertOrUpdate(OpeAssemblyOrder record);

    int insertOrUpdateSelective(OpeAssemblyOrder record);

    int insertSelective(OpeAssemblyOrder record);

    OpeAssemblyOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeAssemblyOrder record);

    int updateByPrimaryKey(OpeAssemblyOrder record);

    int updateBatch(List<OpeAssemblyOrder> list);

    int updateBatchSelective(List<OpeAssemblyOrder> list);

    int batchInsert(@Param("list") List<OpeAssemblyOrder> list);
}