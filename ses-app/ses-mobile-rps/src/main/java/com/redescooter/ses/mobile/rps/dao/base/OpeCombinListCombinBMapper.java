package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 13:55
 */
public interface OpeCombinListCombinBMapper extends BaseMapper<OpeCombinListCombinB> {
    int updateBatch(List<OpeCombinListCombinB> list);

    int updateBatchSelective(List<OpeCombinListCombinB> list);

    int batchInsert(@Param("list") List<OpeCombinListCombinB> list);

    int insertOrUpdate(OpeCombinListCombinB record);

    int insertOrUpdateSelective(OpeCombinListCombinB record);
}