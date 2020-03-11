package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysRoleDept;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysRoleDeptService extends IService<OpeSysRoleDept> {


    int updateBatch(List<OpeSysRoleDept> list);

    int batchInsert(List<OpeSysRoleDept> list);

    int insertOrUpdate(OpeSysRoleDept record);

    int insertOrUpdateSelective(OpeSysRoleDept record);

}
