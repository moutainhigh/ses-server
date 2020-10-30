package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchaseScooterB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchaseScooterBMapper extends BaseMapper<OpePurchaseScooterB> {
    int updateBatch(List<OpePurchaseScooterB> list);

    int batchInsert(@Param("list") List<OpePurchaseScooterB> list);

    int insertOrUpdate(OpePurchaseScooterB record);

    int insertOrUpdateSelective(OpePurchaseScooterB record);
}