package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasProduct;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasProductMapper extends BaseMapper<OpePurchasProduct> {
    int updateBatch(List<OpePurchasProduct> list);

    int batchInsert(@Param("list") List<OpePurchasProduct> list);

    int insertOrUpdate(OpePurchasProduct record);

    int insertOrUpdateSelective(OpePurchasProduct record);
}