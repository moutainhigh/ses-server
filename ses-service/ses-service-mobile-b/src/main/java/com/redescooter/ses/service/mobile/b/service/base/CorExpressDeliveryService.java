package com.redescooter.ses.service.mobile.b.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDelivery;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryExample;
@Transactional

public interface CorExpressDeliveryService extends IService<CorExpressDelivery>{


    long countByExample(CorExpressDeliveryExample example);

    int deleteByExample(CorExpressDeliveryExample example);

    List<CorExpressDelivery> selectByExample(CorExpressDeliveryExample example);

    int updateByExampleSelective(CorExpressDelivery record,CorExpressDeliveryExample example);

    int updateByExample(CorExpressDelivery record,CorExpressDeliveryExample example);

}
