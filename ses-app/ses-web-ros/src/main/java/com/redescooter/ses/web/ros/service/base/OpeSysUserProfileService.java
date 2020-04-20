package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeSysUserProfileService extends IService<OpeSysUserProfile> {


    int updateBatch(List<OpeSysUserProfile> list);

    int batchInsert(List<OpeSysUserProfile> list);

    int insertOrUpdate(OpeSysUserProfile record);

    int insertOrUpdateSelective(OpeSysUserProfile record);

}

