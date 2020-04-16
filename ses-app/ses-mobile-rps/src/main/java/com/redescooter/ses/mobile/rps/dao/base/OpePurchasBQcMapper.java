package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePurchasBQc;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasBQcMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpePurchasBQc record);

    int insertOrUpdate(OpePurchasBQc record);

    int insertOrUpdateSelective(OpePurchasBQc record);

    int insertSelective(OpePurchasBQc record);

    OpePurchasBQc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpePurchasBQc record);

    int updateByPrimaryKey(OpePurchasBQc record);

    int updateBatch(List<OpePurchasBQc> list);

    int updateBatchSelective(List<OpePurchasBQc> list);

    int batchInsert(@Param("list") List<OpePurchasBQc> list);
}