package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeStockMapper extends BaseMapper<OpeStock> {
    int updateBatch(List<OpeStock> list);

    int batchInsert(@Param("list") List<OpeStock> list);

    int insertOrUpdate(OpeStock record);

    int insertOrUpdateSelective(OpeStock record);
}