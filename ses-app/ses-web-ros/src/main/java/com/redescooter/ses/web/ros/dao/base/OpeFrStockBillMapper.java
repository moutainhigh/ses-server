package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeFrStockBill;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeFrStockBillMapper extends BaseMapper<OpeFrStockBill> {
    int updateBatch(List<OpeFrStockBill> list);

    int batchInsert(@Param("list") List<OpeFrStockBill> list);

    int insertOrUpdate(OpeFrStockBill record);

    int insertOrUpdateSelective(OpeFrStockBill record);
}