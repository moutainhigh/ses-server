package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCombinOrder;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeCombinOrderMapper extends BaseMapper<OpeCombinOrder> {
    int updateBatch(List<OpeCombinOrder> list);

    int batchInsert(@Param("list") List<OpeCombinOrder> list);

    int insertOrUpdate(OpeCombinOrder record);

    int insertOrUpdateSelective(OpeCombinOrder record);
}