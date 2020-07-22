package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeStockProdProduct;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeStockProdProductMapper extends BaseMapper<OpeStockProdProduct> {
    int updateBatch(List<OpeStockProdProduct> list);

    int batchInsert(@Param("list") List<OpeStockProdProduct> list);

    int insertOrUpdate(OpeStockProdProduct record);

    int insertOrUpdateSelective(OpeStockProdProduct record);
}
