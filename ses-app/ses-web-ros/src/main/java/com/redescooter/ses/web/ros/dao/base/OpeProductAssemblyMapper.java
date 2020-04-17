package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductAssembly;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductAssemblyMapper extends BaseMapper<OpeProductAssembly> {
    int updateBatch(List<OpeProductAssembly> list);

    int batchInsert(@Param("list") List<OpeProductAssembly> list);

    int insertOrUpdate(OpeProductAssembly record);

    int insertOrUpdateSelective(OpeProductAssembly record);
}