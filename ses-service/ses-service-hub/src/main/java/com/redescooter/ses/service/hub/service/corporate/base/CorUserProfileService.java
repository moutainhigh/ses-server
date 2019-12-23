package com.redescooter.ses.service.hub.service.corporate.base;

import com.redescooter.ses.service.hub.dm.corporate.base.CorUserProfile;

import java.util.List;
public interface CorUserProfileService{


    int deleteByPrimaryKey(Long id);

    int insert(CorUserProfile record);

    int insertOrUpdate(CorUserProfile record);

    int insertOrUpdateSelective(CorUserProfile record);

    int insertSelective(CorUserProfile record);

    CorUserProfile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CorUserProfile record);

    int updateByPrimaryKey(CorUserProfile record);

    int updateBatch(List<CorUserProfile> list);

}
