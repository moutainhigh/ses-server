package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingBQc;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasingBQcMapper extends BaseMapper<OpePurchasingBQc> {
    int updateBatch(List<OpePurchasingBQc> list);

    int batchInsert(@Param("list") List<OpePurchasingBQc> list);

    int insertOrUpdate(OpePurchasingBQc record);

    int insertOrUpdateSelective(OpePurchasingBQc record);
}