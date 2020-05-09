package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRegionalPriceSheet;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeRegionalPriceSheetMapper extends BaseMapper<OpeRegionalPriceSheet> {
    int updateBatch(List<OpeRegionalPriceSheet> list);

    int batchInsert(@Param("list") List<OpeRegionalPriceSheet> list);

    int insertOrUpdate(OpeRegionalPriceSheet record);

    int insertOrUpdateSelective(OpeRegionalPriceSheet record);
}