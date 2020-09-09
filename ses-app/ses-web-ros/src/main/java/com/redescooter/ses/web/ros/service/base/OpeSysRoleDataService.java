package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysRoleData;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSysRoleDataService extends IService<OpeSysRoleData> {


    int updateBatch(List<OpeSysRoleData> list);

    int updateBatchSelective(List<OpeSysRoleData> list);

    int batchInsert(List<OpeSysRoleData> list);

    int insertOrUpdate(OpeSysRoleData record);

    int insertOrUpdateSelective(OpeSysRoleData record);

}

