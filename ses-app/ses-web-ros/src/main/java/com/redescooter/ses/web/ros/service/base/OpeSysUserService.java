package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeSysUserService extends IService<OpeSysUser> {


    int updateBatch(List<OpeSysUser> list);

    int batchInsert(List<OpeSysUser> list);

    int insertOrUpdate(OpeSysUser record);

    int insertOrUpdateSelective(OpeSysUser record);

}


