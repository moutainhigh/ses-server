package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeEntrustOrderMapper extends BaseMapper<OpeEntrustOrder> {
    int updateBatch(List<OpeEntrustOrder> list);

    int batchInsert(@Param("list") List<OpeEntrustOrder> list);

    int insertOrUpdate(OpeEntrustOrder record);

    int insertOrUpdateSelective(OpeEntrustOrder record);
}