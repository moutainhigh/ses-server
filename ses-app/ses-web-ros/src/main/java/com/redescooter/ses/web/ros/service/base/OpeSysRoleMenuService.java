package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysRoleMenuService extends IService<OpeSysRoleMenu>{


    int batchInsert(List<OpeSysRoleMenu> list);

    int insertOrUpdate(OpeSysRoleMenu record);

    int insertOrUpdateSelective(OpeSysRoleMenu record);

}
