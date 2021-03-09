package com.redescooter.ses.service.mobile.c.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserProfile;

import java.util.List;

public interface ConUserProfileService extends IService<ConUserProfile>{


    int updateBatch(List<ConUserProfile> list);

    int batchInsert(List<ConUserProfile> list);

    int insertOrUpdate(ConUserProfile record);

    int insertOrUpdateSelective(ConUserProfile record);

}
