package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePriceSheet;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePriceSheetMapper extends BaseMapper<OpePriceSheet> {
    int updateBatch(List<OpePriceSheet> list);

    int batchInsert(@Param("list") List<OpePriceSheet> list);

    int insertOrUpdate(OpePriceSheet record);

    int insertOrUpdateSelective(OpePriceSheet record);
}