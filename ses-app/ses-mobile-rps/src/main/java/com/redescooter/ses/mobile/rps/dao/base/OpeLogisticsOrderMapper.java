package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeLogisticsOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeLogisticsOrderMapper extends BaseMapper<OpeLogisticsOrder> {
    int updateBatch(List<OpeLogisticsOrder> list);

    int batchInsert(@Param("list") List<OpeLogisticsOrder> list);

    int insertOrUpdate(OpeLogisticsOrder record);

    int insertOrUpdateSelective(OpeLogisticsOrder record);
}