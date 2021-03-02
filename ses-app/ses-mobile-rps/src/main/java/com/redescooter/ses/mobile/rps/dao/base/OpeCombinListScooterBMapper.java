package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 13:52
 */
public interface OpeCombinListScooterBMapper extends BaseMapper<OpeCombinListScooterB> {
    int updateBatch(List<OpeCombinListScooterB> list);

    int updateBatchSelective(List<OpeCombinListScooterB> list);

    int batchInsert(@Param("list") List<OpeCombinListScooterB> list);

    int insertOrUpdate(OpeCombinListScooterB record);

    int insertOrUpdateSelective(OpeCombinListScooterB record);
}