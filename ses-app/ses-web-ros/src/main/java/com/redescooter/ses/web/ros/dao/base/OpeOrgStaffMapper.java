package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOrgStaff;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeOrgStaffMapper extends BaseMapper<OpeOrgStaff> {
    int updateBatch(List<OpeOrgStaff> list);

    int batchInsert(@Param("list") List<OpeOrgStaff> list);

    int insertOrUpdate(OpeOrgStaff record);

    int insertOrUpdateSelective(OpeOrgStaff record);
}