package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderStaff;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeRepairOrderStaffMapper extends BaseMapper<OpeRepairOrderStaff> {
    int updateBatch(List<OpeRepairOrderStaff> list);

    int batchInsert(@Param("list") List<OpeRepairOrderStaff> list);

    int insertOrUpdate(OpeRepairOrderStaff record);

    int insertOrUpdateSelective(OpeRepairOrderStaff record);
}