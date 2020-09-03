package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeFrStockProduct;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeFrStockProductMapper extends BaseMapper<OpeFrStockProduct> {
    int updateBatch(List<OpeFrStockProduct> list);

    int batchInsert(@Param("list") List<OpeFrStockProduct> list);

    int insertOrUpdate(OpeFrStockProduct record);

    int insertOrUpdateSelective(OpeFrStockProduct record);
}