package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasing;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasingMapper extends BaseMapper<OpePurchasing> {
    int updateBatch(List<OpePurchasing> list);

    int batchInsert(@Param("list") List<OpePurchasing> list);

    int insertOrUpdate(OpePurchasing record);

    int insertOrUpdateSelective(OpePurchasing record);
}