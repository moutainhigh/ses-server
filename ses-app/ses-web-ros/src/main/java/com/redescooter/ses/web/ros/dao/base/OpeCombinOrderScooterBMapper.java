package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeCombinOrderScooterB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeCombinOrderScooterBMapper extends BaseMapper<OpeCombinOrderScooterB> {
    int updateBatch(List<OpeCombinOrderScooterB> list);

    int batchInsert(@Param("list") List<OpeCombinOrderScooterB> list);

    int insertOrUpdate(OpeCombinOrderScooterB record);

    int insertOrUpdateSelective(OpeCombinOrderScooterB record);
}