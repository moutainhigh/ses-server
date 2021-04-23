package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysUser;

import java.util.List;

public interface OpeSysUserService extends IService<OpeSysUser>{


    int updateBatch(List<OpeSysUser> list);

    int batchInsert(List<OpeSysUser> list);

    int insertOrUpdate(OpeSysUser record);

    int insertOrUpdateSelective(OpeSysUser record);

    List<String> findPerms(Long userId);

    List<String> findAllPerms();

}
