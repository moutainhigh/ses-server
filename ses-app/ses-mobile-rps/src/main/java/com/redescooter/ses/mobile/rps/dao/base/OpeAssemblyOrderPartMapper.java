package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrderPart;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyOrderPartMapper extends BaseMapper<OpeAssemblyOrderPart> {
    int updateBatch(List<OpeAssemblyOrderPart> list);

    int batchInsert(@Param("list") List<OpeAssemblyOrderPart> list);

    int insertOrUpdate(OpeAssemblyOrderPart record);

    int insertOrUpdateSelective(OpeAssemblyOrderPart record);
}