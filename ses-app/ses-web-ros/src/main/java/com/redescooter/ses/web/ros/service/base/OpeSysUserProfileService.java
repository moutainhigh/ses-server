package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 19/12/2019 3:28 上午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Transactional
public interface OpeSysUserProfileService extends IService<OpeSysUserProfile> {


    int updateBatch(List<OpeSysUserProfile> list);

    int batchInsert(List<OpeSysUserProfile> list);

    int insertOrUpdate(OpeSysUserProfile record);

    int insertOrUpdateSelective(OpeSysUserProfile record);

}
