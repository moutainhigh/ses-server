package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import com.baomidou.mybatisplus.extension.service.IService;
    /**
 * @author      Mr.lijiating
 * @Date:       19/12/2019 3:29 上午
 * @ClassName:  ${NAME}
 * @Function:   TODO
 * @version     V1.0
 */ 
public interface OpeSysUserService extends IService<OpeSysUser>{

    int updateBatch(List<OpeSysUser> list);

    int batchInsert(List<OpeSysUser> list);

    int insertOrUpdate(OpeSysUser record);

    int insertOrUpdateSelective(OpeSysUser record);

}
