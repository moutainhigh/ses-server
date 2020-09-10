package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.SellsyProduct;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellsyProductMapper extends BaseMapper<SellsyProduct> {
    int updateBatch(List<SellsyProduct> list);

    int batchInsert(@Param("list") List<SellsyProduct> list);

    int insertOrUpdate(SellsyProduct record);

    int insertOrUpdateSelective(SellsyProduct record);
}