package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOrgDepartment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeOrgDepartmentMapper extends BaseMapper<OpeOrgDepartment> {
    int updateBatch(List<OpeOrgDepartment> list);

    int batchInsert(@Param("list") List<OpeOrgDepartment> list);

    int insertOrUpdate(OpeOrgDepartment record);

    int insertOrUpdateSelective(OpeOrgDepartment record);
}