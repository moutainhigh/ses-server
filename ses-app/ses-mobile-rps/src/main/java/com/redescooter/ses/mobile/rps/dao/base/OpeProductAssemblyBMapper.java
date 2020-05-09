package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssemblyB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductAssemblyBMapper extends BaseMapper<OpeProductAssemblyB> {
    int updateBatch(List<OpeProductAssemblyB> list);

    int batchInsert(@Param("list") List<OpeProductAssemblyB> list);

    int insertOrUpdate(OpeProductAssemblyB record);

    int insertOrUpdateSelective(OpeProductAssemblyB record);
}