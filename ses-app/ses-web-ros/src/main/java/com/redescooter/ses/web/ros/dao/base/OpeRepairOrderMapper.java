package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairOrder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairOrderMapper extends BaseMapper<OpeRepairOrder> {
    int updateBatch(List<OpeRepairOrder> list);

    int batchInsert(@Param("list") List<OpeRepairOrder> list);

    int insertOrUpdate(OpeRepairOrder record);

    int insertOrUpdateSelective(OpeRepairOrder record);
}