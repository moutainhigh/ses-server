package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductAssemblyB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductAssemblyBMapper extends BaseMapper<OpeProductAssemblyB> {
    int updateBatch(List<OpeProductAssemblyB> list);

    int batchInsert(@Param("list") List<OpeProductAssemblyB> list);

    int insertOrUpdate(OpeProductAssemblyB record);

    int insertOrUpdateSelective(OpeProductAssemblyB record);
}