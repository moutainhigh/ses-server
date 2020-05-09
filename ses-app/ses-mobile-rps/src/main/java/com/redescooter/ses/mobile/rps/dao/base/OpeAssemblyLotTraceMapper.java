package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyLotTrace;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpeAssemblyLotTraceMapper extends BaseMapper<OpeAssemblyLotTrace> {
    int updateBatch(List<OpeAssemblyLotTrace> list);

    int updateBatchSelective(List<OpeAssemblyLotTrace> list);

    int batchInsert(@Param("list") List<OpeAssemblyLotTrace> list);

    int insertOrUpdate(OpeAssemblyLotTrace record);

    int insertOrUpdateSelective(OpeAssemblyLotTrace record);
}