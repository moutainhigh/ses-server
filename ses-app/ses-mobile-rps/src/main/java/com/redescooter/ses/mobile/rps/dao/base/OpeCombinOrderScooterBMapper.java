package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderScooterB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 15:54
 */
public interface OpeCombinOrderScooterBMapper extends BaseMapper<OpeCombinOrderScooterB> {
    int updateBatch(List<OpeCombinOrderScooterB> list);

    int updateBatchSelective(List<OpeCombinOrderScooterB> list);

    int batchInsert(@Param("list") List<OpeCombinOrderScooterB> list);

    int insertOrUpdate(OpeCombinOrderScooterB record);

    int insertOrUpdateSelective(OpeCombinOrderScooterB record);
}