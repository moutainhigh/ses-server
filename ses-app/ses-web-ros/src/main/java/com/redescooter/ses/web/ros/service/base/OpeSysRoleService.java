package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeSysRole;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysRoleService extends IService<OpeSysRole>{


    int updateBatch(List<OpeSysRole> list);

    int batchInsert(List<OpeSysRole> list);

    int insertOrUpdate(OpeSysRole record);

    int insertOrUpdateSelective(OpeSysRole record);

}
