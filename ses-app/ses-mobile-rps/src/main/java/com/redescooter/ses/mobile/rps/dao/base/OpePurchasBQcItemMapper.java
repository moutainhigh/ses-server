package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePurchasBQcItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasBQcItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpePurchasBQcItem record);

    int insertOrUpdate(OpePurchasBQcItem record);

    int insertOrUpdateSelective(OpePurchasBQcItem record);

    int insertSelective(OpePurchasBQcItem record);

    OpePurchasBQcItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpePurchasBQcItem record);

    int updateByPrimaryKey(OpePurchasBQcItem record);

    int updateBatch(List<OpePurchasBQcItem> list);

    int updateBatchSelective(List<OpePurchasBQcItem> list);

    int batchInsert(@Param("list") List<OpePurchasBQcItem> list);
}