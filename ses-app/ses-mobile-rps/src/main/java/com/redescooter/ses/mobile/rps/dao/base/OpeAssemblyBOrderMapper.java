package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyBOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeAssemblyBOrder record);

    int insertOrUpdate(OpeAssemblyBOrder record);

    int insertOrUpdateSelective(OpeAssemblyBOrder record);

    int insertSelective(OpeAssemblyBOrder record);

    OpeAssemblyBOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeAssemblyBOrder record);

    int updateByPrimaryKey(OpeAssemblyBOrder record);

    int updateBatch(List<OpeAssemblyBOrder> list);

    int updateBatchSelective(List<OpeAssemblyBOrder> list);

    int batchInsert(@Param("list") List<OpeAssemblyBOrder> list);
}