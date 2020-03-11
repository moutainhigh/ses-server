package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSysUserProfile;

import java.util.List;

public interface OpeSysUserProfileService extends IService<OpeSysUserProfile> {


    int updateBatch(List<OpeSysUserProfile> list);

    int batchInsert(List<OpeSysUserProfile> list);

    int insertOrUpdate(OpeSysUserProfile record);

    int insertOrUpdateSelective(OpeSysUserProfile record);

}
