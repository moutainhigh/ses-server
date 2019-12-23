package com.redescooter.ses.service.hub.source.consumer.service.base;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.consumer.dm.ConUserProfile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 23/12/2019 11:21 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@DS("consumer")
public interface ConUserProfileService extends IService<ConUserProfile> {


    int updateBatch(List<ConUserProfile> list);

    int batchInsert(List<ConUserProfile> list);

    int insertOrUpdate(ConUserProfile record);

    int insertOrUpdateSelective(ConUserProfile record);

}
