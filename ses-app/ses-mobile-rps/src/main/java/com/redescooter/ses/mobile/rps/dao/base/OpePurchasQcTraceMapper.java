package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePurchasQcTrace;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasQcTraceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpePurchasQcTrace record);

    int insertOrUpdate(OpePurchasQcTrace record);

    int insertOrUpdateSelective(OpePurchasQcTrace record);

    int insertSelective(OpePurchasQcTrace record);

    OpePurchasQcTrace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpePurchasQcTrace record);

    int updateByPrimaryKey(OpePurchasQcTrace record);

    int updateBatch(List<OpePurchasQcTrace> list);

    int updateBatchSelective(List<OpePurchasQcTrace> list);

    int batchInsert(@Param("list") List<OpePurchasQcTrace> list);
}