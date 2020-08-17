package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeStockProdPart;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeStockProdPartMapper extends BaseMapper<OpeStockProdPart> {
    int updateBatch(List<OpeStockProdPart> list);

    int batchInsert(@Param("list") List<OpeStockProdPart> list);

    int insertOrUpdate(OpeStockProdPart record);

    int insertOrUpdateSelective(OpeStockProdPart record);

    int updateBatchSelective(List<OpeStockProdPart> list);
}