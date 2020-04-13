package com.redescooter.ses.service.hub.source.consumer.service.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserProfile;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 1:58 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@DS("consumer")
public interface HubConUserProfileService extends IService<HubConUserProfile> {


    int updateBatch(List<HubConUserProfile> list);

    int batchInsert(List<HubConUserProfile> list);

    int insertOrUpdate(HubConUserProfile record);

    int insertOrUpdateSelective(HubConUserProfile record);

}
