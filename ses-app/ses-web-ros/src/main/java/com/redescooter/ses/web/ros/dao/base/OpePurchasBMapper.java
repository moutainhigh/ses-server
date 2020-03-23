package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasB;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasBMapper extends BaseMapper<OpePurchasB> {
    int batchInsert(@Param("list") List<OpePurchasB> list);

    int insertOrUpdate(OpePurchasB record);

    int insertOrUpdateSelective(OpePurchasB record);
}