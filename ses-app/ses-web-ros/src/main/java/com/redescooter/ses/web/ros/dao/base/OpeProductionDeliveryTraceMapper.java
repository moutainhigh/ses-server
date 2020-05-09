package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionDeliveryTrace;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductionDeliveryTraceMapper extends BaseMapper<OpeProductionDeliveryTrace> {
    int updateBatch(List<OpeProductionDeliveryTrace> list);

    int batchInsert(@Param("list") List<OpeProductionDeliveryTrace> list);

    int insertOrUpdate(OpeProductionDeliveryTrace record);

    int insertOrUpdateSelective(OpeProductionDeliveryTrace record);
}