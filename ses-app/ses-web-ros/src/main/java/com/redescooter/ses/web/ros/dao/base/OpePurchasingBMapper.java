package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasingBMapper extends BaseMapper<OpePurchasingB> {
    int batchInsert(@Param("list") List<OpePurchasingB> list);

    int insertOrUpdate(OpePurchasingB record);

    int insertOrUpdateSelective(OpePurchasingB record);
}