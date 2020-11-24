package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderCombinB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeCombinOrderCombinBMapper extends BaseMapper<OpeCombinOrderCombinB> {
    int updateBatch(List<OpeCombinOrderCombinB> list);

    int batchInsert(@Param("list") List<OpeCombinOrderCombinB> list);

    int insertOrUpdate(OpeCombinOrderCombinB record);

    int insertOrUpdateSelective(OpeCombinOrderCombinB record);
}