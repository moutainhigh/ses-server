package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpePurchasProduct;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpePurchasProductMapper extends BaseMapper<OpePurchasProduct> {
    int updateBatch(List<OpePurchasProduct> list);

    int updateBatchSelective(List<OpePurchasProduct> list);

    int batchInsert(@Param("list") List<OpePurchasProduct> list);

    int insertOrUpdate(OpePurchasProduct record);

    int insertOrUpdateSelective(OpePurchasProduct record);
}