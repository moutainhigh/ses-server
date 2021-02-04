package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeAllocateOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeAllocateOrderMapper extends BaseMapper<OpeAllocateOrder> {
    int updateBatch(List<OpeAllocateOrder> list);

    int batchInsert(@Param("list") List<OpeAllocateOrder> list);

    int insertOrUpdate(OpeAllocateOrder record);

    int insertOrUpdateSelective(OpeAllocateOrder record);
}