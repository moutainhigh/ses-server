package com.redescooter.ses.mobile.rps.dao.base;

import com.redescooter.ses.mobile.rps.dm.OpeParts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePartsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OpeParts record);

    int insertOrUpdate(OpeParts record);

    int insertOrUpdateSelective(OpeParts record);

    int insertSelective(OpeParts record);

    OpeParts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeParts record);

    int updateByPrimaryKey(OpeParts record);

    int updateBatch(List<OpeParts> list);

    int updateBatchSelective(List<OpeParts> list);

    int batchInsert(@Param("list") List<OpeParts> list);
}