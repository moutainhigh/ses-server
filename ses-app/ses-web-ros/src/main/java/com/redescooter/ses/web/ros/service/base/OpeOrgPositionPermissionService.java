package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOrgPositionPermission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeOrgPositionPermissionService extends IService<OpeOrgPositionPermission> {


    int updateBatch(List<OpeOrgPositionPermission> list);

    int batchInsert(List<OpeOrgPositionPermission> list);

    int insertOrUpdate(OpeOrgPositionPermission record);

    int insertOrUpdateSelective(OpeOrgPositionPermission record);

}
