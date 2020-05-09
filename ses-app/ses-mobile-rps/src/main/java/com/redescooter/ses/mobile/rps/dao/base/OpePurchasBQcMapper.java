package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasBQc;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpePurchasBQcMapper extends BaseMapper<OpePurchasBQc> {
    int updateBatch(List<OpePurchasBQc> list);

    int batchInsert(@Param("list") List<OpePurchasBQc> list);

    int insertOrUpdate(OpePurchasBQc record);

    int insertOrUpdateSelective(OpePurchasBQc record);
}