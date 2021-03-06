package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysRole;

import java.util.List;

public interface OpeSysRoleService extends IService<OpeSysRole> {


    int updateBatch(List<OpeSysRole> list);

    int batchInsert(List<OpeSysRole> list);

    int insertOrUpdate(OpeSysRole record);

    int insertOrUpdateSelective(OpeSysRole record);

    int updateBatchSelective(List<OpeSysRole> list);
}




