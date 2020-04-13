package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderScooter;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairOrderScooterMapper extends BaseMapper<OpeRepairOrderScooter> {
    int updateBatch(List<OpeRepairOrderScooter> list);

    int batchInsert(@Param("list") List<OpeRepairOrderScooter> list);

    int insertOrUpdate(OpeRepairOrderScooter record);

    int insertOrUpdateSelective(OpeRepairOrderScooter record);
}