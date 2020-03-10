package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysUserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeSysUserRoleService extends IService<OpeSysUserRole> {

    int updateBatch(List<OpeSysUserRole> list);

    int batchInsert(List<OpeSysUserRole> list);

    int insertOrUpdate(OpeSysUserRole record);

    int insertOrUpdateSelective(OpeSysUserRole record);

}
