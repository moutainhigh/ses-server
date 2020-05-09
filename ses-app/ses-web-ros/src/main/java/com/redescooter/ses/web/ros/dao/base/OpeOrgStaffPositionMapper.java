package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOrgStaffPosition;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeOrgStaffPositionMapper extends BaseMapper<OpeOrgStaffPosition> {
    int updateBatch(List<OpeOrgStaffPosition> list);

    int batchInsert(@Param("list") List<OpeOrgStaffPosition> list);

    int insertOrUpdate(OpeOrgStaffPosition record);

    int insertOrUpdateSelective(OpeOrgStaffPosition record);
}