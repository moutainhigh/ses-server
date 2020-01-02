package com.redescooter.ses.service.hub.source.consumer.service.base;

import java.util.List;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserProfile;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ConUserProfileService extends IService<ConUserProfile>{


    int updateBatch(List<ConUserProfile> list);

    int batchInsert(List<ConUserProfile> list);

    int insertOrUpdate(ConUserProfile record);

    int insertOrUpdateSelective(ConUserProfile record);

}
