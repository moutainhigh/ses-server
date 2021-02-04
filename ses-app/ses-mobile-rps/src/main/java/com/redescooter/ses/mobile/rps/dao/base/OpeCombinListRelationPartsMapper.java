package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 13:27
 */
public interface OpeCombinListRelationPartsMapper extends BaseMapper<OpeCombinListRelationParts> {
    int updateBatch(List<OpeCombinListRelationParts> list);

    int updateBatchSelective(List<OpeCombinListRelationParts> list);

    int batchInsert(@Param("list") List<OpeCombinListRelationParts> list);

    int insertOrUpdate(OpeCombinListRelationParts record);

    int insertOrUpdateSelective(OpeCombinListRelationParts record);
}