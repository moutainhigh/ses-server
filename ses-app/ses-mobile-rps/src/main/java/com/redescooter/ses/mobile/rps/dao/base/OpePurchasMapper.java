package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpePurchas;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpePurchas record);

    int insertOrUpdate(OpePurchas record);

    int insertOrUpdateSelective(OpePurchas record);

    int insertSelective(OpePurchas record);

    OpePurchas selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpePurchas record);

    int updateByPrimaryKey(OpePurchas record);

    int updateBatch(List<OpePurchas> list);

    int updateBatchSelective(List<OpePurchas> list);

    int batchInsert(@Param("list") List<OpePurchas> list);
}