package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyPreparation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyPreparationMapper extends BaseMapper<OpeAssemblyPreparation> {
    int updateBatch(List<OpeAssemblyPreparation> list);

    int batchInsert(@Param("list") List<OpeAssemblyPreparation> list);

    int insertOrUpdate(OpeAssemblyPreparation record);

    int insertOrUpdateSelective(OpeAssemblyPreparation record);
}