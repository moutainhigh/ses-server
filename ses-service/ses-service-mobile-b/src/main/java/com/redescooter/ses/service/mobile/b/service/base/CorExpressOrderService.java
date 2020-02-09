package com.redescooter.ses.service.mobile.b.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrder;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderExample;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional

public interface CorExpressOrderService extends IService<CorExpressOrder>{


    long countByExample(CorExpressOrderExample example);

    int deleteByExample(CorExpressOrderExample example);

    List<CorExpressOrder> selectByExample(CorExpressOrderExample example);

    int updateByExampleSelective(CorExpressOrder record,CorExpressOrderExample example);

    int updateByExample(CorExpressOrder record,CorExpressOrderExample example);

}
