package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchaseCombinB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchaseCombinBMapper extends BaseMapper<OpePurchaseCombinB> {
    int updateBatch(List<OpePurchaseCombinB> list);

    int batchInsert(@Param("list") List<OpePurchaseCombinB> list);

    int insertOrUpdate(OpePurchaseCombinB record);

    int insertOrUpdateSelective(OpePurchaseCombinB record);
}