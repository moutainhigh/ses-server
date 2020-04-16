package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeProductAssemblyB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductAssemblyBMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeProductAssemblyB record);

    int insertOrUpdate(OpeProductAssemblyB record);

    int insertOrUpdateSelective(OpeProductAssemblyB record);

    int insertSelective(OpeProductAssemblyB record);

    OpeProductAssemblyB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeProductAssemblyB record);

    int updateByPrimaryKey(OpeProductAssemblyB record);

    int updateBatch(List<OpeProductAssemblyB> list);

    int updateBatchSelective(List<OpeProductAssemblyB> list);

    int batchInsert(@Param("list") List<OpeProductAssemblyB> list);
}