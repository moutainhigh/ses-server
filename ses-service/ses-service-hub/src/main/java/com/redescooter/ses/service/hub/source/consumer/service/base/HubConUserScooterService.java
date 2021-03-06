package com.redescooter.ses.service.hub.source.consumer.service.base;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserScooter;
import com.baomidou.mybatisplus.extension.service.IService;
@DS("consumer")
public interface HubConUserScooterService extends IService<HubConUserScooter>{


    int updateBatch(List<HubConUserScooter> list);

    int batchInsert(List<HubConUserScooter> list);

    int insertOrUpdate(HubConUserScooter record);

    int insertOrUpdateSelective(HubConUserScooter record);

}
