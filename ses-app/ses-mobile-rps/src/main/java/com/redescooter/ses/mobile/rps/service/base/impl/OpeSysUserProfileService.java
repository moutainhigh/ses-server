package com.redescooter.ses.mobile.rps.service.base.impl;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeSysUserProfile;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeSysUserProfileService extends IService<OpeSysUserProfile>{


    int updateBatch(List<OpeSysUserProfile> list);

    int batchInsert(List<OpeSysUserProfile> list);

    int insertOrUpdate(OpeSysUserProfile record);

    int insertOrUpdateSelective(OpeSysUserProfile record);

}
