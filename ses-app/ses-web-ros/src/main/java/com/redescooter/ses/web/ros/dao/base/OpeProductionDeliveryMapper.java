package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionDelivery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductionDeliveryMapper extends BaseMapper<OpeProductionDelivery> {
    int updateBatch(List<OpeProductionDelivery> list);

    int batchInsert(@Param("list") List<OpeProductionDelivery> list);

    int insertOrUpdate(OpeProductionDelivery record);

    int insertOrUpdateSelective(OpeProductionDelivery record);
}