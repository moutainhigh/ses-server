package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author assert
 * @date 2021/2/2 13:49
 */
public interface OpeCombinListPartsSerialBindMapper extends BaseMapper<OpeCombinListPartsSerialBind> {
    int updateBatch(List<OpeCombinListPartsSerialBind> list);

    int updateBatchSelective(List<OpeCombinListPartsSerialBind> list);

    int batchInsert(@Param("list") List<OpeCombinListPartsSerialBind> list);

    int insertOrUpdate(OpeCombinListPartsSerialBind record);

    int insertOrUpdateSelective(OpeCombinListPartsSerialBind record);
}