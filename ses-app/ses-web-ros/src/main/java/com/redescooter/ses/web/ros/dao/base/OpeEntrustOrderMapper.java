package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeEntrustOrderMapper extends BaseMapper<OpeEntrustOrder> {
    int updateBatch(List<OpeEntrustOrder> list);

    int batchInsert(@Param("list") List<OpeEntrustOrder> list);

    int insertOrUpdate(OpeEntrustOrder record);

    int insertOrUpdateSelective(OpeEntrustOrder record);
}