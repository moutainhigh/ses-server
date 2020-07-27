package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeFrStock;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeFrStockMapper extends BaseMapper<OpeFrStock> {
    int updateBatch(List<OpeFrStock> list);

    int batchInsert(@Param("list") List<OpeFrStock> list);

    int insertOrUpdate(OpeFrStock record);

    int insertOrUpdateSelective(OpeFrStock record);
}
