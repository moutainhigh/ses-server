package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpeSysRoleMenuService extends IService<OpeSysRoleMenu>{


    int batchInsert(List<OpeSysRoleMenu> list);

    int insertOrUpdate(OpeSysRoleMenu record);

    int insertOrUpdateSelective(OpeSysRoleMenu record);

}
