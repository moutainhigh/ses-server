package com.redescooter.ses.service.hub.service.consumer.base;

import java.util.List;
import com.redescooter.ses.service.hub.dm.customer.base.ConUserProfile;
public interface ConUserProfileService{


    int deleteByPrimaryKey(Long id);

    int insert(ConUserProfile record);

    int insertOrUpdate(ConUserProfile record);

    int insertOrUpdateSelective(ConUserProfile record);

    int insertSelective(ConUserProfile record);

    ConUserProfile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConUserProfile record);

    int updateByPrimaryKey(ConUserProfile record);

    int updateBatch(List<ConUserProfile> list);

    int batchInsert(List<ConUserProfile> list);

}
