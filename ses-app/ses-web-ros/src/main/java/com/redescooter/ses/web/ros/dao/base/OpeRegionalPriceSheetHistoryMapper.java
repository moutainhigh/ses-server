package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheetHistory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeRegionalPriceSheetHistoryMapper extends BaseMapper<OpeRegionalPriceSheetHistory> {
    int updateBatch(List<OpeRegionalPriceSheetHistory> list);

    int batchInsert(@Param("list") List<OpeRegionalPriceSheetHistory> list);

    int insertOrUpdate(OpeRegionalPriceSheetHistory record);

    int insertOrUpdateSelective(OpeRegionalPriceSheetHistory record);
}