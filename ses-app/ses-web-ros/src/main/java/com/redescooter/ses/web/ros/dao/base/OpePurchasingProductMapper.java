package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasingProduct;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpePurchasingProductMapper extends BaseMapper<OpePurchasingProduct> {
    int updateBatch(List<OpePurchasingProduct> list);

    int batchInsert(@Param("list") List<OpePurchasingProduct> list);

    int insertOrUpdate(OpePurchasingProduct record);

    int insertOrUpdateSelective(OpePurchasingProduct record);
}