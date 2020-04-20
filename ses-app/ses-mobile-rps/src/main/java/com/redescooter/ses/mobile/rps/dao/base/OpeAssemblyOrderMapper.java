package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAssemblyOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyOrderMapper extends BaseMapper<OpeAssemblyOrder> {
    int updateBatch(List<OpeAssemblyOrder> list);

    int batchInsert(@Param("list") List<OpeAssemblyOrder> list);

    int insertOrUpdate(OpeAssemblyOrder record);

    int insertOrUpdateSelective(OpeAssemblyOrder record);
}