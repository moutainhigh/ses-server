package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeOrgPositionPermission;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeOrgPositionPermissionMapper extends BaseMapper<OpeOrgPositionPermission> {
    int updateBatch(List<OpeOrgPositionPermission> list);

    int batchInsert(@Param("list") List<OpeOrgPositionPermission> list);

    int insertOrUpdate(OpeOrgPositionPermission record);

    int insertOrUpdateSelective(OpeOrgPositionPermission record);
}