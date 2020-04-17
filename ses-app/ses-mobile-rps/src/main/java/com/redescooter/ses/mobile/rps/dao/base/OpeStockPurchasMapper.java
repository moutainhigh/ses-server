package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeStockPurchas;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeStockPurchasMapper extends BaseMapper<OpeStockPurchas> {
    int updateBatch(List<OpeStockPurchas> list);

    int batchInsert(@Param("list") List<OpeStockPurchas> list);

    int insertOrUpdate(OpeStockPurchas record);

    int insertOrUpdateSelective(OpeStockPurchas record);
}