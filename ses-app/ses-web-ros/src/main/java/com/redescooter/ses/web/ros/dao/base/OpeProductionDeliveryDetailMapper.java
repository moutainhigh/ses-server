package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionDeliveryDetail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeProductionDeliveryDetailMapper extends BaseMapper<OpeProductionDeliveryDetail> {
    int updateBatch(List<OpeProductionDeliveryDetail> list);

    int batchInsert(@Param("list") List<OpeProductionDeliveryDetail> list);

    int insertOrUpdate(OpeProductionDeliveryDetail record);

    int insertOrUpdateSelective(OpeProductionDeliveryDetail record);
}