package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysUserService extends IService<OpeSysUser>{


    int updateBatch(List<OpeSysUser> list);

    int batchInsert(List<OpeSysUser> list);

    int insertOrUpdate(OpeSysUser record);

    int insertOrUpdateSelective(OpeSysUser record);

}
