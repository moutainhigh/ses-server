package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysRpsUser;

import java.util.List;
public interface OpeSysRpsUserService extends IService<OpeSysRpsUser> {


    int updateBatch(List<OpeSysRpsUser> list);

    int batchInsert(List<OpeSysRpsUser> list);

    int insertOrUpdate(OpeSysRpsUser record);

    int insertOrUpdateSelective(OpeSysRpsUser record);

}
