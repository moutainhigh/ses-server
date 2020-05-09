package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeStockBill;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeStockBillMapper extends BaseMapper<OpeStockBill> {
    int updateBatch(List<OpeStockBill> list);

    int batchInsert(@Param("list") List<OpeStockBill> list);

    int insertOrUpdate(OpeStockBill record);

    int insertOrUpdateSelective(OpeStockBill record);
}