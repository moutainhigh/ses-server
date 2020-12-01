package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchaseScooterB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchaseScooterBMapper extends BaseMapper<OpePurchaseScooterB> {
    int updateBatch(List<OpePurchaseScooterB> list);

    int batchInsert(@Param("list") List<OpePurchaseScooterB> list);

    int insertOrUpdate(OpePurchaseScooterB record);

    int insertOrUpdateSelective(OpePurchaseScooterB record);
}