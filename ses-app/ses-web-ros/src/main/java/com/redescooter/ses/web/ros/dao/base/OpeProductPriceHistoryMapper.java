package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductPriceHistory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductPriceHistoryMapper extends BaseMapper<OpeProductPriceHistory> {
    int updateBatch(List<OpeProductPriceHistory> list);

    int batchInsert(@Param("list") List<OpeProductPriceHistory> list);

    int insertOrUpdate(OpeProductPriceHistory record);

    int insertOrUpdateSelective(OpeProductPriceHistory record);
}
