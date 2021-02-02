package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOrderStatusFlow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeOrderStatusFlowMapper extends BaseMapper<OpeOrderStatusFlow> {
    int updateBatch(List<OpeOrderStatusFlow> list);

    int batchInsert(@Param("list") List<OpeOrderStatusFlow> list);

    int insertOrUpdate(OpeOrderStatusFlow record);

    int insertOrUpdateSelective(OpeOrderStatusFlow record);
}