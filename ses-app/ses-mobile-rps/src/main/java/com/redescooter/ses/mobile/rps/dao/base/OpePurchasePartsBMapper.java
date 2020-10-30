package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasePartsB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasePartsBMapper extends BaseMapper<OpePurchasePartsB> {
    int updateBatch(List<OpePurchasePartsB> list);

    int batchInsert(@Param("list") List<OpePurchasePartsB> list);

    int insertOrUpdate(OpePurchasePartsB record);

    int insertOrUpdateSelective(OpePurchasePartsB record);
}