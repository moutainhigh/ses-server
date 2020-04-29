package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyQcItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpeAssemblyQcItemMapper extends BaseMapper<OpeAssemblyQcItem> {
    int updateBatch(List<OpeAssemblyQcItem> list);

    int updateBatchSelective(List<OpeAssemblyQcItem> list);

    int batchInsert(@Param("list") List<OpeAssemblyQcItem> list);

    int insertOrUpdate(OpeAssemblyQcItem record);

    int insertOrUpdateSelective(OpeAssemblyQcItem record);
}