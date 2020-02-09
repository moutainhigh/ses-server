package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetail;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetailExample;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional

public interface CorExpressDeliveryDetailService extends IService<CorExpressDeliveryDetail>{


    long countByExample(CorExpressDeliveryDetailExample example);

    int deleteByExample(CorExpressDeliveryDetailExample example);

    List<CorExpressDeliveryDetail> selectByExample(CorExpressDeliveryDetailExample example);

    int updateByExampleSelective(CorExpressDeliveryDetail record,CorExpressDeliveryDetailExample example);

    int updateByExample(CorExpressDeliveryDetail record,CorExpressDeliveryDetailExample example);

}
