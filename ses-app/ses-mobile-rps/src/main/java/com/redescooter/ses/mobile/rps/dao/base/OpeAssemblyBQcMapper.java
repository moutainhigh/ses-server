package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyBQc;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyBQcMapper extends BaseMapper<OpeAssemblyBQc> {
    int updateBatch(List<OpeAssemblyBQc> list);

    int batchInsert(@Param("list") List<OpeAssemblyBQc> list);

    int insertOrUpdate(OpeAssemblyBQc record);

    int insertOrUpdateSelective(OpeAssemblyBQc record);
}