package com.redescooter.ses.service.hub.source.consumer.service.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.hub.source.consumer.dm.HubConUserScooter;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 6/1/2020 1:59 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@DS("consumer")
public interface HubConUserScooterService extends IService<HubConUserScooter> {


    int updateBatch(List<HubConUserScooter> list);

    int batchInsert(List<HubConUserScooter> list);

    int insertOrUpdate(HubConUserScooter record);

    int insertOrUpdateSelective(HubConUserScooter record);

}
