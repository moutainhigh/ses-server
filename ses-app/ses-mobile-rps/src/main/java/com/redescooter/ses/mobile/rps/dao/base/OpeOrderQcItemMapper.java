package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeOrderQcItemMapper extends BaseMapper<OpeOrderQcItem> {
    int updateBatch(List<OpeOrderQcItem> list);

    int batchInsert(@Param("list") List<OpeOrderQcItem> list);

    int insertOrUpdate(OpeOrderQcItem record);

    int insertOrUpdateSelective(OpeOrderQcItem record);
}