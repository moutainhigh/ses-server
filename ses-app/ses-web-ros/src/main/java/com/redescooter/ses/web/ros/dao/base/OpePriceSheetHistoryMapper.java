package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePriceSheetHistory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePriceSheetHistoryMapper extends BaseMapper<OpePriceSheetHistory> {
    int updateBatch(List<OpePriceSheetHistory> list);

    int batchInsert(@Param("list") List<OpePriceSheetHistory> list);

    int insertOrUpdate(OpePriceSheetHistory record);

    int insertOrUpdateSelective(OpePriceSheetHistory record);
}