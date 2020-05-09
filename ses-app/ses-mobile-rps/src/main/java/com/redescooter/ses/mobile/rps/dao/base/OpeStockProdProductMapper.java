package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeStockProdProduct;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpeStockProdProductMapper extends BaseMapper<OpeStockProdProduct> {
    int updateBatch(List<OpeStockProdProduct> list);

    int updateBatchSelective(List<OpeStockProdProduct> list);

    int batchInsert(@Param("list") List<OpeStockProdProduct> list);

    int insertOrUpdate(OpeStockProdProduct record);

    int insertOrUpdateSelective(OpeStockProdProduct record);
}