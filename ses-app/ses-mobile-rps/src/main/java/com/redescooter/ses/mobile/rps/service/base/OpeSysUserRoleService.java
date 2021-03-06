package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeSysUserRole;

import java.util.List;

public interface OpeSysUserRoleService extends IService<OpeSysUserRole>{


    int updateBatch(List<OpeSysUserRole> list);

    int batchInsert(List<OpeSysUserRole> list);

    int insertOrUpdate(OpeSysUserRole record);

    int insertOrUpdateSelective(OpeSysUserRole record);

}
