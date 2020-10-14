package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPartPriceHistory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionPartPriceHistoryMapper extends BaseMapper<OpeProductionPartPriceHistory> {
    int updateBatch(List<OpeProductionPartPriceHistory> list);

    int batchInsert(@Param("list") List<OpeProductionPartPriceHistory> list);

    int insertOrUpdate(OpeProductionPartPriceHistory record);

    int insertOrUpdateSelective(OpeProductionPartPriceHistory record);
}
