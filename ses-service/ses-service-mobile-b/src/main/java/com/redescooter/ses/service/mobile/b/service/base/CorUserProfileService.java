package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorUserProfile;

import java.util.List;

public interface CorUserProfileService extends IService<CorUserProfile> {


    int updateBatch(List<CorUserProfile> list);

    int batchInsert(List<CorUserProfile> list);

    int insertOrUpdate(CorUserProfile record);

    int insertOrUpdateSelective(CorUserProfile record);

}

