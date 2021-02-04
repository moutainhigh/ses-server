package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 15:58
 */
public interface OpeCombinOrderCombinBMapper extends BaseMapper<OpeCombinOrderCombinB> {
    int updateBatch(List<OpeCombinOrderCombinB> list);

    int updateBatchSelective(List<OpeCombinOrderCombinB> list);

    int batchInsert(@Param("list") List<OpeCombinOrderCombinB> list);

    int insertOrUpdate(OpeCombinOrderCombinB record);

    int insertOrUpdateSelective(OpeCombinOrderCombinB record);
}