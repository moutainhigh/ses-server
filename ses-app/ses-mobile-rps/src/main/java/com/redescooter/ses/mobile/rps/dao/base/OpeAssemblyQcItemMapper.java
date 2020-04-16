package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyQcItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeAssemblyQcItem record);

    int insertOrUpdate(OpeAssemblyQcItem record);

    int insertOrUpdateSelective(OpeAssemblyQcItem record);

    int insertSelective(OpeAssemblyQcItem record);

    OpeAssemblyQcItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeAssemblyQcItem record);

    int updateByPrimaryKey(OpeAssemblyQcItem record);

    int updateBatch(List<OpeAssemblyQcItem> list);

    int updateBatchSelective(List<OpeAssemblyQcItem> list);

    int batchInsert(@Param("list") List<OpeAssemblyQcItem> list);
}