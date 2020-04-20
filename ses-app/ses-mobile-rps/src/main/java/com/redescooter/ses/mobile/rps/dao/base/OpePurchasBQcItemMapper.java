package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasBQcItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasBQcItemMapper extends BaseMapper<OpePurchasBQcItem> {
    int updateBatch(List<OpePurchasBQcItem> list);

    int batchInsert(@Param("list") List<OpePurchasBQcItem> list);

    int insertOrUpdate(OpePurchasBQcItem record);

    int insertOrUpdateSelective(OpePurchasBQcItem record);
}