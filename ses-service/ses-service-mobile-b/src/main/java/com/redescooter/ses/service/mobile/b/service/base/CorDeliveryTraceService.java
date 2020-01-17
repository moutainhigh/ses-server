package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorDeliveryTrace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CorDeliveryTraceService extends IService<CorDeliveryTrace> {


    int batchInsert(List<CorDeliveryTrace> list);

    int insertOrUpdate(CorDeliveryTrace record);

    int insertOrUpdateSelective(CorDeliveryTrace record);

}

