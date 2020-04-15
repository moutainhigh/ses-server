package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeAssemblyQcItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeAssemblyQcItemMapper extends BaseMapper<OpeAssemblyQcItem> {
    int updateBatch(List<OpeAssemblyQcItem> list);

    int batchInsert(@Param("list") List<OpeAssemblyQcItem> list);

    int insertOrUpdate(OpeAssemblyQcItem record);

    int insertOrUpdateSelective(OpeAssemblyQcItem record);
}