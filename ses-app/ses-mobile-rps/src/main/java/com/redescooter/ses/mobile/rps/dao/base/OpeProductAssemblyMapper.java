package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeProductAssembly;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductAssemblyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeProductAssembly record);

    int insertOrUpdate(OpeProductAssembly record);

    int insertOrUpdateSelective(OpeProductAssembly record);

    int insertSelective(OpeProductAssembly record);

    OpeProductAssembly selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductAssembly record);

    int updateByPrimaryKey(OpeProductAssembly record);

    int updateBatch(List<OpeProductAssembly> list);

    int updateBatchSelective(List<OpeProductAssembly> list);

    int batchInsert(@Param("list") List<OpeProductAssembly> list);
}