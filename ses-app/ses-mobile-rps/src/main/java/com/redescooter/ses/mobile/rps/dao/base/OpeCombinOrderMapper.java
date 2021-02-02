package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 11:55
 */
public interface OpeCombinOrderMapper extends BaseMapper<OpeCombinOrder> {
    int updateBatch(List<OpeCombinOrder> list);

    int updateBatchSelective(List<OpeCombinOrder> list);

    int batchInsert(@Param("list") List<OpeCombinOrder> list);

    int insertOrUpdate(OpeCombinOrder record);

    int insertOrUpdateSelective(OpeCombinOrder record);
}