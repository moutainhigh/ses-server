package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyBOrderMapper extends BaseMapper<OpeAssemblyBOrder> {
    int updateBatch(List<OpeAssemblyBOrder> list);

    int batchInsert(@Param("list") List<OpeAssemblyBOrder> list);

    int insertOrUpdate(OpeAssemblyBOrder record);

    int insertOrUpdateSelective(OpeAssemblyBOrder record);
}