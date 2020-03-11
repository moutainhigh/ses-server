package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 3:29 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Transactional
public interface OpeSysUserService extends IService<OpeSysUser> {

    int updateBatch(List<OpeSysUser> list);

    int batchInsert(List<OpeSysUser> list);

    int insertOrUpdate(OpeSysUser record);

    int insertOrUpdateSelective(OpeSysUser record);

}








