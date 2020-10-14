package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionPartPriceSheet;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeProductionPartPriceSheetMapper extends BaseMapper<OpeProductionPartPriceSheet> {
    int updateBatch(List<OpeProductionPartPriceSheet> list);

    int batchInsert(@Param("list") List<OpeProductionPartPriceSheet> list);

    int insertOrUpdate(OpeProductionPartPriceSheet record);

    int insertOrUpdateSelective(OpeProductionPartPriceSheet record);
}
