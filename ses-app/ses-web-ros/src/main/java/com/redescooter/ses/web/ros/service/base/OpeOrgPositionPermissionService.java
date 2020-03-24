package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOrgPositionPermission;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOrgPositionPermissionService extends IService<OpeOrgPositionPermission> {


    int updateBatch(List<OpeOrgPositionPermission> list);

    int batchInsert(List<OpeOrgPositionPermission> list);

    int insertOrUpdate(OpeOrgPositionPermission record);

    int insertOrUpdateSelective(OpeOrgPositionPermission record);

}
